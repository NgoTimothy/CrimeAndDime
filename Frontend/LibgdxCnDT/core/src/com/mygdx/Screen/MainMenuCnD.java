package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeandDime;

import java.awt.*;

public class MainMenuCnD implements Screen {

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private Table table;
    private TextButton buttonPlay,  buttonExit;
    private Label heading;
    private CrimeandDime game;

    public MainMenuCnD(CrimeandDime game)
    {
    	this.game = game;
    	
    }

    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);

        stage.setDebugAll(true);

        stage.draw();
    }


    @Override
    public void show ()
    {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);

        table.setBounds(0,0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth());

        white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up.9");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;


        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.pad(200);
        buttonExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.pad(200);
        buttonPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new Lobbies(game));
            }
        });
        table.add(buttonExit);
        table.row();
        buttonExit.moveBy(540,0);
        table.add(buttonPlay);
        buttonPlay.moveBy(540,300);
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label("Crime & Dime", headingStyle);
        heading.moveBy(540,680);

        table.add(heading);
        table.row();
        table.padBottom(50);
        stage.addActor(table);
        stage.addActor(buttonPlay);
        stage.addActor(buttonExit);
        stage.addActor(heading);
        table.debugAll();


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
