package fp.infiniteset.Burst;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import fp.infiniteset.Burst.Beats.BeatMap;
import fp.infiniteset.Burst.Utils.Stopwatch;

import java.util.Random;

public class SimpleGame extends GameController
{
    private BeatMap beatMap;
    private Stopwatch timer;

    private Random rng;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super();

        music.loadSong(musicFile);

        beatMap = new BeatMap(beatFile);
        timer = new Stopwatch();

        rng = new Random();
    }

    public void reset()
    {
        beatMap.reset();
        music.getMusic().stop();
        music.getMusic().play();
        timer.stop();
        timer.start();
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