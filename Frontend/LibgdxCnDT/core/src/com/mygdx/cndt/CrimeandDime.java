package com.mygdx.cndt;

import com.badlogic.gdx.Game;
import com.mygdx.Screen.*;

public class CrimeandDime extends Game {

	@Override
	public void create () {
		setScreen(new ShelfScreen());
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
}
