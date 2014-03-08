package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

public class MusicController
{
    private Music music;

    public MusicController()
    {
        music = Gdx.audio.newMusic(Gdx.files.external(".config/Burst/music/Melodica.mp3"));
        music.setLooping(false);
    }

    public void dispose()
    {
        music.dispose();
    }

    public void play()
    {
        music.play();
    }

    public Music getMusic()
    {
        return music;
    }
}
