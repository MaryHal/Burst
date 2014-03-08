package fp.infiniteset.Burst;

public class Stopwatch
{
    private long startTime;
    private long stopTime;
    private boolean running;

    public Stopwatch()
    {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    public void start()
    {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop()
    {
        stopTime = System.currentTimeMillis();
        running = false;
    }

    public long getTime()
    {
        long elapsed;
        if (running)
        {
            elapsed = (System.currentTimeMillis() - startTime);
        }
        else
        {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public float getFloat()
    {
        return (float)getTime() / 1000.0f;
    }
}

