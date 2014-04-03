package fp.infiniteset.Burst;

import com.badlogic.gdx.graphics.OrthographicCamera;

import fp.infiniteset.Burst.MusicController;
import fp.infiniteset.Burst.Fireworks.FireworkLauncher;

public abstract class GameController
{
    protected OrthographicCamera camera;
    protected FireworkLauncher launcher;
    protected MusicController music;
    protected int score;

    protected GameController()
    {
        camera = new OrthographicCamera(MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(true, MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.update();

        initializeLauncher();

        music = new MusicController();
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
}

