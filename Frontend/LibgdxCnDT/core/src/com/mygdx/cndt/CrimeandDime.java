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

public class CrimeandDime extends Game {

	public Store gameStore;
	public ArrayList<Item> items;
	
	@Override
	public void create () {
		gameStore = new Store("My Store");
		try {
			loadItems();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(0));
//        gameStore.purchaseItemToInventory(items.get(6));
//        gameStore.purchaseItemToInventory(items.get(6));
//        gameStore.purchaseItemToInventory(items.get(7));
        gameStore.getInventory().add(items.get(0));
        gameStore.getInventory().get(0).addQuantity(10);
        gameStore.getInventory().add(items.get(6));
        gameStore.getInventory().get(1).addQuantity(5);
        gameStore.getInventory().add(items.get(7));
        gameStore.getInventory().get(2).addQuantity(3);
		setScreen(new ShelfScreen(this, new Tile()));
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
		
		return items;
	}
}
