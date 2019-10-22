package com.mygdx.cndt.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.TileMapGame.TileMapGame;
import com.mygdx.cndt.CrimeAndDime;


public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		new LwjglApplication(new CrimeAndDime(), cfg);
		cfg.vSyncEnabled = true;
		cfg.title = "Crime & Dime";
		cfg.useGL30 = true;
		cfg.width = 1280;
		cfg.height = 720;
		
		/*
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		new LwjglApplication(new TileMapGame(), cfg);

		cfg.title = "Crime & Dime";
		cfg.useGL30 = true;
		cfg.width = 1280;
		cfg.height = 720;
		*/
	}
}
