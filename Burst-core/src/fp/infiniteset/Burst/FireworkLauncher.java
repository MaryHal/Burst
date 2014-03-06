package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
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

    public FireworkLauncher()
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
    }

    public void dispose()
    {
        prototype.dispose();
    }

    public void fire(Vector2 position)
    {
        PooledEffect effect = effectPool.obtain();
        float[] color = fireworkColors[rng.nextInt(fireworkColors.length)];
        effect.getEmitters().peek().getTint().setColors(color);
        effect.setPosition(position.x, position.y);
        effects.add(effect);
    }

    public void update()
    {
    }

    public void draw(SpriteBatch batch, float delta)
    {
        for (PooledEffect effect : effects)
        {
            effect.draw(batch, delta);
            if(effect.isComplete())
            {
                effects.removeValue(effect, true);
                effect.free();
            }
        }
    }
}
