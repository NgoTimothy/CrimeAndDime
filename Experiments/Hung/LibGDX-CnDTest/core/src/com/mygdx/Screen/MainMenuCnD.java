package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import javafx.stage.Stage;

public class MainMenuCnD implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private Table table;
    private TextButton buttonPlay,  buttonExit;
    private Label heading;


    @Override
    public void show ()
    {
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);


        white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    }

    @Override
    public void render(float delta){

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
