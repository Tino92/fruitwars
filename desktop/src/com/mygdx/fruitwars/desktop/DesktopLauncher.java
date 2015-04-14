package com.mygdx.fruitwars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.fruitwars.FruitWarsMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Fruit Wars";
	    config.width = 800;
	    config.height = 480;
		new LwjglApplication(new FruitWarsMain(), config);
	}
}
