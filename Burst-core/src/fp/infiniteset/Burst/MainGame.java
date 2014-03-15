package fp.infiniteset.Burst;

import com.badlogic.gdx.Game;

public class MainGame extends Game
{
    public static final int VIRTUAL_WIDTH  = 480;
    public static final int VIRTUAL_HEIGHT = 320;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH / (float)VIRTUAL_HEIGHT;

    private TestScreen testScreen;

    @Override
    public void create()
    {
        testScreen = new TestScreen(this);
        this.setScreen(testScreen);
    }

    @Override
    public void dispose()
    {
        testScreen.dispose();
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
