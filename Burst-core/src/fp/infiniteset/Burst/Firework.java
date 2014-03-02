package fp.infiniteset.Burst;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Firework
{
    private Vector2 position;
    /* private Vector2 destination; */
    private Vector2 velocity;
    private ShapeRenderer sprite;

    public Firework(Vector2 position, Vector2 destination)
    {
        this.position = position;
        /* this.destination = destination; */
        this.velocity = position.cpy().lerp(destination, 0.05f);

        sprite = new ShapeRenderer();
    }

    public void update(float delta)
    {
        position.add(velocity.cpy().scl(delta));
        System.out.println(velocity);
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

