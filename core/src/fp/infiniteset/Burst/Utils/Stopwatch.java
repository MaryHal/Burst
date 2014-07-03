package fp.infiniteset.Burst.Utils;

import com.badlogic.gdx.utils.TimeUtils;

public class Stopwatch
{
    private long startTime;
    private long pauseTime;

    private boolean started;
    private boolean paused;

    public Stopwatch()
    {
        startTime = 0;
        pauseTime = 0;

        started = false;
        paused = false;
    }

    public void start()
    {
        started = true;
        paused = false;
        startTime = TimeUtils.millis();
    }

    public void stop()
    {
        started = false;
        paused = false;
    }

    public void reset()
    {
        stop();
        start();
    }

    // Clock actions
    public void pause()
    {
        if ((started == true) && (paused == false))
        {
            paused = true;
            pauseTime = TimeUtils.millis() - startTime;
        }
    }

    public void unpause()
    {
        if (paused == true)
        {
            // Set status
            paused = false;

            // Reset times
            startTime = TimeUtils.millis() - pauseTime;
            pauseTime = 0;
        }
    }

    // Set Time
    public void setOffset(float time)
    {
        startTime += time;
    }

    public float getTime()
    {
        if (started)
        {
            if (paused)
            {
                return (float)pauseTime / 1000.0f;
            }
            else
            {
                return (float)(TimeUtils.millis() - startTime) / 1000.0f;
            }
        }

        return 0;
    }

        // Check status
    public boolean isStarted()
    {
        return started;
    }

    public boolean isPaused()
    {
        return paused;
    }
}

