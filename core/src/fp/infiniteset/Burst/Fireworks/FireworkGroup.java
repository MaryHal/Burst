package fp.infiniteset.Burst.Fireworks;

import java.util.ArrayList;

public class FireworkGroup
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
}

