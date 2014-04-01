package fp.infiniteset.Burst;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import fp.infiniteset.Burst.Beats.BeatMap;
import fp.infiniteset.Burst.Utils.Stopwatch;

import java.util.Random;

public class SimpleGame extends GameController
{
    protected BeatMap beatMap;
    protected Stopwatch timer;

    protected Random rng;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super();

        launcher = new FireworkLauncher(camera)
        {
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

        music.loadSong(musicFile);

        beatMap = new BeatMap(beatFile);
        timer = new Stopwatch();

        rng = new Random();
    }

    public void reset()
    {
        beatMap.reset();
        music.getMusic().stop();
        music.play();
        timer.stop();
        timer.start();
    }

    @Override
    public boolean handleKeyDown(int keycode)
    {
        for (Firework f : launcher.getFireworks())
        {
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
        music.timeDiff();

        while (beatMap.getNextBeat() != null &&
               timer.getTime() > beatMap.getNextBeat().time - 1.0f)
        {
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
