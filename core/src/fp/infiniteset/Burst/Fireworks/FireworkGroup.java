package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class FireworkGroup
{
    private float headTime;
    private float tailTime;
    private int comboIndex;
    private Array<Firework> comboList;

    public FireworkGroup()
    {
        this.headTime = 0.0f;
        this.tailTime = 0.0f;
        this.comboIndex = 0;
        this.comboList = new Array<Firework>();
    }

    public FireworkGroup(float headTime, float tailTime, Array<Firework> comboList)
    {
        this.headTime = headTime;
        this.tailTime = tailTime;
        this.comboIndex = 0;
        this.comboList = comboList;

        placeFireworks();
    }

    public void addToCombo(float time, Firework f)
    {
        if (headTime == 0)
        {
            headTime = time;
        }

        comboList.add(f);
        tailTime = time;
    }

    public float getHeadTime()
    {
        return headTime;
    }

    public float getTailTime()
    {
        return tailTime;
    }

    public Array<Firework> getComboList()
    {
        return comboList;
    }

    public void pushComboIndex()
    {
        comboIndex++;
    }

    public boolean isCompleted()
    {
        return comboIndex >= comboList.size;
    }

    public Firework getCurrentFirework()
    {
        // if (comboIndex > comboList.size)
        //     throw new IndexOutOfBoundsException();

        return comboList.get(comboIndex);
    }

    public void draw(SpriteBatch batch)
    {
    }

    public void placeFireworks()
    {
        float x = MathUtils.random(100, 440);
        float y = MathUtils.random(120, 260);
        float radius = MathUtils.random(40 + comboList.size * 2, 60 + comboList.size * 2);
        float offset = MathUtils.random(0, MathUtils.PI2);
        int dir = MathUtils.randomBoolean() == true ? 1 : -1;

        for (int i = 0; i < comboList.size; i++)
        {
            float radians = offset + dir * i * MathUtils.PI * 2 / comboList.size;

            Vector2 position = new Vector2(x, 479.0f);
            Vector2 destination = new Vector2((float)(x + Math.cos(radians) * radius),
                    (float)(y + Math.sin(radians) * radius));

            comboList.get(i).set(position, destination);
        }
    }

    public void launchAll()
    {
        for (Firework f : comboList)
        {
            f.launch();
        }
    }
}

