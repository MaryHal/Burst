package fp.infiniteset.Burst.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import fp.infiniteset.Burst.MainGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Burst";
        config.width = 640;
        config.height = 480;
        config.resizable = false;
        new LwjglApplication(new MainGame(), config);
    }
}
