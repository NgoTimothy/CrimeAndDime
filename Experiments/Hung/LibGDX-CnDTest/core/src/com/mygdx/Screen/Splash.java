package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class Splash implements Screen {

    private SpriteBatch batch;
    private Sprite splashImg;


    @Override
    public void show ()
    {
        batch = new SpriteBatch();

        Texture splashTexture = new Texture(Gdx.files.internal("assets/cndSplash.jpg"));
        splashImg = new Sprite(splashTexture);
        splashImg.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splashImg.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int y, int x){

    }

    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){

    }

    @Override
    public void dispose () {

    }
}
