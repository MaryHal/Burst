package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;
import fp.infiniteset.Burst.SimpleGame;

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

    private SimpleGame simpleGame;
    private BitmapFont font;
    private SpriteBatch textBatch;

    public SimpleScreen(MainGame game, AssetManager assets)
    {
        this.game = game;
        this.assets = assets;
    }

    public void loadFiles(FileHandle musicFile, FileHandle beatFile)
    {
        simpleGame = new SimpleGame(musicFile, beatFile);
        if (simpleGame != null)
            simpleGame.reset();
    }

    @Override
    public void show()
    {
        if (simpleGame != null)
            simpleGame.reset();

        InputAdapter adapter = new InputAdapter()
        {
            @Override
            public boolean keyDown(int keycode)
            {
                return simpleGame.handleKeyDown(keycode);
            }

            @Override
            public boolean keyUp(int keycode)
            {
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button)
            {
                return simpleGame.handleTouchDown(x, y, pointer, button);
            }
        };
        Gdx.input.setInputProcessor(adapter);

        FreeTypeFontGenerator generator = assets.get("fonts/DroidSansFallback.ttf", FreeTypeFontGenerator.class);
        FreeTypeFontParameter parameters = new FreeTypeFontParameter();
        parameters.size = 12;
        parameters.flip = true;
        font = generator.generateFont(parameters);
        
        textBatch = new SpriteBatch(64);
        textBatch.setProjectionMatrix(game.camera.combined);
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

        simpleGame.render(delta);

        textBatch.begin();
        {
            font.draw(textBatch, "Score: " + simpleGame.getScore(), 4.0f, 4.0f);
        }
        textBatch.end();
    }
}

