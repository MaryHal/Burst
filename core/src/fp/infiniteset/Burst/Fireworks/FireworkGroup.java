package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class FireworkGroup
{
    private float tailTime;
    private int comboIndex;
    private Array<Firework> comboList;

    public FireworkGroup()
    {
        this.tailTime = 0.0f;
        this.comboIndex = 0;
        this.comboList = new Array<Firework>();
    }

    public FireworkGroup(float tailTime, Array<Firework> comboList)
    {
        this.tailTime = tailTime;
        this.comboIndex = 0;
        this.comboList = comboList;

        placeFireworks();
    }

    public void addToCombo(float time, Firework f)
    {
        comboList.add(f);
        tailTime = time;
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

    public void draw(SpriteBatch batch, ShapeRenderer renderer)
    {
        renderer.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.begin(ShapeType.Line);
        {
            for (int i = 0; i < comboList.size - 1; i++)
            {
                Firework f1 = comboList.get(i);
                Firework f2 = comboList.get(i+1);
                renderer.line(f1.getDestination().x, f1.getDestination().y,
                        f2.getDestination().x, f2.getDestination().y);
            }

        }
        renderer.end();

        Gdx.gl20.glDisable(GL20.GL_BLEND);
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

