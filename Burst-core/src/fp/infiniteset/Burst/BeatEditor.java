package fp.infiniteset.Burst;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class BeatEditor extends BeatMap
{
    public BeatEditor()
    {
        super();
    }

    public BeatEditor(FileHandle file)
    {
        super(file);
    }

    public void addBeat(float time, int keys)
    {
        beatQueue.add(new Beat(time, keys));
    }

    public void writeFile(FileHandle file, boolean pretty)
    {
        Json json = new Json();
        if (pretty)
            file.writeString(json.prettyPrint(this), false);
        else
            file.writeString(json.toJson(this), false);
    }

    public String toString()
    {
        return new Json().prettyPrint(this);
    }
}
