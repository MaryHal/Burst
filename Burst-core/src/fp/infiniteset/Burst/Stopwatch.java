package fp.infiniteset.Burst;

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
        startTime = System.currentTimeMillis();
    }

    public void stop()
    {
        started = false;
        paused = false;
    }

    // Clock actions
    public void pause()
    {
        if ((started == true) && (paused == false))
        {
            paused = true;
            pauseTime = System.currentTimeMillis() - startTime;
        }
    }

    public void unpause()
    {
        if (paused == true)
        {
            // Set status
            paused = false;

            // Reset times
            startTime = System.currentTimeMillis() - pauseTime;
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
                return (float)(System.currentTimeMillis() - startTime) / 1000.0f;
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

