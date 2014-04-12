package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.utils.Sort;
import java.util.Comparator;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.assets.AssetManager;

import fp.infiniteset.Burst.Fireworks.Firework;
import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Utils.Menu;

import java.util.Random;

public class MainMenu implements Screen
{
    private MainGame game;
    private AssetManager assets;

    private BitmapFont font;

    private FireworkLauncher launcher;
    private Menu menu;
    private Random rng;

    public MainMenu(MainGame game, AssetManager assets)
    {
        this.game = game;
        this.assets = assets;
    }

    @Override
    public void show()
    {
        FreeTypeFontGenerator generator = assets.get("fonts/DroidSansFallback.ttf", FreeTypeFontGenerator.class);
        FreeTypeFontParameter parameters = new FreeTypeFontParameter();
        parameters.size = 11;
        parameters.flip = true;
        font = generator.generateFont(parameters);

        launcher = new FireworkLauncher(game.camera)
        {
            @Override
            public void updateFirework(Firework f, float delta)
            {
                f.update(delta);
                f.setAlive(f.checkBoundary() && f.checkCloseness());

                if (!f.isAlive())
                {
                    detonate(f);
                }
            }
        };

        // Generate Menu
        menu = new Menu(game.camera, font, 16.0f, 16.0f);
        menu.addItem("Song List:", false);

        // Get beatfiles and sort 'em
        FileHandle[] beatFiles = Gdx.files.external(".config/Burst/music").list(".beats");
        Sort.instance().sort(beatFiles, 
                new Comparator<FileHandle>()
                {
                    @Override
                    public int compare(FileHandle o1, FileHandle o2)
                    {
                        return o1.name().compareTo(o2.name());
                    }
                });

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
                return menu.handleKeyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode)
            {
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button)
            {
                return menu.handleTouchDown(x, y, pointer, button);
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
        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        launcher.draw(delta);

        if (rng.nextInt(45) == 0)
        {
            Vector2 position    = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
            Vector2 destination = new Vector2(rng.nextFloat() * 200 + 140,
                    rng.nextFloat() * 80 + 80);
            Firework f = launcher.fire(position, destination, null);
            f.launch();
        }

        if (menu.isSelected())
        {
            String name = menu.getSelection();
            game.simpleScreen.loadFiles(
                    Gdx.files.external(".config/Burst/music/" + name + ".mp3"),
                    Gdx.files.external(".config/Burst/music/" + name + ".beats"));
            game.setScreen(game.simpleScreen);
        }
        menu.draw(delta);
    }
}
