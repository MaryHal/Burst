package fp.infiniteset.Burst.Screens;

import fp.infiniteset.Burst.MainGame;

// import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.InputAdapter;
// import com.badlogic.gdx.files.FileHandle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

// For a game of this scale, this is probably unnecessary.
public class LoadingScreen implements Screen
{
    private MainGame game;
    private AssetManager assets;

    public LoadingScreen(MainGame game, AssetManager assets)
    {
        this.game = game;
        this.assets = assets;
    }

    @Override
    public void show()
    {
        assets.load("fonts/DroidSansFallback.ttf", FreeTypeFontGenerator.class);
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
        if (assets.update())
        {
            // Done loading, move on!
            game.setScreen(game.mainMenu);
        }

        // float progress = assets.getProgress();
    }
}
