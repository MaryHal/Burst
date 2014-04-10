package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.Gdx;
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

    private Firework(Vector2 position, Vector2 destination)
    {
        this.position = position;
        this.destination = destination;
        this.velocity = destination.cpy().sub(position);

        this.sprite = new ShapeRenderer();
        this.alive = false;
    }

    public void set(Vector2 position, Vector2 destination)
    {
        this.position = position;
        this.destination = destination;
        this.velocity = destination.cpy().sub(position);

        this.alive = true;
    }

    @Override
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

    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getDestination()
    {
        return destination;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void update(float delta)
    {
        position.add(velocity.cpy().scl(delta));
    }

    // Returns true if inside window, false if outside
    public boolean checkBoundary()
    {
        if (position.x < 0 && position.x >= 480)
        {
            if (position.y < 0 && position.y >= 320)
            {
                return false;
            }
        }
        return true;
    }

    // Returns true if far from destination
    public boolean checkCloseness()
    {
        return !position.epsilonEquals(destination, 5.0f);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.setProjectionMatrix(batch.getProjectionMatrix());

        sprite.begin(ShapeType.Filled);
        sprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        sprite.rect(position.x - 2, position.y - 2, 4, 4);

        sprite.setColor(0.3f, 0.3f, 0.3f, 1.0f);
        sprite.rect(destination.x - 2, destination.y - 2, 4, 4);
        sprite.end();

        if (position.y > destination.y)
        {
            Gdx.gl20.glLineWidth(2.0f);
            sprite.begin(ShapeType.Line);
            sprite.setColor(0.15f, 0.15f, 0.15f, 1.0f);
            sprite.circle(destination.x, destination.y, position.dst(destination));
            sprite.end();
        }
    }
}

