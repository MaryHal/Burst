package fp.infiniteset.Burst.Utils;

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
        public boolean selectable;

        public MenuItem(String label, Rectangle bounds, boolean selectable)
        {
            this.label = label;
            this.bounds = bounds;
            this.selectable = selectable;
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

        index = -1;
        selected = false;
    }

    public void dispose()
    {
        menuBatch.dispose();
    }

    public void addItem(String label)
    {
        appendToList(label, true);
    }

    public void addItem(String label, boolean selectable)
    {
        appendToList(label, selectable);
    }

    private void appendToList(String label, boolean selectable)
    {
        BitmapFont.TextBounds textBounds = font.getBounds(label);
        Rectangle old = (items.isEmpty() ?
                         new Rectangle(offset.x, offset.y, 0.0f, 0.0f) :
                         items.get(items.size() - 1).bounds);

        Rectangle bounds = new Rectangle(old.x,
                                         old.y + old.height,
                                         textBounds.width,
                                         font.getLineHeight());
        items.add(new MenuItem(label, bounds, selectable));

        // Set initial index
        if (index < 0 && selectable)
            index = items.size() - 1;
    }

    public void addMargin()
    {
        if (!items.isEmpty())
            items.get(items.size() - 1).bounds.x += 8.0f;
    }

    public void removeMargin()
    {
        if (!items.isEmpty())
            items.get(items.size() - 1).bounds.x -= 8.0f;
    }

    public boolean handleKeyDown(int keycode)
    {
        int accumulator = 0;
        if (keycode == Keys.W)
        {
            do
            {
                index--;
                if (index < 0)
                    index = items.size() - 1;
            } while (++accumulator < items.size() && !items.get(index).selectable);
        }
        else if (keycode == Keys.S)
        {
            do
            {
                index++;
                if (index > items.size() - 1)
                    index = 0;
            } while (++accumulator < items.size() && !items.get(index).selectable);
        }

        if (keycode == Keys.J || keycode == Keys.ENTER)
        {
            selected = true;
        }

        return true;
    }

    public boolean handleTouchDown(int x, int y, int pointer, int button)
    {
        for (int i = 0; i < items.size(); i++)
        {
            Vector3 point = new Vector3(x, y, 0.0f);
            camera.unproject(point);

            MenuItem currentItem = items.get(i);
            if (currentItem.bounds.contains(point.x, point.y) && currentItem.selectable)
            {
                // If the clicked menu item is already selected, hard select it.
                if (index == i)
                {
                    selected = true;
                }
                else
                {
                    index = i;
                }
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

