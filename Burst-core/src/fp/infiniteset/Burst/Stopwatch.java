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

    public float getTime()
    {
        if (running)
        {
            return (float)(System.currentTimeMillis() - startTime) / 1000.0f;
        }
        return (float)(stopTime - startTime) / 1000.0f;
    }
}

