package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;
import fp.infiniteset.Burst.SimpleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.graphics.GL20;

public class SimpleScreen implements Screen
{
    private MainGame game;
    private SimpleGame simpleGame;

    public SimpleScreen(MainGame game)
    {
        this.game = game;
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
    }
}

