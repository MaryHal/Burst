package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class FireworkGroup implements Pool.Poolable
{
    private ArrayList<Firework> comboList;

    public void reset()
    {
        comboList.clear();
    }
}
