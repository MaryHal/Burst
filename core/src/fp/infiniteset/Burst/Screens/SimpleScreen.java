package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;
import fp.infiniteset.Burst.SimpleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

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
    }

}

