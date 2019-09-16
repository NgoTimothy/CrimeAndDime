package com.mygdx.cnd.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.cnd.CrimeandDime;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		new LwjglApplication(new CrimeandDime(), cfg);

		cfg.title = "Crime & Dime";
		cfg.useGL30 = true;
		cfg.width = 1280;
		cfg.height = 720;


	}


}
