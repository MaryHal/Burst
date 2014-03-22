package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.math.Vector2;

import fp.infiniteset.Burst.FireworkLauncher;
import fp.infiniteset.Burst.Menu;

import java.util.Random;

public class MainMenu implements Screen
{
    private MainGame game;
    private OrthographicCamera camera;
    private BitmapFont font;

    private FireworkLauncher launcher;
    private Menu menu;
    private Random rng;

    public MainMenu(MainGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        camera = new OrthographicCamera(MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(true, MainGame.VIRTUAL_WIDTH, MainGame.VIRTUAL_HEIGHT);
        camera.update();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DroidSansFallback.ttf"));
        font = generator.generateFont(12, FreeTypeFontGenerator.DEFAULT_CHARS, true);
        generator.dispose();

        launcher = new FireworkLauncher(camera);

        // Generate Menu
        menu = new Menu(camera, font, 100.0f, 100.0f);
        FileHandle[] beatFiles = Gdx.files.external(".config/Burst/music").list(".beats");
        for (FileHandle file : beatFiles)
        {
            menu.addItem(file.nameWithoutExtension());
        }

        rng = new Random();

        InputAdapter adapter = new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                return menu.handleKeyEvent(keycode);
            }

            @Override
            public boolean keyUp(int keycode)
            {
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button)
            {
                return menu.handleTouchEvent(x, y, pointer, button);
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
    public void resize(int width, int height)
    {
    }

    @Override
    public void dispose()
    {
        menu.dispose();
    }

    @Override
    public void render(float delta)
    {
        camera.update();

        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        launcher.draw(delta);

        if (rng.nextInt(45) == 0)
        {
            Vector2 position    = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
            Vector2 destination = new Vector2(rng.nextFloat() * 200 + 140,
                    rng.nextFloat() * 80 + 80);
            launcher.fire(position, destination);
        }

        menu.draw(delta);

        /* menu.draw(delta); */
        /* launcher.draw(delta); */
    }
}
