package fp.infiniteset.Burst;

import fp.infiniteset.Burst.MainGame;

import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

public class SimpleGame extends GameController
{
    private static final int hitRadius  = 20;
    private static final int hitRadius2 = hitRadius * hitRadius;
    private static final int missPenalty = 1200;

    private LinkedList<Firework> comboList;

    public SimpleGame(FileHandle musicFile, FileHandle beatFile)
    {
        super(musicFile, beatFile);

        comboList = new LinkedList<Firework>();
        scoreDiff = 0;
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

                if (f.getPosition().y < f.getDestination().y &&
                    f.getPosition().dst2(f.getDestination()) > hitRadius2)
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
            Vector2 position = new Vector2(beatList.get(beatIndex).x + MathUtils.random(-10, 10),
                    MainGame.VIRTUAL_HEIGHT);
            for (int i = comboSize; i > 0; i--)
            {
                Vector2 destination = new Vector2(beatList.get(beatIndex + i - 1).x,
                        beatList.get(beatIndex + i - 1).y);

                Firework next = comboList.isEmpty() ? null : comboList.getFirst();
                Firework f = launcher.fire(position.cpy(), destination, next);
                comboList.addFirst(f);
            }
        }

        // Only handle one beat at a time (or else this would be a while loop)
        if (beatIndex < beatList.size() &&
               timer.getTime() > beatList.get(beatIndex).time - 1.0f)
        {
            Firework f = comboList.remove();
            f.setActive();
            f.launch();
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

    // This function no longer handles multiple hits per keypress
    public boolean handleInputEvent()
    {
        for (Firework f : launcher.getFireworks())
        {
            // 20 pixel radius to destination
            if (f.getDistance2() < hitRadius2)
            {
                combo++;
                scoreDiff = (int)((hitRadius2 - f.getDistance2()) * combo / 10.0f);
                score += scoreDiff;
                launcher.detonate(f);

                return true;
            }
        }

        inputFail();

        return true;
    }

    public void inputFail()
    {
        scoreDiff = -missPenalty;
        score = Math.max(0, score + scoreDiff);
        combo = 0;
    }
}
