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

        this.index = 0;
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
                }
            }
        };
    }

    @Override
    public boolean handleKeyDown(int keycode)
    {
        for (Firework f : launcher.getFireworks())
        {
            // 20 pixel radius to destination
            if (f.getDistance2() < 400.0f)
            {
                launcher.detonate(f);
            }
        }
        return true;
    }

    @Override
    public boolean handleTouchDown(int x, int y, int pointer, int button)
    {
        for (Firework f : launcher.getFireworks())
        {
            // 20 pixel radius to destination
            if (f.getDistance2() < 400.0f)
            {
                launcher.detonate(f);
            }
        }
        return true;
    }

    @Override
    public void render(float delta)
    {
        camera.update();

        /* System.out.println(music.getPosition() + ", " + beatMap.getNextBeat().time); */

        while (beatMap.getNextBeat() != null &&
               timer.getTime() > beatMap.getNextBeat().time - 1.0f)
        {
            // TODO: More robust positioning.
            Vector2 position    = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);

            double[] v = dist.getHaltonNumber(index);
            index++;
            Vector2 destination = new Vector2((float)v[0] * 200 + 140,
                                              (float)v[1] * 80 + 80);

            // Vector2 destination = new Vector2(rng.nextFloat() * 200 + 140,
            //         rng.nextFloat() * 80 + 80);

            launcher.fire(position, destination);
            beatMap.popBeat();
        }

        launcher.draw(delta);
    }

    public void dispose()
    {
        launcher.dispose();
        music.dispose();
    }
}
