package com.mygdx.Screen;

import GameClasses.Tile;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.Screen.InventoryScreen;
import com.mygdx.Screen.ShelfScreen;
import com.mygdx.cndt.CrimeAndDime;
import GameClasses.Tile;
import GameExceptions.ShelfWithNoDirectionException;
import com.mygdx.cndt.CrimeAndDime;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mygdx.entities.Customer;
import com.mygdx.entities.Wall;
import org.json.JSONArray;
import utility.WebSocketClient;

public class tileMapScreen implements Screen {

    private static final int closingTime = 20;
    private BitmapFont white;
    private TiledMap maps;
    private TiledMapTileLayer layer;
    private OrthogonalTiledMapRenderer render;
    private OrthographicCamera camera;
    private TextButton shelfButton;
    private TextButton.TextButtonStyle shelfButtonStyle;
    private Skin skin;
    private BitmapFont font;
    private Stage stage;
    private TextureAtlas shelfButtonAtlas;
    public ArrayList<Tile> shelfTileArray;
    private CrimeAndDime game;
    private MapObjects shelfMapObject;
    private WebSocketClient socketClient;
    private Label clock;
    private Label day;

    // Customer
    private SpriteBatch batch;
    private Sprite sprite;

    private Vector2 spawnPoint = new Vector2(175,620);

    private Customer customer;
    private ArrayList<Wall> wallArrayList;

    public tileMapScreen(CrimeAndDime game) {
    	this.game = game;
        white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
        maps = new TmxMapLoader().load("img/StoreTileMap.tmx");
        render = new OrthogonalTiledMapRenderer(maps);
        shelfMapObject = maps.getLayers().get("Shelf Object Layer").getObjects();
        shelfTileArray = new ArrayList<Tile>();
        wallArrayList = new ArrayList<Wall>(0);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("img/sprite.png"));
        Vector2 tempVector2 = new Vector2(625,70);
        customer = new Customer((int) spawnPoint.x,(int) spawnPoint.y,sprite,tempVector2);
        customer.setWallArrayList(wallArrayList);
        customer.setPosition((int) spawnPoint.x,(int) spawnPoint.y);
        int i = 0;
    	for (MapObject shelfObjects : shelfMapObject)
        {
    		if (shelfObjects instanceof RectangleMapObject){
                if (shelfObjects.getName().equals("Shelf")) {
                    Tile shelfTile = new Tile(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY(), Tile.shelfDirection.NORTH);
                    shelfTile.setTileId(i);
                    shelfTileArray.add(shelfTile);
                    i++;
                }
    		}
        }
    	try {
    	    //socketClient = new WebSocketClient(new URI("ws://localhost:8082/websocket/" + 25 + "/" + "Tim"), game);
            socketClient = new WebSocketClient(
                    new URI("ws://coms-309-tc-3.misc.iastate.edu:8080/websocket/" + game.getLobbyID() + "/" + game.getUsername()), game);
            game.setOnBreak(true);
    	    //game.setStartTimer(true);//Delete this shit

    	} catch (Exception e) {
    	    e.printStackTrace();
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_ALPHA_BITS);
        camera.update();
        render.setView(camera);
        render.render();
        stage.setDebugAll(true);
        clock.setText(timeToString(game.getHour()));
        day.setText("Day: " + Integer.toString(game.getDay()));
        if(game.getCustomerBuyItems()) {
            game.setCustomerBuyItems(false);
            getListOfItemsOnShelves();
        }
        if(game.getUpdateShelves()) {
            game.setUpdateShelves(false);
            game.clearPurchasedShelves();
            updateShelves();
        }
        stage.draw();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        customer.draw(batch);
        customer.setPosition(customer.getPosition().x, customer.getPosition().y);
        batch.end();

