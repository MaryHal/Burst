package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.files.FileHandle;

public class MusicController implements Disposable
{
    private Music music;

    public MusicController()
    {
        /* music = Gdx.audio.newMusic(Gdx.files.external(".config/Burst/music/Melodica.mp3")); */
        /* music.setLooping(false); */
    }

    public void loadSong(FileHandle file)
    {
        music = Gdx.audio.newMusic(file);
        music.setLooping(false);
    }

    @Override
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
