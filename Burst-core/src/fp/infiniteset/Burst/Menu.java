package fp.infiniteset.Burst;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

public class Menu
{
    private class MenuItem
    {
        public String label;
        public Rectangle bounds;

        public MenuItem(String label, Rectangle bounds)
        {
            this.label = label;
            this.bounds = bounds;
        }
    }

    private SpriteBatch menuBatch;
    private Camera camera;

    private BitmapFont font;
    private ArrayList<MenuItem> items;

    private Vector2 offset;
    private int index;
    private boolean selected;

    public Menu(Camera camera, BitmapFont font, float x, float y)
    {
        this.camera = camera;
        this.font = font;

        offset = new Vector2(x, y);

        menuBatch = new SpriteBatch(64);
        menuBatch.setProjectionMatrix(camera.combined);
        items = new ArrayList<MenuItem>();

        index = 0;
        selected = false;
    }

    public void dispose()
    {
        menuBatch.dispose();
    }

    public void addItem(String label)
    {
        BitmapFont.TextBounds textBounds = font.getBounds(label);
        Rectangle old = (items.isEmpty() ? new Rectangle(offset.x, offset.y, 0.0f, 0.0f) : items.get(items.size() - 1).bounds);

        Rectangle bounds = new Rectangle(old.x, old.y + old.height, textBounds.width, font.getLineHeight());
        items.add(new MenuItem(label, bounds));
    }

    public boolean handleKeyEvent(int keycode)
    {
        if (keycode == Keys.W)
        {
            index--;
            if (index < 0)
                index = items.size() - 1;
        }
        else if (keycode == Keys.S)
        {
            index++;
            if (index > items.size() - 1)
                index = 0;
        }
        return true;
    }

    public boolean handleTouchEvent(int x, int y, int pointer, int button)
    {
        for (int i = 0; i < items.size(); i++)
        {
            Vector3 point = new Vector3(x, y, 0.0f);
            camera.unproject(point);

            if (items.get(i).bounds.contains(point.x, point.y))
            {
                index = i;
                return true;
            }
        }

        return false;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public String getSelection()
    {
        if (selected)
        {
            selected = false;
            return items.get(index).label;
        }

        return "";
    }

    public void draw(float delta)
    {
        menuBatch.begin();
        for (int i = 0; i < items.size(); i++)
        {
            if (i == index)
                font.setColor(1.0f, 0.0f, 1.0f, 1.0f);
            else
                font.setColor(1.0f, 1.0f, 1.0f, 1.0f);

            font.draw(menuBatch, items.get(i).label, items.get(i).bounds.x, items.get(i).bounds.y);
        }
        menuBatch.end();
    }
}

