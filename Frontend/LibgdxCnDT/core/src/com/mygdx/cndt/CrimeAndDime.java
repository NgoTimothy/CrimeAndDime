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
import com.badlogic.gdx.utils.TimeUtils;
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
import com.sun.org.apache.xpath.internal.operations.Bool;

public class CrimeAndDime extends Game {

	public Store gameStore;
	public ArrayList<Item> items;
	public tileMapScreen tileMap;
	private Boolean shelfChanged;
	private float accumulator;
	private final float TIME_STEP = 1 / 30f; // 30 times a second
	private int hour;
	private boolean startTimer;
	private boolean nextDay;
	private static final int closingTime = 20;
	private int day;

	@Override
	public void create() {
		gameStore = new Store("My Store");
		tileMap = new tileMapScreen(this);
		CrimeAndDimeService newService = new CrimeAndDimeService();
		createItems(newService);
		setScreen(new Splash(this));
		shelfChanged = false;
		startTimer = false;
		nextDay = false;
		hour = 8;
		accumulator = 0;
		printTime(hour);
		day = 1;
	}

	@Override
	public void render() {
		super.render();
		if(startTimer) {
			accumulator += Gdx.graphics.getDeltaTime();
			if (accumulator >= 1f) {//1f is 1 second, 2f is 2 seconds and so forth
				hour++;
				accumulator = 0;
				//System.out.println(hour);
			}
		}

		screen.render(TIME_STEP);
	}

	@Override
	public void dispose() {
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

	private void printTime(int seconds) {
		String AMOrPM = "AM";
		int timeOfDay = seconds;
		if(timeOfDay > 12) {
			timeOfDay -= 12;
			AMOrPM = "PM";
		}
		System.out.println(timeOfDay + ":00 " + AMOrPM);
		if(seconds == closingTime)
			startTimer = false;
	}
	
	public ArrayList<Item> getItems() { return items; }

	public boolean isShelfChanged() { return shelfChanged; }

	public void setShelfChanged(boolean shelfChanged) { this.shelfChanged = shelfChanged; }

	public void setStartTimer(boolean startOrStop) { startTimer = startOrStop; }

	public boolean getStartTimer() { return startTimer; }

	public void setHour(int newHour) {
		hour = newHour;
		accumulator = 0;
	}

	public int getHour() { return hour; }

	public void increaseDay() { day++; }

	public int getDay() { return  day; }

	public void setNextDay(boolean readyForNewDay) { nextDay = readyForNewDay; }

	public boolean getNextDay() { return nextDay; }
}

