package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fp.infiniteset.Burst.Beats.BeatMap;

public abstract class FireworkLauncher implements Disposable
{
    private ParticleEffect prototype;
    private ParticleEffectPool effectPool;
    private Array<PooledEffect> effects;

    private FireworkPool fireworkPool;
    private Array<FireworkGroup> groups;

    private Array<float[]> fireworkColors;

    private SpriteBatch particleBatch;
    private SpriteBatch fireworkBatch;

    private ShapeRenderer shapeRenderer;

    public FireworkLauncher(Camera camera)
    {
        prototype = new ParticleEffect();
        prototype.load(Gdx.files.internal("effects/test.p"), Gdx.files.internal("effects"));
        prototype.start();

        effectPool = new ParticleEffectPool(prototype, 0, 70);
        effects = new Array<PooledEffect>();

        fireworkPool = new FireworkPool(16, 128);
        groups = new Array<FireworkGroup>();

        fireworkColors = new Array<float[]>(new float[][]{
            {0.9f, 0.3f, 0.3f, 1.0f},
            {0.3f, 0.9f, 0.3f, 1.0f},
            {0.3f, 0.3f, 0.9f, 1.0f},

            {0.3f, 0.9f, 0.9f, 1.0f},
            {0.9f, 0.3f, 0.9f, 1.0f},
            {0.9f, 0.9f, 0.3f, 1.0f},
        });

        particleBatch = new SpriteBatch(2048);
        particleBatch.setProjectionMatrix(camera.combined);

        fireworkBatch = new SpriteBatch(64);
        fireworkBatch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void dispose()
    {
        prototype.dispose();
        particleBatch.dispose();
        fireworkBatch.dispose();
    }

    public int enqueueCombo(int count)
    {
        FireworkGroup group = new FireworkGroup();

        for (int i = 0; i < count; i++)
        {
            Firework f = fireworkPool.obtain();
            group.addToCombo(0.0f, f);
        }

        group.placeFireworks();
        group.launchAll();

        groups.insert(0, group);

        return count;
    }

    public int enqueueCombo(int queuedIndex, BeatMap.Beat[] beatList)
    {
        if (queuedIndex > beatList.length)
            return queuedIndex;

        FireworkGroup group = new FireworkGroup();

        // 5 and 6 are beat finalizers
        do
        {
            Firework f = fireworkPool.obtain();
            group.addToCombo(beatList[queuedIndex].time, f);

            queuedIndex++;
        }
        while (queuedIndex < beatList.length &&
                beatList[queuedIndex].type != 5 &&
                beatList[queuedIndex].type != 6);

        group.placeFireworks();
        groups.insert(0, group);

        return queuedIndex;
    }

    public Firework getNextFirework()
    {
        for (int i = groups.size - 1; i > 0; i--)
        {
            if (!groups.get(i).isCompleted())
                return groups.get(i).getCurrentFirework();
        }
        return null;
    }

    // Get the current firework, but move the firework index along as well.
    public Firework yieldNextFirework()
    {
        for (int i = groups.size - 1; i > 0; i--)
        {
            if (!groups.get(i).isCompleted())
            {
                Firework f = groups.get(i).getCurrentFirework();
                groups.get(i).pushComboIndex();

                return f;
            }
        }
        return null;
    }

    public void removeFinishedGroups(float time)
    {
        while (groups.size > 0)
        {
            if (groups.peek().getTailTime() < time - 10.0f)
            {
                groups.pop();
            }
            else
                break;
        }
    }

    public Array<FireworkGroup> getFireworkGroups()
    {
        return groups;
    }

    public void detonate(Firework f)
    {
        // fireworks.removeValue(f, true);
        Vector2 effectPosition = f.getPosition();

        PooledEffect effect = effectPool.obtain();
        float[] color = fireworkColors.random();
        effect.getEmitters().peek().getTint().setColors(color);
        effect.setPosition(effectPosition.x, effectPosition.y);
        effects.add(effect);
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
        System.out.println(groups.size);
        fireworkBatch.begin();
        {
            for (FireworkGroup g : groups)
            {
                // g.draw(fireworkBatch, shapeRenderer);
                for (Firework f : g.getComboList())
                {
                    updateFirework(f, delta);

                    f.draw(fireworkBatch, shapeRenderer);
                }
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
