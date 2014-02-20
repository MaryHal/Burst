package fp.infiniteset.particle;

import java.util.Stack;
import android.opengl.GLES20;

public class Emitter
{
    public static final int MAX_SETS = 16;
    private PointCloud[] particleSets;
    private Stack<PointCloud> freeStack;

    public Emitter()
    {
        particleSets = new PointCloud[MAX_SETS];
        freeStack = new Stack<PointCloud>();
    }

    public void addSet(PointCloud p)
    {
    }
}

