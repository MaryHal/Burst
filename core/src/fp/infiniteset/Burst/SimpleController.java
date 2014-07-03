package fp.infiniteset.Burst;

import fp.infiniteset.Burst.Fireworks.FireworkGroup;
import fp.infiniteset.Burst.Fireworks.FireworkLauncher;
import fp.infiniteset.Burst.Fireworks.Firework;

import com.badlogic.gdx.files.FileHandle;

public class SimpleController extends GameController
{
    private static final int hitRadius  = 20;
    private static final int hitRadius2 = hitRadius * hitRadius;
    private static final int missPenalty = 1200;

    public SimpleController(FileHandle musicFile, FileHandle beatFile)
    {
        super(musicFile, beatFile);

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
        while (queuedIndex < beatList.length &&
                timer.getTime() + 4.0f > beatList[queuedIndex].time)
        {
            // System.out.println(queuedIndex + " " + beatList.length + " " + beatList[queuedIndex].time);
            queuedIndex = launcher.enqueueCombo(queuedIndex, beatList);
        }

        // Launch next beat
        // Only handle one beat at a time (or else this would be a while loop)
        if (runningIndex < beatList.length &&
               timer.getTime() + 1.0f > beatList[runningIndex].time)
        {
            Firework f = launcher.yieldNextFirework();
            if (f != null)
                f.launch();

            runningIndex++;
        }

        launcher.draw(delta);
        launcher.removeFinishedGroups(timer.getTime());
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
        for (FireworkGroup g : launcher.getFireworkGroups())
        {
            for (Firework f : g.getComboList())
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
