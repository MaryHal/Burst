package fp.infiniteset.Burst;

import com.badlogic.gdx.files.FileHandle;

import fp.infiniteset.Burst.Beats.BeatMap;
import fp.infiniteset.Burst.Utils.Stopwatch;

public class SimpleGame extends GameController
{
    private BeatMap beatMap;
    private Stopwatch timer;
    private int score;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super();
        score = 0;

        music.loadSong(musicFile);

        beatMap = new BeatMap(beatFile);
        timer = new Stopwatch();
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
    }
}
