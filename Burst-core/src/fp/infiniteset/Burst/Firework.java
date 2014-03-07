package fp.infiniteset.Burst;

/* import com.badlogic.gdx.graphics.Camera; */
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Pool;

public class Firework implements Pool.Poolable
{
    private Vector2 position;
    private Vector2 destination;
    private Vector2 velocity;
    private ShapeRenderer sprite;

    private boolean alive;

    public Firework()
    {
        this(Vector2.Zero, Vector2.Zero);
    }

    public Firework(Vector2 position, Vector2 destination)
    {
        this.position = position;
        this.destination = destination;
        this.velocity = position.cpy().lerp(destination, 1.00f);

        this.sprite = new ShapeRenderer();
        this.alive = false;
    }

    public void set(Vector2 position, Vector2 destination)
    {
        this.position = position;
        this.destination = destination;
        this.velocity = position.cpy().lerp(destination, 1.00f);

        this.alive = true;
    }

    public void reset()
    {
        position = Vector2.Zero;
        destination = Vector2.Zero;
        velocity = Vector2.Zero;
    }

    public float getDistance2()
    {
        return position.dst2(destination);
    }

    public Vector2 getDestination()
    {
        return destination;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void update(float delta)
    {
        position.add(velocity.cpy().scl(delta));

        if (position.epsilonEquals(destination, 5.0f))
        {
            alive = false;
        }
    }

    public void draw(SpriteBatch batch)
    {
        sprite.setProjectionMatrix(batch.getProjectionMatrix());
        sprite.begin(ShapeType.Filled);
        sprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        sprite.rect(position.x - 2, position.y - 2, 4, 4);
        sprite.end();
    }
}

