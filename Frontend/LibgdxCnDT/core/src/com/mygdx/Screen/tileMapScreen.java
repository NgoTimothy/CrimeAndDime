package com.mygdx.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Objects.tempTile;

import java.util.ArrayList;
import java.util.Random;

public class tileMapScreen implements Screen {

    private TiledMap maps;
    private TiledMapTileLayer layer;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;
    private tempTile shelfTile;
    private TextButton shelfButton;
    private TextButton.TextButtonStyle shelfButtonStyle;
    private Skin skin;
    private BitmapFont font;
    private Stage stage;
    private TextureAtlas shelfButtonAtlas;
    private ArrayList<tempTile> shelfTileArray;
    private Label.LabelStyle textStyle;

    //Test Var
    private Double money;
    private SpriteBatch batch;
    private Sprite sprite;
    private float xPosition = 180;
    private float yPosition = 640;

    private int randomWant;
    private int testInt = 0;


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
        camera.update();
        render.setView(camera);
        render.render();

        // Test Code:
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        switch (randomWant) {
            case 0:
                yPosition = yPosition - (20 * (Gdx.graphics.getDeltaTime()));
                sprite.setPosition(xPosition,yPosition);
                break;
            case 1:
               // xPosition = xPosition + (20 * (Gdx.graphics.getDeltaTime()));
                sprite.setPosition(xPosition,yPosition);
                break;
            case 2:
                xPosition = xPosition + (20 * (Gdx.graphics.getDeltaTime()));
                sprite.setPosition(xPosition,yPosition);
                break;
            default:
                break;
        }

        stage.setDebugAll(true);
        stage.draw();
    }


    @Override
    public void resize(int width, int height){
        camera.viewportWidth = width;
        camera.viewportHeight = height;

       camera.update();
    }

    @Override
    public void show ()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        font = new BitmapFont();
        shelfButtonAtlas = new TextureAtlas(Gdx.files.internal("ui/button.pack"));
        skin.addRegions(shelfButtonAtlas);
        shelfButtonStyle = new TextButton.TextButtonStyle();
        shelfButtonStyle.font = font;
        shelfButtonStyle.up = skin.getDrawable("button.up.9");
        shelfButtonStyle.down = skin.getDrawable("button.down");

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();

        maps = new TmxMapLoader().load("img/StoreTileMap.tmx");

        render = new OrthogonalTiledMapRenderer(maps);

        MapObjects shelfMapObject = maps.getLayers().get("Shelf Object Layer").getObjects();

        shelfTileArray = new ArrayList<tempTile>(0);
        int opponentIter = 2;
        int uiIter = 1;
        for (MapObject shelfObjects : shelfMapObject )
        {
            if (shelfObjects instanceof RectangleMapObject){
                if (shelfObjects.getName().equals("Shelf")) {
                    shelfTileArray.add(new tempTile());

                    Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
                    Image shelfImage = new Image(texture);
                    shelfImage.setPosition(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY());
                    shelfImage.setSize(((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    shelfImage.setColor(0, 0, 0, 0);

                    stage.addActor(shelfImage);
                    shelfImage.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new ShelfScreen());
                        }
                    });
                }
                if (shelfObjects.getName().equals("Player Info")){
                    Label playerInfo;

                    textStyle = new Label.LabelStyle();
                    textStyle.font = font;
                    Double playerMoney = 100.00;
                    playerInfo = new Label("Player 1 :" + playerMoney,textStyle);
                    playerInfo.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    stage.addActor(playerInfo);
                }
            }
            if (shelfObjects.getName().equals("UI"))
            {
                Label UI;
                textStyle = new Label.LabelStyle();
                textStyle.font = font;
                UI = new Label(Integer.toString(uiIter),textStyle);

                UI.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                stage.addActor(UI);
                UI.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new Splash());
                    }
                });
                uiIter = uiIter + 1;
            }
            if (shelfObjects.getName().equals("Opponent Info")){
                    Label OpponentInfo;
                    textStyle = new Label.LabelStyle();
                    textStyle.font = font;
                    Double OpponentMoney = 100.00;

                    OpponentInfo = new Label("Opponent " + opponentIter + " :" + OpponentMoney,textStyle);
                    OpponentInfo.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    stage.addActor(OpponentInfo);
                    opponentIter = opponentIter + 1;
            }

        }

        // Test Code
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("img/apple.jpg"));
        sprite.setSize(30,30);
        sprite.setPosition(xPosition,yPosition);

        setRandomWant();
        testInt++;
            switch (randomWant) {
                case 0:
                    System.out.println("Customer is just looking.");
                    break;
                case 1:
                    System.out.println("Customer wants bannanas.");
                    System.out.println("Bannanas are located near the cash register");
                    break;
                case 2:
                    System.out.println("Customer Wants Apples");
                    System.out.println("Apples are located on the middle left");
                    break;
                default:
                    break;
            }

    }


    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){
        maps.dispose();
    }

    @Override
    public void dispose () {
        maps.dispose();
        render.dispose();
    }

    public void setRandomWant(){
        Random rn = new Random();
        randomWant = rn.nextInt(3);
    }
}

