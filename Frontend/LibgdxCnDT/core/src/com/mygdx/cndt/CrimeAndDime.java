package com.mygdx.cndt;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygdx.Screen.*;

import GameClasses.Item;
import GameClasses.Store;
import GameClasses.Tile;

import Services.CrimeAndDimeService;

public class CrimeAndDime extends Game {

	public Store gameStore;
	public ArrayList<Item> items;
	public tileMapScreen tileMap;
	private Boolean shelfChanged;
	private long accumulator = 0;
	private final float TIME_STEP      = 1 / 30f; // 30 times a second
	private final int   MAX_FRAMESKIPS = 5;       // Make 5 updates mostly without a render
	private int skippedFrames;

	@Override
	public void create () {
		gameStore = new Store("My Store");
		tileMap = new tileMapScreen(this);
		CrimeAndDimeService newService = new CrimeAndDimeService();
		createItems(newService);
		setScreen(new Splash(this));
		shelfChanged = false;
		//setScreen(new ShelfScreen(this, new Tile()));
	}

	@Override
	public void render () {
		super.render();
		/*accumulator += Gdx.graphics.getDeltaTime();
		skippedFrames = 0;

		while (accumulator >= TIME_STEP && skippedFrames <= MAX_FRAMESKIPS)
		{
			accumulator -= TIME_STEP;
			//screen.update(TIME_STEP);
			System.out.println(accumulator);
			skippedFrames++;
		}

		screen.render(TIME_STEP);*/
	}

	/*final float TIME_STEP      = 1 / 30f; // 30 times a second
	final int   MAX_FRAMESKIPS = 5;       // Make 5 updates mostly without a render

	float accumulator;
	int   skippedFrames;

	void render()
	{
		accumulator += Gdx.graphics.getDeltaTime();

		skippedFrames = 0;

		while (accumulator >= TIME_STEP && skippedFrames <= MAX_FRAMESKIPS)
		{
			accumulator -= TIME_STEP;
			screen.update(TIME_STEP);

			skippedFrames++;
		}

		screen.render();
	}*/

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width,height);
	}
	
	public void createItems(CrimeAndDimeService service) {
		try {
			items = service.loadItems();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Item> getItems()
	{
		return items;
	}

	public boolean isShelfChanged() { return shelfChanged; }

	public void setShelfChanged(boolean shelfChanged) { this.shelfChanged = shelfChanged; }
}

