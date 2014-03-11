package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class TestScreen implements Screen
{
    private BurstGame game;

    private OrthographicCamera camera;

    private FireworkLauncher launcher;
    private MusicController musicController;

    /* private BeatEditor beatEditor; */
    private BeatMap beatMap;
    private Stopwatch timer;

    private Random rng;

    // constructor to keep a reference to the main Game class
    public TestScreen(BurstGame game)
    {
        this.game = game;
    }

    @Override
    public void render(float delta)
    {
        camera.update();

        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (beatMap.getNextBeat() != null)
        {
            while (timer.getTime() > beatMap.getNextBeat().time - 1.0f)
            {
                Vector2 position    = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
                Vector2 destination = new Vector2(rng.nextFloat() * 200 + 140,
                                                  rng.nextFloat() * 80 + 80);

                launcher.fire(position, destination);
                beatMap.popBeat();
            }
        }
        else
        {
            beatMap.reset();
            musicController.getMusic().stop();
            musicController.getMusic().play();
            timer.stop();
            timer.start();
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
        camera = new OrthographicCamera(BurstGame.VIRTUAL_WIDTH, BurstGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(true, BurstGame.VIRTUAL_WIDTH, BurstGame.VIRTUAL_HEIGHT);
        camera.update();

        launcher = new FireworkLauncher(camera);
        musicController = new MusicController();
        musicController.loadSong(Gdx.files.external(".config/Burst/music/FuwaFuwa.mp3"));
        musicController.play();

        /* beatEditor = new BeatEditor(); */
        beatMap = new BeatMap(Gdx.files.external(".config/Burst/music/FuwaFuwa_hard.beats"));
        timer = new Stopwatch();
        timer.start();

        rng = new Random();

        InputAdapter adapter = new InputAdapter()
        {
            @Override
            public boolean keyUp(int keycode)
            {
                if (keycode == Keys.A)
                {
                    Vector2 position = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
                    Vector2 destination = new Vector2(Gdx.graphics.getWidth()  / 2,
                                              Gdx.graphics.getHeight() / 2);
                    launcher.fire(position, destination);

                    return true;
                }
                else if (keycode == Keys.Q)
                {
                    /* beatEditor.addBeat(timer.getTime(), 1); */
                    launcher.detonate(new Vector2(Gdx.graphics.getWidth()  / 2,
                                                  Gdx.graphics.getHeight() / 2));

                    return true;
                }
                else if (keycode == Keys.R)
                {
                    beatMap.reset();
                    musicController.getMusic().stop();
                    musicController.getMusic().play();
                    timer.stop();
                    timer.start();
                }

                return false;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button)
            {
                if (button == Input.Buttons.LEFT)
                {
                    Vector2 position = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
                    Vector3 destination = new Vector3(x, y, 0.0f);
                    camera.unproject(destination);

                    launcher.fire(position, new Vector2(destination.x, destination.y));

                    return true;
                }
                return false;
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
