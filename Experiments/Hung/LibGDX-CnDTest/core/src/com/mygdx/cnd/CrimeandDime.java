package com.mygdx.cnd;

import com.badlogic.gdx.Game;
import com.mygdx.Screen.Splash;
import com.mygdx.Screen.tileMapScreen;

public class CrimeandDime extends Game {

	@Override
	public void create () {
		setScreen(new Splash());
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
