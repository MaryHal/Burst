package fp.infiniteset.Burst;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;

/* import fp.infiniteset.Burst.Screens.TestScreen; */
import fp.infiniteset.Burst.Screens.MainMenu;

public class MainGame extends Game
{
    public static final int VIRTUAL_WIDTH  = 480;
    public static final int VIRTUAL_HEIGHT = 320;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH / (float)VIRTUAL_HEIGHT;
    public static final Rectangle viewport = new Rectangle(0.0f, 0.0f, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

    /* private TestScreen testScreen; */
    private MainMenu mainMenu;

    @Override
    public void create()
    {
        /* testScreen = new TestScreen(this); */
        mainMenu = new MainMenu(this);
        this.setScreen(mainMenu);
    }

    @Override
    public void dispose()
    {
        /* testScreen.dispose(); */
        mainMenu.dispose();
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
