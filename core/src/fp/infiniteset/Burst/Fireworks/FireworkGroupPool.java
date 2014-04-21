package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.utils.Pool;

public class FireworkGroupPool extends Pool<FireworkGroup>
{
    public FireworkGroupPool()
    {
        super();
    }

    public FireworkGroupPool(int initialCapacity)
    {
        super(initialCapacity);
    }

    public FireworkGroupPool(int initialCapacity, int max)
    {
        super(initialCapacity, max);
    }

    @Override
    protected FireworkGroup newObject()
    {
        return new FireworkGroup();
    }
}
