package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.math.Vector2;

public class TestScreen implements Screen
{
    private BurstGame game;

    private OrthographicCamera camera;
    private FireworkLauncher launcher;
    private MusicController musicController;

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
        /* musicController.play(); */

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
                return false;
            }

            public boolean touchUp(int x, int y, int pointer, int button)
            {
                launcher.fire(new Vector2(x, y));
                return true;
            }
        };
        Gdx.input.setInputProcessor(adapter);

        System.out.println("All Set!");
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
        launcher.dispose();
    }
}