        for (int i = 0; i < wallArrayList.size(); i++){
            Wall tempWall = wallArrayList.get(i);
            if (tempWall.collides(customer.getBounds())){
                customer.stop();
            }
        }
    }

    private void updateShelves() {
        ArrayList<Tile> tiles = (ArrayList<Tile>) game.purchasedShelves.clone();
        for(int i = 0; i < tiles.size(); i++) {
            int index = shelfTileArray.indexOf(tiles.get(i));
            if(index >= 0) {//If purchased tile is in array then subtract quantity of screen tile by the purchased tile
                shelfTileArray.get(index).getItem().subtractQuantity(tiles.get(i).getItem().getQuantity());
                if(shelfTileArray.get(index).getItem().getQuantity() == 0)
                    shelfTileArray.get(index).removeItemFromShelf();
            }
        }
    }

    @Override
    public void resize(int width, int height){
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show () {
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
        int i = 0;
        int x = 1;
        for (MapObject shelfObjects : shelfMapObject)
        {
            if (shelfObjects instanceof RectangleMapObject){
                if (shelfObjects.getName().equals("Shelf")) {

                    Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
                    Image shelfImage = new Image(texture);
                    shelfImage.setPosition(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY());
                    shelfImage.setSize(((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    shelfImage.setColor(0, 0, 0, 0);
                    stage.addActor(shelfImage);
                    final int index = i;
                    shelfImage.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            game.setScreen(new ShelfScreen(game, index));
                        }
                    });
                    i++;
                }
                if (shelfObjects.getName().equals("Player Info")){
                    Label playerInfo;
                    Label.LabelStyle textStyle;
                    BitmapFont font = new BitmapFont();

                    textStyle = new Label.LabelStyle();
                    textStyle.font = font;
                    Double playerMoney = 100.00;
                    playerInfo = new Label("Player 1 :" + game.gameStore.getBalance(),textStyle);
                    playerInfo.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    stage.addActor(playerInfo);
                }
            }
            if (shelfObjects.getName().equals("DayCounter"))
            {
                Label.LabelStyle textStyle;
                BitmapFont font = new BitmapFont();

                textStyle = new Label.LabelStyle();
                textStyle.font = font;

                day = new Label("Day: " + game.getDay(), textStyle);

                day.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                day.setAlignment(100);
                day.setFontScale(2);
                stage.addActor(day);
                x += 1;
            }
            if(shelfObjects.getName().equals("Clock")) {
                Label.LabelStyle textStyle;
                BitmapFont font = new BitmapFont();

                textStyle = new Label.LabelStyle();
                textStyle.font = font;

                clock = new Label(timeToString(game.getHour()), textStyle);

                clock.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                clock.setAlignment(100);
                clock.setFontScale(2);
                stage.addActor(clock);
                x = x + 1;
            }
            if (shelfObjects.getName().equals("Opponent Info")){
                Label playerInfo;
                Label.LabelStyle textStyle;
                BitmapFont font = new BitmapFont();

                textStyle = new Label.LabelStyle();
                textStyle.font = font;
                Double playerMoney = 100.00;
                playerInfo = new Label("Opponent 1 :" + playerMoney,textStyle);
                playerInfo.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                stage.addActor(playerInfo);
            }
            if (shelfObjects.getName().equals("BLOCKED")){
                wallArrayList.add(new Wall((RectangleMapObject) shelfObjects));

                // Temp code
                Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
                Image shelfImage = new Image(texture);
                shelfImage.setPosition(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY());
                shelfImage.setSize(((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                shelfImage.setColor(0, 0, 0, 0);

                stage.addActor(shelfImage);
            }

            Label playerInfo;
            Label.LabelStyle textStyle;
            BitmapFont font = new BitmapFont();
            textStyle = new Label.LabelStyle();
            textStyle.font = font;
            textStyle = new Label.LabelStyle();
            textStyle.font = font;
            Double playerMoney = 100.00;
            playerInfo = new Label("Inventory",textStyle);
            playerInfo.setBounds(1100, 20, 100, 50);
            stage.addActor(playerInfo);
            playerInfo.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new InventoryScreen(game));
                }
            });
        }
    }


    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide(){
        //maps.dispose();
    }

    @Override
    public void dispose() {
        maps.dispose();
        render.dispose();
        game = null;
    }

    private String timeToString(int hour) {
        String AMOrPM = "AM";
        int timeOfDay = hour;
        if(timeOfDay > 12) {
            timeOfDay -= 12;
            AMOrPM = "PM";
        }
        if(hour >= closingTime && game.getStartTimer()) {
            //sendShelfListToServer();
            game.setStartTimer(false);
            socketClient.sendMessage("sendMyMoney " + game.gameStore.getBalance());
        }
        else if(!game.getStartTimer() && game.getNextDay()) {
            try {
                Thread.sleep(500);
                game.advanceDay();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return timeOfDay + ":00 " + AMOrPM;
    }

    private void sendShelfListToServer() {
        JSONArray sendToServerArray = listToJSON();
        String msg = "storeInfo" + sendToServerArray.toString().replace("\\", "");
        msg = msg.replace("[", "");
        socketClient.sendMessage(msg);
    }

    private JSONArray listToJSON() {
        JSONArray jsArray = new JSONArray(getListOfItemsOnShelves());
        return jsArray;
    }

    public ArrayList<Tile> getListOfItemsOnShelves() {
        ArrayList<Tile> tileArr = new ArrayList<>();
        for(int i = 0; i < shelfTileArray.size(); i++) {
            if(shelfTileArray.get(i).getItem() != null) {
                if(shelfTileArray.get(i).getItem().getQuantity() == 0)
                    shelfTileArray.get(i).removeItemFromShelf();
                else
                    tileArr.add(shelfTileArray.get(i));
            }
        }
        game.setShelvesToBeBoughtFrom(tileArr);
        return tileArr;
    }

    public Customer getCustomerTestCollsion(){
        return customer;
    }
}
