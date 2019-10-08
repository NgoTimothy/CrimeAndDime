package com.mygdx.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import sun.applet.Main;

//import javax.xml.soap.Text;

public class Splash implements Screen {

    private SpriteBatch batch;
    private Sprite splashImg;
    private Image shelfImage;
    private Stage stage;

    @Override
    public void show ()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        Texture splashTexture = new Texture(Gdx.files.internal("img/CnD-MainMenu.png"));
        splashImg = new Sprite(splashTexture);
        splashImg.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
        shelfImage = new Image(texture);
        shelfImage.setPosition(290,314);
        shelfImage.setSize(700,75);
        shelfImage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(shelfImage);
        batch.begin();

        batch.end();

        System.out.println(shelfImage.getX());
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splashImg.draw(batch);
        shelfImage.setColor(0,0,0,0);
        batch.end();

        stage.draw();
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
        batch.dispose();
        splashImg.getTexture().dispose();
    }
}
