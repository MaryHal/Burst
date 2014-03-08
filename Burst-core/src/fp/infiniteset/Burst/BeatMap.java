package fp.infiniteset.Burst;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonReader;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

public class BeatMap implements Json.Serializable
{
    public class Beat
    {
        public float time;
        public int keys;

        public Beat(float time, int keys)
        {
            this.time = time;
            this.keys = keys;
        }
    }

    public class BeatComparator implements Comparator<Beat>
    {
        public int compare(Beat x, Beat y)
        {
            return Float.compare(x.time, y.time);
        }
    }

    protected PriorityQueue<Beat> beatQueue;

    protected BeatMap()
    {
        beatQueue = new PriorityQueue<Beat>(10, new BeatComparator());
    }

    public BeatMap(FileHandle file)
    {
        this();
        readFile(file);
    }

    public Beat getNextBeat()
    {
        return beatQueue.peek();
    }

    public void readFile(FileHandle file)
    {
        JsonReader jsonReader = new JsonReader();
        JsonValue map = jsonReader.parse(file);

        for (JsonValue value : map)
        {
            Beat b = new Beat(Float.parseFloat(value.name),
                              value.asInt());
            beatQueue.add(b);
        }
    }

    public void write(Json json)
    {
        Beat[] beatArray = new Beat[beatQueue.size()];
        beatQueue.toArray(beatArray);
        Arrays.sort(beatArray, beatQueue.comparator());

        for (Beat b : beatArray)
        {
            json.writeValue(Float.toString(b.time), b.keys);
        }
    }

    public void read(Json json, JsonValue jsonMap)
    {
        Beat b = new Beat(Float.parseFloat(jsonMap.child().name()),
                          jsonMap.child().asInt());
        beatQueue.add(b);
    }
}
