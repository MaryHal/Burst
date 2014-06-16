package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;
import fp.infiniteset.Burst.SimpleController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleScreen implements Screen
{
    private MainGame game;
    private AssetManager assets;

    private SimpleController simpleController;
    private BitmapFont font;
    private SpriteBatch hudBatch;

    public SimpleScreen(MainGame game, AssetManager assets)
    {
        this.game = game;
        this.assets = assets;
    }

    public void loadFiles(FileHandle musicFile, FileHandle beatFile)
    {
        simpleController = new SimpleController(musicFile, beatFile);
        if (simpleController != null)
            simpleController.reset();
    }

    @Override
    public void show()
    {
        if (simpleController != null)
            simpleController.reset();

        InputAdapter adapter = new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                return simpleController.handleKeyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode)
            {
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button)
            {
                return simpleController.handleTouchDown(x, y, pointer, button);
            }
        };
        Gdx.input.setInputProcessor(adapter);

        FreeTypeFontGenerator generator = assets.get("fonts/DroidSansFallback.ttf", FreeTypeFontGenerator.class);
        FreeTypeFontParameter parameters = new FreeTypeFontParameter();
        parameters.size = 12;
        parameters.flip = true;
        font = generator.generateFont(parameters);

        hudBatch = new SpriteBatch(64);
        hudBatch.setProjectionMatrix(game.camera.combined);
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
    }

    @Override
    public void render(float delta)
    {
        // update and draw stuff
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        simpleController.render(delta);

        hudBatch.begin();
        {
            font.draw(hudBatch, String.format("Time: %.2f", simpleController.getSongPosition()), 4.0f, 4.0f);
            font.draw(hudBatch, String.format("Score: %d (%+d)", 
                                              simpleController.getScore(), 
                                              simpleController.getScoreDifference()), 
                                              4.0f, 18.0f);
            font.draw(hudBatch, "Combo: " + simpleController.getCombo(), 4.0f, 32.0f);
        }
        hudBatch.end();
    }
}

