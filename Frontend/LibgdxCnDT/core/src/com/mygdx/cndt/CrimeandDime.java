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
import GameExceptions.PlacingItemWithNoShelfException;
import GameExceptions.ShelfWithNoDirectionException;

public class CrimeandDime extends Game {

	public Store gameStore;
	public ArrayList<Item> items;
	public tileMapScreen tileMap;
	public ArrayList<Tile> shelves;
	
	@Override
	public void create () {
		gameStore = new Store("My Store");
		tileMap = new tileMapScreen(this);
		try {
			loadItems();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Tile tile = new Tile();
		try {
			tile.setShelfTile(Tile.shelfDirection.NORTH);
		} catch (ShelfWithNoDirectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Item item = new Item(items.get(0).copy());
		try {
			tile.setItemOnShelf(item, 10);
		} catch (PlacingItemWithNoShelfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setScreen(tileMap);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width,height);
	}
	
	private ArrayList<Item> loadItems() throws JsonParseException, JsonMappingException, IOException
	{
		String result = "";
    	try {
        	String url = "http://coms-309-tc-3.misc.iastate.edu:8080/inventory";
    		
    		URL obj = new URL(url);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    		// optional default is GET
    		con.setRequestMethod("GET");

    		String USER_AGENT = "Mozilla/5.0";
    		
    		//add request header
    		con.setRequestProperty("User-Agent", USER_AGENT);

    		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'GET' request to URL : " + url);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();

    		result = response.toString();
    		
        	}
        	catch(Exception e)	{
        		System.out.print(e);
        	}    	    	
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		items = objectMapper.readValue(result, new TypeReference<ArrayList<Item>>(){});
		for (Item i : items)
		{
			System.out.println(i.getName());
		}
		System.out.println("***");
		
		for (Item item : items)
			item.setRetailCost(item.getWholesaleCost() + 0.1 * item.getWholesaleCost());
		
		return items;
	}
}
