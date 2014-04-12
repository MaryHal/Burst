package fp.infiniteset.Burst.Beats;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;

import java.util.Random;
import fp.infiniteset.Burst.Utils.HaltonSequence;

public class BeatMap implements Json.Serializable
{
    public class Beat
    {
        public float time;
        public int type;
        public float x;
        public float y;
        public int comboSize;

        public Beat(float time, int type)
        {
            this.time = time;
            this.type = type;
        }
    }

    public class BeatComparator implements Comparator<Beat>
    {
        @Override
        public int compare(Beat x, Beat y)
        {
            return Float.compare(x.time, y.time);
        }
    }

    // We use two copies of our Beat Queue because playing the game is destructive.
    // We must keep an "original" copy in case we ever want to replay a song without
    // reloading a beat file.
    protected PriorityQueue<Beat> beatQueue;
    // protected PriorityQueue<Beat> runningQueue;

    protected BeatMap()
    {
        beatQueue = new PriorityQueue<Beat>(512, new BeatComparator());
    }

    public BeatMap(FileHandle file)
    {
        this();

        readFile(file);
        placeBeats();
    }

    public void reset()
    {
        placeBeats();
    }

    public ArrayList<Beat> getNewBeatList()
    {
        placeBeats();
        Beat[] beatArray = new Beat[beatQueue.size()];
        beatQueue.toArray(beatArray);
        return new ArrayList<Beat>(Arrays.asList(beatArray));
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

    @Override
    public void write(Json json)
    {
        Beat[] beatArray = new Beat[beatQueue.size()];
        beatQueue.toArray(beatArray);
        Arrays.sort(beatArray, beatQueue.comparator());

        for (Beat b : beatArray)
        {
            json.writeValue(Float.toString(b.time), b.type);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonMap)
    {
        Beat b = new Beat(Float.parseFloat(jsonMap.child().name()),
                          jsonMap.child().asInt());
        beatQueue.add(b);
    }

    public void placeBeats()
    {
        /* Description of "type" from Osu-sdk
         *
         * [Flags]
         * public enum HitObjectType
         * {
         *     Normal = 1,
         *     Slider = 2,
         *     NewCombo = 4,
         *     NormalNewCombo = 5,
         *     SliderNewCombo = 6,
         *     Spinner = 8,
         *     ColourHax = 112,
         *     Hold = 128,
         *     ManiaLong = 128
         * } ;
         */

        // Throw away the first 20 numbers from the HaltonSequence
        // and also introduce some randomness./
        Random rng = new Random();
        int index = rng.nextInt(10) + 20;
        HaltonSequence dist = new HaltonSequence(new int[] {2, 3});

        // Converting to an array is recommended for ordered traveral of a
        // PriorityQueue.
        // http://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
        Beat[] beatArray = new Beat[beatQueue.size()];
        beatQueue.toArray(beatArray);
        Arrays.sort(beatArray, beatQueue.comparator());

        ArrayList<Beat> comboList = new ArrayList<Beat>();
        for (Beat beat : beatArray)
        {
            comboList.add(beat);

            // 5 and 6 finalize a string of beats, so place them
            if (beat.type == 5 || beat.type == 6)
            {
                double[] v = dist.getHaltonNumber(index);
                index++;

                placeCircle(comboList, (float)v[0] * 200 + 140, (float)v[1] * 80 + 80, rng.nextInt(20) + 20);
                comboList.get(0).comboSize = comboList.size();

                // Start our combo over again
                comboList.clear();
            }
        }

        // If the last beat isn't a finalizer, we gotta finish it up.
        double[] v = dist.getHaltonNumber(index);
        index++;

        placeCircle(comboList, (float)v[0] * 200 + 140, (float)v[1] * 80 + 80, rng.nextInt(20) + 20);
        comboList.get(0).comboSize = comboList.size();
    }

    public void placeCombo()
    {

    }

    public void placeLine(ArrayList<Beat> comboList)
    {

    }

    public void placeCircle(ArrayList<Beat> comboList, float x, float y, int radius)
    {
        for (int i = 0; i < comboList.size(); i++)
        {
            float radians = i * 3.14159f * 2 / comboList.size();
            comboList.get(i).x = (float)(x + Math.cos(radians) * radius);
            comboList.get(i).y = (float)(y + Math.sin(radians) * radius);
        }
    }
}
