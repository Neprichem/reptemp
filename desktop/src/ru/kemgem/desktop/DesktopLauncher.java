package ru.kemgem.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.kemgem.mainClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = mainClass.WIDTH;
		config.height = mainClass.HEIGHT;
		config.title = mainClass.TITLE;
		new LwjglApplication(new mainClass(), config);
	}
}
