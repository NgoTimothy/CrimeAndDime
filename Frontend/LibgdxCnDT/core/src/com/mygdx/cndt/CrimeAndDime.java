package com.mygdx.cndt;

import GameClasses.Item;
import GameClasses.Store;
import com.badlogic.gdx.Game;
import com.mygdx.Screen.*;

import java.util.ArrayList;

public class CrimeAndDime extends Game {
	public Store gameStore;
	public ArrayList<Item> items;

	@Override
	public void create () {
		setScreen(new Splash(this));
		gameStore = new Store("My Store");
		Item newItem = new Item();
		items.add(newItem);
		gameStore.getInventory().add(items.get(0));

	}//new MainMenuCnD(this)

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
}
