package fp.infiniteset.Burst;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Beats.BeatMap;
import fp.infiniteset.Burst.Utils.Stopwatch;

public abstract class GameController implements Disposable
{
    protected OrthographicCamera camera;

    protected FireworkLauncher launcher;
    protected BeatMap beatMap;
    protected BeatMap.Beat[] beatList;
    protected int runningIndex;
    protected int queuedIndex;

    protected Stopwatch timer;
    protected Music music;

    protected int score;
    protected int scoreDiff;
    protected int combo;

    protected GameController(FileHandle file, FileHandle beatFile)
    {
        camera = new OrthographicCamera(MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(true, MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.update();

        initializeLauncher();

        beatMap = new BeatMap(beatFile);
        beatList = beatMap.getBeatArray();
        runningIndex = 0;
        queuedIndex = 0;

        timer = new Stopwatch();

        music = Gdx.audio.newMusic(file);
        music.setVolume(0.5f);
        music.setLooping(false);

        score = 0;
        combo = 0;
    }

    @Override
    public void dispose()
    {
        music.dispose();
    }

    public void reset()
    {
        // beatList = beatMap.getNewBeatList();
        runningIndex = 0;

        music.stop();
        music.play();
        timer.reset();

        score = 0;
        combo = 0;
    }

    // More explicit method to enforce that launcher is overridden
    public abstract void initializeLauncher();

    public abstract boolean handleKeyDown(int keycode);
    public abstract boolean handleTouchDown(int x, int y, int pointer, int button);
    public abstract void render(float delta);

    public int getScoreDifference()
    {
        return scoreDiff;
    }

    public int getScore()
    {
        return score;
    }

    public int getCombo()
    {
        return combo;
    }

    public float getSongPosition()
    {
        return music.getPosition();
    }
}

