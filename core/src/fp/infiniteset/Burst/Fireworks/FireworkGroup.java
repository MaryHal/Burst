package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class FireworkGroup
{
    private int comboIndex;
    private ArrayList<Firework> comboList;

    public FireworkGroup(ArrayList<Firework> comboList)
    {
        this.comboIndex = 0;
        this.comboList = comboList;
    }

    public void addToList(Firework f)
    {
        comboList.add(f);
    }

    public void draw(SpriteBatch batch)
    {
    }
}

