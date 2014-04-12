package fp.infiniteset.Burst;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

public class SimpleGame extends GameController
{
    private LinkedList<Firework> comboList;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super(musicFile, beatFile);

        comboList = new LinkedList<Firework>();
    }

    @Override
    public void initializeLauncher()
    {
        launcher = new FireworkLauncher(camera)
        {
            // Since Fireworks are not guaranteed to "hit" their targets,
            // we gotta remove undetonated ones.
            @Override
            public void updateFirework(Firework f, float delta)
            {
                f.update(delta);

                if (f.getPosition().y < 0.0f)
                {
                    f.setAlive(false);
                    remove(f);

                    inputFail();
                }
            }
        };
    }

    @Override
    public boolean handleKeyDown(int keycode)
    {
        return handleInputEvent();
    }

    @Override
    public boolean handleTouchDown(int x, int y, int pointer, int button)
    {
        return handleInputEvent();
    }

    @Override
    public void render(float delta)
    {
        if (beatIndex < beatList.size() && comboList.size() == 0)
        {
            int comboSize = beatList.get(beatIndex).comboSize;

            // Fixed starting position for a set of beats
            Vector2 position = new Vector2(rng.nextFloat() * 200 + 140, 320.0f);
            for (int i = comboSize; i > 0; i--)
            {
                Vector2 destination = new Vector2(beatList.get(beatIndex + i - 1).x,
                        beatList.get(beatIndex + i - 1).y);

                Firework next = comboList.isEmpty() ? null : comboList.getFirst();
                Firework f = launcher.fire(position.cpy(), destination, next);
                comboList.addFirst(f);
            }
        }

        while (beatIndex < beatList.size() &&
               timer.getTime() > beatList.get(beatIndex).time - 1.0f)
        {
            comboList.remove().launch();
            beatIndex++;
        }

        launcher.draw(delta);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        launcher.dispose();
    }

    public boolean handleInputEvent()
    {
        boolean success = false;
        for (Firework f : launcher.getFireworks())
        {
            // 20 pixel radius to destination
            if (f.getDistance2() < 400.0f)
            {
                combo++;
                score += (400.0f - f.getDistance2()) * combo / 10.0f;
                launcher.detonate(f);

                success = true;
            }
        }

        if (!success)
        {
            inputFail();
        }

        return true;
    }

    public void inputFail()
    {
        score = (score - 1200 < 0) ? 0 : score - 1200;
        combo = 0;
    }
}
