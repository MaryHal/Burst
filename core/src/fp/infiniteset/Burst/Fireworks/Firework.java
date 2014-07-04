package fp.infiniteset.Burst.Fireworks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;

public class Firework implements Pool.Poolable
{
    private static final int pointRadius = 3;

    private Vector2 position;
    private Vector2 destination;
    private Vector2 velocity;

    private ShapeRenderer sprite;
    private Firework next;
    private float lineAlpha;

    private boolean active; // Is this beat coming up next?
    private boolean alive;

    public Firework()
    {
        this(Vector2.Zero, Vector2.Zero);
    }

    private Firework(Vector2 position, Vector2 destination)
    {
        this.position = position;
        this.destination = destination;
        // this.velocity = destination.cpy().sub(position);
        this.velocity = Vector2.Zero;

        this.sprite = new ShapeRenderer();

        this.next = null;
        this.lineAlpha = 0.0f;

        this.active = false;
        this.alive = false;
    }

    public void set(Vector2 position, Vector2 destination, Firework next)
    {
        this.position = position;
        this.destination = destination;
        // this.velocity = destination.cpy().sub(position);
        this.velocity = Vector2.Zero;
        this.next = next;
        this.lineAlpha = 0.05f;

        this.active = false;
        this.alive = true;
    }

    public void setActive()
    {
        this.active = true;
    }

    public void launch()
    {
        this.velocity = destination.cpy().sub(position);
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
        if (alive)
        {
            position.add(velocity.cpy().scl(delta));

            if (velocity.len2() > 0.0f)
            {
                lineAlpha += delta;
            }
        }
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
        if (!alive)
            return;

        sprite.setProjectionMatrix(batch.getProjectionMatrix());

        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Connecting line
        if (next != null)
        {
            Gdx.gl20.glLineWidth(pointRadius);

            sprite.begin(ShapeType.Line);
            {
                sprite.setColor(1.0f, 1.0f, 1.0f, lineAlpha);
                sprite.line(destination.x, destination.y,
                            next.getDestination().x, next.getDestination().y);
            }
            sprite.end();
        }

        sprite.begin(ShapeType.Filled);
        {
            sprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            sprite.rect(position.x - pointRadius, position.y - pointRadius, 2*pointRadius, 2*pointRadius);

            if (active)
                sprite.setColor(1.0f, 1.0f, 0.0f, 0.8f);
            else
                sprite.setColor(0.3f, 0.3f, 0.3f, 1.0f);
            sprite.rect(destination.x - pointRadius, destination.y - pointRadius, 2*pointRadius, 2*pointRadius);
        }
        sprite.end();

        Gdx.gl20.glDisable(GL20.GL_BLEND);

        // // Bounding Circle
        // if (position.y > destination.y)
        // {
        //     Gdx.gl20.glLineWidth(2.0f);
        //     sprite.begin(ShapeType.Line);
        //     {
        //         sprite.setColor(0.15f, 0.15f, 0.15f, 1.0f);
        //         sprite.circle(destination.x, destination.y, position.dst(destination));
        //     }
        //     sprite.end();
        // }
    }
}

