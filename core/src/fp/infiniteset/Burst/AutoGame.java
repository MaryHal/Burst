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
    }

    @Override
    public void dispose()
    {
        super.dispose();
        music.dispose();
    }
}
