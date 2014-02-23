package fp.infiniteset.Burst;

import com.badlogic.gdx.Game;

public class BurstGame extends Game
{
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
    }

    /* @Override */
    /* public void render()  */
    /* { */
    /* } */

    @Override
    public void resize(int width, int height) 
    {
    }

    @Override
    public void pause() 
    {
    }

    @Override
    public void resume() 
    {
    }
}
