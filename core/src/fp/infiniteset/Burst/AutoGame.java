package fp.infiniteset.Burst;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

// Simple testbed
public class AutoGame extends GameController
{

    public AutoGame(FileHandle musicFile, FileHandle beatFile)
    {
        super(musicFile, beatFile);
    }

    @Override
    public void initializeLauncher()
    {
        launcher = new FireworkLauncher(camera)
        {
            @Override
            public void updateFirework(Firework f, float delta)
            {
                f.update(delta);
                f.setAlive(f.checkBoundary() && f.checkCloseness());

                if (!f.isAlive())
                {
                    detonate(f);
                }
            }
        };
    }

    @Override
    public boolean handleKeyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean handleTouchDown(int x, int y, int pointer, int button)
    {
        return false;
    }

    @Override
    public void render(float delta)
    {
        camera.update();

        while (beatMap.getNextBeat() != null &&
               timer.getTime() > beatMap.getNextBeat().time - 1.0f)
        {
            /* System.out.println(timer.getTime() + ", " + music.getPosition() + ", " + beatMap.getNextBeat().time); */

            Vector2 position    = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
            Vector2 destination = new Vector2(rng.nextFloat() * 200 + 140,
                    rng.nextFloat() * 80 + 80);

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
