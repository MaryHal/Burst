package fp.infiniteset.Burst;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import fp.infiniteset.Burst.Utils.HaltonSequence;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class SimpleGame extends GameController
{
    private HaltonSequence dist;
    private int index;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super(musicFile, beatFile);

        // Throw away the first 20 numbers from the HaltonSequence
        // and also introduce some randomness./
        this.index = rng.nextInt(10) + 20;
        dist = new HaltonSequence(new int[] {2, 3});
    }

    @Override
    public void initializeLauncher()
    {
        launcher = new FireworkLauncher(camera)
        {
            // Since Fireworks are not guaranteed to "hit" their targets,
            // we gotta remove undetonated ones.
            @Override
            public void updateFirework(Firework f, float delta)
            {
                f.update(delta);

                if (f.getPosition().y < 0.0f)
                {
                    f.setAlive(false);
                    remove(f);

                    inputFail();
                }
            }
        };
    }

    @Override
    public boolean handleKeyDown(int keycode)
    {
        return handleInputEvent();
    }

    @Override
    public boolean handleTouchDown(int x, int y, int pointer, int button)
    {
        return handleInputEvent();
    }

    @Override
    public void render(float delta)
    {
        while (beatMap.getNextBeat() != null &&
               timer.getTime() > beatMap.getNextBeat().time - 1.0f)
        {
            Vector2 position = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);

            // Calculate destination position
            double[] v = dist.getHaltonNumber(index);
            index++;
            Vector2 destination = new Vector2((float)v[0] * 200 + 140,
                                              (float)v[1] * 80 + 80);

            launcher.fire(position, destination);
            beatMap.popBeat();
        }

        launcher.draw(delta);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        launcher.dispose();
    }

    public boolean handleInputEvent()
    {
        boolean success = false;
        for (Firework f : launcher.getFireworks())
        {
            // 20 pixel radius to destination
            if (f.getDistance2() < 400.0f)
            {
                combo++;
                score += (400.0f - f.getDistance2()) * combo / 10.0f;
                launcher.detonate(f);

                success = true;
            }
        }

        if (!success)
        {
            inputFail();
        }

        return true;
    }

    public void inputFail()
    {
        score = (score - 1200 < 0) ? 0 : score - 1200;
        combo = 0;
    }
}
