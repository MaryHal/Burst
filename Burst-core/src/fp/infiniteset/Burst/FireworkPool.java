package fp.infiniteset.Burst;

import com.badlogic.gdx.utils.Pool;

public class FireworkPool extends Pool<Firework>
{
    public FireworkPool()
    {
        super();
    }

    public FireworkPool(int initialCapacity)
    {
        super(initialCapacity);
    }

    public FireworkPool(int initialCapacity, int max)
    {
        super(initialCapacity, max);
    }

    protected Firework newObject()
    {
        return new Firework();
    }
}
