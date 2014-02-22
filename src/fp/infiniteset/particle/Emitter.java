package fp.infiniteset.particle;

import java.util.ArrayDeque;
/* import android.opengl.GLES20; */

public class Emitter
{
    public static final int MAX_SETS = 16;

    private PointCloud[] particleSets;
    private ArrayDeque<PointCloud> freeStack;

    public Emitter()
    {
        particleSets = new PointCloud[MAX_SETS];
        freeStack = new ArrayDeque<PointCloud>(MAX_SETS);

        for (PointCloud p : particleSets)
        {
            freeStack.push(p);
        }
    }

    public void add(ParticleInitializer initializer)
    {
        if (freeStack.isEmpty())
        {
            throw new ArrayStoreException("No more free slots available in Emitter");
        }
        PointCloud p = freeStack.pop();
        initializer.setParticle(p);
        p.alive = true;
    }

    public void destroy(PointCloud p)
    {
        p.alive = false;
        freeStack.push(p);
    }

    public void update()
    {
        for (PointCloud p : particleSets)
        {
            p.update();
        }
    }

    public void draw(float[] mvpMatrix)
    {
        for (PointCloud p : particleSets)
        {
            p.draw(mvpMatrix);
        }
    }
}

