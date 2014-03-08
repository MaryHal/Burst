package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.utils.Timer;

import com.badlogic.gdx.math.Vector2;

public class TestScreen implements Screen
{
    private BurstGame game;

    private OrthographicCamera camera;
    private FireworkLauncher launcher;
    private MusicController musicController;

    /* private BeatEditor beatEditor; */
    private BeatMap beatMap;
    private Stopwatch timer;

    // constructor to keep a reference to the main Game class
    public TestScreen(BurstGame game)
    {
        this.game = game;
    }

    @Override
    public void render(float delta)
    {
        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (beatMap.getNextBeat() != null)
        {
            if (timer.getTime() > beatMap.getNextBeat().time - 2.0f)
            {
                beatMap.popBeat();
                launcher.fire(new Vector2(Gdx.graphics.getWidth()  / 2,
                                          Gdx.graphics.getHeight() / 2));
            }
        }
        launcher.draw(delta);
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void show()
    {
        // Called when this screen is set as the screen with game.setScreen();
        // System.out.println("Opengl ES 2.0: " + (Gdx.graphics.isGL20Available() ? "Supported!" : "Unsupported."));
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        launcher = new FireworkLauncher(camera);
        musicController = new MusicController();
        musicController.play();

        /* beatEditor = new BeatEditor(); */
        beatMap = new BeatMap(Gdx.files.external(".config/Burst/music/Melodica.beats"));
        timer = new Stopwatch();
        timer.start();

        InputAdapter adapter = new InputAdapter()
        {
            public boolean keyUp(int keycode)
            {
                if (keycode == Keys.A)
                {
                    launcher.fire(new Vector2(Gdx.graphics.getWidth()  / 2,
                                              Gdx.graphics.getHeight() / 2));

                    return true;
                }
                else if (keycode == Keys.Q)
                {
                    /* beatEditor.addBeat(timer.getTime(), 1); */
                    launcher.detonate(new Vector2(Gdx.graphics.getWidth()  / 2,
                                                  Gdx.graphics.getHeight() / 2));

                    return true;
                }

                return false;
            }

            public boolean touchUp(int x, int y, int pointer, int button)
            {
                launcher.fire(new Vector2(x, y));
                return true;
            }
        };
        Gdx.input.setInputProcessor(adapter);
    }

    @Override
    public void hide()
    {
        // called when current screen changes from this to a different screen
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        /* beatEditor.writeFile(Gdx.files.external(".config/Burst/music/Melodica.beats"), true); */

        launcher.dispose();
        musicController.dispose();
    }
}
