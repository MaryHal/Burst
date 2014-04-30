package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

public class FireworkGroup implements Pool.Poolable
{
    private int comboIndex;
    private ArrayList<Firework> comboList;

    public void reset()
    {
        comboIndex = 0;
        comboList.clear();
    }

    public void addToList(Firework f)
    {
        comboList.add(f);
    }

    public void clearList(FireworkPool fireworkPool)
    {
        for (int i = 0; i < comboList.size(); i++)
        {
            fireworkPool.free(comboList.get(i));
        }
        reset();
    }
}

