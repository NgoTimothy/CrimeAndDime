package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ShelfScreen implements Screen {

	private Stage stage;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		stage = new Stage();
		Texture texture = new Texture(Gdx.files.internal("img/shelf.jpeg"));
        Image shelfImage = new Image(texture);
        shelfImage.setPosition(100, 100);
        stage.addActor(shelfImage);
        
        
        
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.setDebugAll(true);
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
