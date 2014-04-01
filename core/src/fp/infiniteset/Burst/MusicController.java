package fp.infiniteset.Burst;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.files.FileHandle;

public class MusicController implements Disposable
{
    private Music music;
    private Music music2;

    public MusicController()
    {
        /* music = Gdx.audio.newMusic(Gdx.files.external(".config/Burst/music/Melodica.mp3")); */
        /* music.setLooping(false); */
    }

    public void loadSong(FileHandle file)
    {
        music = Gdx.audio.newMusic(file);
        music.setLooping(false);

        music2 = Gdx.audio.newMusic(file);
        music2.setLooping(false);
        music2.setVolume(0.0f);
    }

    @Override
    public void dispose()
    {
        music.dispose();
    }

    public void play()
    {
        music.play();
        music2.play();
    }

    public Music getMusic()
    {
        return music;
    }

    public float timeDiff()
    {
        System.out.println(music2.getPosition() + " " + music.getPosition());
        return music2.getPosition() - music.getPosition();
    }
}
