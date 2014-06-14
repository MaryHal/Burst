package fp.infiniteset.Burst;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.OrthographicCamera;

/* import fp.infiniteset.Burst.Screens.TestScreen; */
import fp.infiniteset.Burst.Screens.LoadingScreen;
import fp.infiniteset.Burst.Screens.MainMenu;
import fp.infiniteset.Burst.Screens.SimpleScreen;

import fp.infiniteset.Burst.Utils.FreeTypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class MainGame extends Game
{
    public static final int VIRTUAL_WIDTH  = 640;
    public static final int VIRTUAL_HEIGHT = 480;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH / (float)VIRTUAL_HEIGHT;
    public static final Rectangle viewport = new Rectangle(0.0f, 0.0f, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    public OrthographicCamera camera;
    public AssetManager assets;

    public LoadingScreen loadingScreen;
    public MainMenu mainMenu;
    public SimpleScreen simpleScreen;

    @Override
    public void create()
    {
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.setToOrtho(true, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        camera.update();

        assets = new AssetManager();
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontLoader(new InternalFileHandleResolver()));

        loadingScreen = new LoadingScreen(this, assets);
        mainMenu      = new MainMenu(this, assets);
        simpleScreen  = new SimpleScreen(this, assets);

        this.setScreen(loadingScreen);
    }

    @Override
    public void dispose()
    {
        assets.dispose();

        loadingScreen.dispose();
        mainMenu.dispose();
        simpleScreen.dispose();
    }

    // @Override
    // public void render()
    // {
    // }

    // @Override
    // public void resize(int width, int height)
    // {
    // }

    // @Override
    // public void pause()
    // {
    // }

    // @Override
    // public void resume()
    // {
    // }
}
