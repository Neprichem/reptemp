package ru.kemgem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.kemgem.MainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MainClass.WIDTH;
		config.height = MainClass.HEIGHT;
		config.title = MainClass.TITLE;
		new LwjglApplication(new MainClass(), config);
	}
}
