package fp.infiniteset.Burst.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fp.infiniteset.Burst.MainGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Burst";
        config.width = 480;
        config.height = 320;
        new LwjglApplication(new MainGame(), config);
    }
}
