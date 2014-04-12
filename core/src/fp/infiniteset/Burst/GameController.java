package fp.infiniteset.Burst;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Beats.BeatMap;
import fp.infiniteset.Burst.Utils.Stopwatch;

import java.util.Random;

public abstract class GameController implements Disposable
{
    protected OrthographicCamera camera;
    protected FireworkLauncher launcher;
    protected BeatMap beatMap;
    protected Stopwatch timer;
    protected Music music;

    protected Random rng;
    protected int score;

    protected GameController(FileHandle file, FileHandle beatFile)
    {
        camera = new OrthographicCamera(MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(true, MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.update();

        initializeLauncher();

        beatMap = new BeatMap(beatFile);
        timer = new Stopwatch();

        music = Gdx.audio.newMusic(file);
        music.setLooping(false);

        rng = new Random();
    }

    @Override
    public void dispose()
    {
        music.dispose();
    }

    public void reset()
    {
        beatMap.reset();
        music.stop();
        music.play();
        timer.stop();
        timer.start();
    }

    // More explicit method to enforce that launcher is overridden
    public abstract void initializeLauncher();

    public abstract boolean handleKeyDown(int keycode);
    public abstract boolean handleTouchDown(int x, int y, int pointer, int button);
    public abstract void render(float delta);

    public int getScore()
    {
        return score;
    }

    public float getSongPosition()
    {
        return music.getPosition();
    }
}

