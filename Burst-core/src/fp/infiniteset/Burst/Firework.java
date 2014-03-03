package fp.infiniteset.Burst;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.utils.Pool;

public class Firework implements Pool.Poolable
{
    private Vector2 position;
    /* private Vector2 destination; */
    private Vector2 velocity;
    private ShapeRenderer sprite;

    public Firework()
    {
        this(Vector2.Zero, Vector2.Zero);
    }

    public Firework(Vector2 position, Vector2 destination)
    {
        this.position = position;
        /* this.destination = destination; */
        this.velocity = position.cpy().lerp(destination, 0.05f);

        sprite = new ShapeRenderer();
    }

    public void reset()
    {
        position = Vector2.Zero;
        velocity = Vector2.Zero;
    }

    public void update(float delta)
    {
        position.add(velocity.cpy().scl(delta));
    }

    public void draw(Camera camera)
    {
        sprite.setProjectionMatrix(camera.combined);
        sprite.begin(ShapeType.Filled);
        sprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        sprite.rect(position.x - 2, position.y - 2, 4, 4);
        sprite.end();
    }
}

