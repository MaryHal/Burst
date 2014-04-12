package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* import com.badlogic.gdx.graphics.glutils.ShaderProgram; */
/* import com.badlogic.gdx.utils.GdxRuntimeException; */

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class FireworkLauncher implements Disposable
{
    private ParticleEffect prototype;
    private ParticleEffectPool effectPool;
    private Array<PooledEffect> effects;

    private FireworkPool fireworkPool;
    private Array<Firework> fireworks;

    private Array<float[]> fireworkColors;

    private SpriteBatch particleBatch;
    private SpriteBatch fireworkBatch;

    /* private ShaderProgram blurShader; */

    public FireworkLauncher(Camera camera)
    {
        prototype = new ParticleEffect();
        prototype.load(Gdx.files.internal("effects/test.p"), Gdx.files.internal("effects"));
        prototype.start();

        effectPool = new ParticleEffectPool(prototype, 0, 70);
        effects = new Array<PooledEffect>();

        fireworkPool = new FireworkPool(16, 64);
        fireworks = new Array<Firework>();

        fireworkColors = new Array<float[]>(new float[][]{
            {0.8f, 0.3f, 0.3f, 1.0f},
            {0.3f, 0.8f, 0.3f, 1.0f},
            {0.3f, 0.3f, 0.8f, 1.0f},

            {0.3f, 0.9f, 0.9f, 1.0f},
            {0.9f, 0.3f, 0.9f, 1.0f},
            {0.9f, 0.9f, 0.3f, 1.0f},
        });

        particleBatch = new SpriteBatch(2048);
        particleBatch.setProjectionMatrix(camera.combined);

        fireworkBatch = new SpriteBatch(64);
        fireworkBatch.setProjectionMatrix(camera.combined);

        /* ShaderProgram.pedantic = false; */
        /* blurShader = new ShaderProgram(Gdx.files.internal("shaders/blur.vertex"), */
        /*                                Gdx.files.internal("shaders/blur.fragment")); */
        /* particleBatch.setShader(blurShader); */
        /* if (!blurShader.isCompiled()) */
        /*     throw new GdxRuntimeException(blurShader.getLog()); */

    }

    @Override
    public void dispose()
    {
        prototype.dispose();
        particleBatch.dispose();
        fireworkBatch.dispose();
        /* blurShader.dispose(); */
    }

    public Firework fire(Vector2 position, Vector2 destination, Firework next)
    {
        Firework f = fireworkPool.obtain();
        f.set(position, destination, next);
        fireworks.add(f);

        return f;
    }

    public void launch(Firework f)
    {
        f.launch();
    }

    public void detonate(Firework f)
    {
        fireworks.removeValue(f, true);
        Vector2 effectPosition = f.getPosition();

        PooledEffect effect = effectPool.obtain();
        float[] color = fireworkColors.random();
        effect.getEmitters().peek().getTint().setColors(color);
        effect.setPosition(effectPosition.x, effectPosition.y);
        effects.add(effect);
    }

    public void remove(Firework f)
    {
        fireworks.removeValue(f, true);
    }

    public Array<Firework> getFireworks()
    {
        return fireworks;
    }

    public abstract void updateFirework(Firework f, float delta);
    /*
    {
        f.update(delta);
        f.setAlive(f.checkBoundary() && f.checkCloseness());

        if (!f.isAlive())
        {
            detonate(f);
        }
    }
    */

    public void draw(float delta)
    {
        fireworkBatch.begin();
        {
            for (Firework f : fireworks)
            {
                updateFirework(f, delta);

                f.draw(fireworkBatch);
            }
        }
        fireworkBatch.end();

        particleBatch.begin();
        {
            for (PooledEffect effect : effects)
            {
                effect.draw(particleBatch, delta);
                if(effect.isComplete())
                {
                    effects.removeValue(effect, true);
                    effect.free();
                }
            }
        }
        particleBatch.end();
    }
}
