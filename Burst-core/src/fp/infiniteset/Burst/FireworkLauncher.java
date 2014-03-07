package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class FireworkLauncher
{
    private ParticleEffect prototype;
    private ParticleEffectPool effectPool;
    private Array<PooledEffect> effects;

    private FireworkPool fireworkPool;
    private Array<Firework> fireworks;

    private float[][] fireworkColors;
    private Random rng;

    private SpriteBatch particleBatch;
    private SpriteBatch fireworkBatch;

    public FireworkLauncher(Camera camera)
    {
        prototype = new ParticleEffect();
        prototype.load(Gdx.files.internal("effects/test.p"), Gdx.files.internal("effects"));
        prototype.start();

        effectPool = new ParticleEffectPool(prototype, 0, 70);
        effects = new Array<PooledEffect>();

        fireworkPool = new FireworkPool(64, 128);
        fireworks = new Array<Firework>();

        fireworkColors = new float[][] {
            {1.0f, 0.3f, 0.3f, 1.0f},
            {0.3f, 1.0f, 0.3f, 1.0f},
            {0.3f, 0.3f, 1.0f, 1.0f},

            {0.3f, 1.0f, 1.0f, 1.0f},
            {1.0f, 0.3f, 1.0f, 1.0f},
            {1.0f, 1.0f, 0.3f, 1.0f},
        };

        rng = new Random();

        particleBatch = new SpriteBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        particleBatch.setProjectionMatrix(camera.combined);

        fireworkBatch = new SpriteBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fireworkBatch.setProjectionMatrix(camera.combined);
    }

    public void dispose()
    {
        prototype.dispose();
        particleBatch.dispose();
        fireworkBatch.dispose();
    }

    public void fire(Vector2 position)
    {
        Firework f = fireworkPool.obtain();
        f.set(new Vector2(0, 0), position);
        fireworks.add(f);

    }

    public void update()
    {
    }

    public void draw(float delta)
    {
        fireworkBatch.begin();
        {
            for (Firework f : fireworks)
            {
                f.update(delta);
                f.draw(fireworkBatch);
                if (!f.isAlive())
                {
                    fireworks.removeValue(f, true);

                    PooledEffect effect = effectPool.obtain();
                    float[] color = fireworkColors[rng.nextInt(fireworkColors.length)];
                    effect.getEmitters().peek().getTint().setColors(color);
                    Vector2 destination = f.getDestination();
                    effect.setPosition(destination.x, destination.y);
                    effects.add(effect);
                }
            }
        }
        fireworkBatch.end();

        particleBatch.begin();
        for (PooledEffect effect : effects)
        {
            effect.draw(particleBatch, delta);
            if(effect.isComplete())
            {
                effects.removeValue(effect, true);
                effect.free();
            }
        }
        particleBatch.end();
    }
}
