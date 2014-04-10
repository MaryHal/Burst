package fp.infiniteset.Burst;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;

/* import fp.infiniteset.Burst.Screens.TestScreen; */
import fp.infiniteset.Burst.Screens.MainMenu;
import fp.infiniteset.Burst.Screens.AutoScreen;
import fp.infiniteset.Burst.Screens.SimpleScreen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import fp.infiniteset.Burst.Utils.FreeTypeFontLoader;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class MainGame extends Game
{
    public static final int VIRTUAL_WIDTH  = 480;
    public static final int VIRTUAL_HEIGHT = 320;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH / (float)VIRTUAL_HEIGHT;
    public static final Rectangle viewport = new Rectangle(0.0f, 0.0f, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    public AssetManager manager;

    public MainMenu mainMenu;
    public AutoScreen autoScreen;
    public SimpleScreen simpleScreen;

    @Override
    public void create()
    {
        manager = new AssetManager();
        manager.setLoader(BitmapFont.class, new FreeTypeFontLoader(new InternalFileHandleResolver()));
        manager.load("fonts/DroidSansFallback.ttf", BitmapFont.class);

        /* testScreen = new TestScreen(this); */
        mainMenu = new MainMenu(this);
        autoScreen = new AutoScreen(this);
        simpleScreen = new SimpleScreen(this);

        this.setScreen(mainMenu);
    }

    @Override
    public void dispose()
    {
        /* testScreen.dispose(); */
        mainMenu.dispose();
        autoScreen.dispose();
        simpleScreen.dispose();

        manager.dispose();
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
