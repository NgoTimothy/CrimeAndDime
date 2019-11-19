package com.mygdx.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;

enum nextScreen {
    EXIT, LOBBY;
}

public class Splash implements Screen {

    private SpriteBatch batch;
    private Sprite splashImg;
    private Image playImg;
    private Image exitImg;
    private Stage stage;
    private CrimeAndDime curGame;

    public Splash(CrimeAndDime crimeAndDime) {
        curGame = crimeAndDime;
    }

    public Splash() {

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
        Texture splashTexture = new Texture(Gdx.files.internal("img/CnD-MainMenu.png"));
        splashImg = new Sprite(splashTexture);
        splashImg.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

<<<<<<< HEAD
        playImg = setImage(texture, 290, 314, 700, 75, nextScreen.LOBBY);
        stage.addActor(playImg);
        exitImg = setImage(texture, 290, 50, 700, 75, nextScreen.EXIT);
        stage.addActor(exitImg);
=======
        Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
        shelfImage = new Image(texture);
        shelfImage.setPosition(290,314);
        shelfImage.setSize(700,75);
        shelfImage.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new tileMapScreen());
            }
        });

        stage.addActor(shelfImage);
>>>>>>> SpriteMovement-Hung
        batch.begin();
        batch.end();
        System.out.println(playImg.getX());
    }

    private Image setImage(Texture newTexture, int positionX, int positionY, int width, int height, nextScreen newScreen) {
        Image returnImg = new Image(newTexture);
        returnImg.setPosition(positionX, positionY);
        returnImg.setSize(width, height);
        if(newScreen == nextScreen.EXIT) {
            returnImg.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });
        }
        else if(newScreen == nextScreen.LOBBY) {
            returnImg.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Lobbies(curGame));
                }
            });
        }
        return returnImg;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splashImg.draw(batch);
        playImg.setColor(0,0,0,0);
        exitImg.setColor(0, 0, 0, 0);
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
