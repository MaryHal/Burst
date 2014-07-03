package fp.infiniteset.Burst.Beats;

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
        public int type;

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
    }

    public Beat[] getBeatArray()
    {
        Beat[] beatArray = new Beat[beatQueue.size()];
        beatQueue.toArray(beatArray);
        Arrays.sort(beatArray, beatQueue.comparator());
        return beatArray;
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
}

    // private void placeBeats()
    // {
    //     // Get viewport rectangle and calculate an area for possible burst points.
    //     Rectangle viewport = MainGame.viewport;
    //     Rectangle area = new Rectangle(100.0f, 120.0f, viewport.width - 200.0f, 140.0f);

    //     /* Description of "type" from Osu-sdk
    //      *
    //      * [Flags]
    //      * public enum HitObjectType
    //      * {
    //      *     Normal = 1,
    //      *     Slider = 2,
    //      *     NewCombo = 4,
    //      *     NormalNewCombo = 5,
    //      *     SliderNewCombo = 6,
    //      *     Spinner = 8,
    //      *     ColourHax = 112,
    //      *     Hold = 128,
    //      *     ManiaLong = 128
    //      * } ;
    //      */

    //     // Throw away the first 20 numbers from the HaltonSequence
    //     // and also introduce some randomness./
    //     HaltonSequence dist = new HaltonSequence(2);

    //     // Converting to an array is recommended for ordered traveral of a
    //     // PriorityQueue.
    //     // http://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html
    //     Beat[] beatArray = new Beat[beatQueue.size()];
    //     beatQueue.toArray(beatArray);
    //     Arrays.sort(beatArray, beatQueue.comparator());

    //     ArrayList<Beat> comboList = new ArrayList<Beat>();
    //     Rectangle prevBox = new Rectangle();
    //     for (Beat beat : beatArray)
    //     {
    //         // 5 and 6 finalize a string of beats, so place them
    //         if (beat.type == 5 || beat.type == 6)
    //         {
    //             Vector2 v = null;
    //             Rectangle thisBox = null;
    //             int radius = 0;

    //             do
    //             {
    //                 v = dist.nextFloat2d();
    //                 v.x = v.x * area.width + area.x;
    //                 v.y = v.y * area.height + area.y;
    //                 radius = MathUtils.random(40 + comboList.size() * 2, 60 + comboList.size() * 2);

    //                 thisBox = new Rectangle(v.x - radius, v.y - radius, 2 * radius, 2 * radius);
    //             } while (thisBox.overlaps(prevBox));

    //             prevBox = thisBox;

    //             placeCircle(comboList,
    //                     v.x,
    //                     v.y,
    //                     radius,
    //                     MathUtils.random(MathUtils.PI2),
    //                     MathUtils.randomBoolean() == true ? 1 : -1);

    //             comboList.get(0).comboSize = comboList.size();

    //             // Start our combo over again
    //             comboList.clear();
    //         }

    //         comboList.add(beat);
    //     }

    //     // If the last beat isn't a finalizer, we gotta finish it up.
    //     Vector2 v = dist.nextFloat2d();
    //     placeCircle(comboList,
    //             v.x * area.width + area.x,
    //             v.y * area.height + area.y,
    //             MathUtils.random(40 + comboList.size() * 2, 60 + comboList.size() * 2),
    //             MathUtils.random(MathUtils.PI2),
    //             MathUtils.randomBoolean() == true ? 1 : -1);

    //     comboList.get(0).comboSize = comboList.size();
    // }

    // private void placeCircle(ArrayList<Beat> comboList, float x, float y, int radius, float offset, int dir)
    // {
    //     for (int i = 0; i < comboList.size(); i++)
    //     {
    //         float radians = offset + dir * i * 3.14159f * 2 / comboList.size();
    //         comboList.get(i).x = (float)(x + Math.cos(radians) * radius);
    //         comboList.get(i).y = (float)(y + Math.sin(radians) * radius);
    //     }
    // }

    // private void placeLine(ArrayList<Beat> comboList, float x1, float y1, float x2, float y2)
    // {
    //     float slopex = (x2 - x1) / comboList.size();
    //     float slopey = (y2 - y1) / comboList.size();
    //     for (int i = 0; i < comboList.size(); i++)
    //     {
    //         comboList.get(i).x = x1 + slopex * i;
    //         comboList.get(i).y = y1 + slopey * i;
    //     }
    // }

    // private void placeRandom(ArrayList<Beat> comboList, float x1, float y1)
    // {
    //     comboList.get(0).x = x1;
    //     comboList.get(0).y = y1;
    //     for (int i = 1; i < comboList.size(); i++)
    //     {
    //         Vector2 v1;
    //         do
    //         {
    //             v1 = new Vector2(MathUtils.random(80, 400), MathUtils.random(80, 180));
    //             comboList.get(i).x = v1.x;
    //             comboList.get(i).y = v1.y;
    //         } while (v1.dst(new Vector2(comboList.get(i-1).x, comboList.get(i-1).y)) < 40.0f);
    //     }
    // }
