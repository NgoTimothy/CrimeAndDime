package com.mygdx.Screen;

import GameClasses.Customer;
import GameClasses.Item;
import GameClasses.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import com.mygdx.entities.CustomerSprite;
import com.mygdx.entities.Wall;
import org.json.JSONArray;

import utility.Player;
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
    private Label clock;
    private Label day;
    Label playerInfo;
    private Label[] playerLabels;


    // Customer
    private SpriteBatch batch;
    private Sprite sprite;

    private Vector2 spawnPoint = new Vector2(175, 620);

    private ArrayList<Wall> wallArrayList;

    private ArrayList<Item> inventoryList;
    private ArrayList<CustomerSprite> customerSpriteList;

    public tileMapScreen(CrimeAndDime game) {
    	game.tileMap = this;
        this.game = game;
        white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
        maps = new TmxMapLoader().load("img/StoreTileMap.tmx");
        render = new OrthogonalTiledMapRenderer(maps);
        shelfMapObject = maps.getLayers().get("Shelf Object Layer").getObjects();
        shelfTileArray = new ArrayList<Tile>();
        wallArrayList = new ArrayList<Wall>(0);
        customerSpriteList = new ArrayList<CustomerSprite>(0);
        inventoryList = new ArrayList<Item>(0);
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("img/customers/customer1-down.png"));
        playerLabels = new Label[3];
        Label.LabelStyle textStyle;
        BitmapFont font = new BitmapFont();

        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        playerInfo = new Label("Player 1 :" + game.gameStore.getBalance(), textStyle);

        int i = 0;
        for (MapObject shelfObjects : shelfMapObject) {
            if (shelfObjects instanceof RectangleMapObject) {
                if (shelfObjects.getName().equals("Shelf")) {
                    Tile shelfTile = new Tile(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY(), Tile.shelfDirection.NORTH);
                    shelfTile.setTileId(i);
                    shelfTileArray.add(shelfTile);
                    i++;
                }
            }
            for(int j = 0; j < 3; j++)
            {
            	if (shelfObjects.getName().equals("Opponent Info " + (j + 1))){
                    playerLabels[j] = new Label("",textStyle);
                    playerLabels[j].setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(),((RectangleMapObject) shelfObjects).getRectangle().getY(),((RectangleMapObject) shelfObjects).getRectangle().getWidth(),((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                    break;
                }
            }
        }
        try {
            //socketClient = new WebSocketClient(new URI("ws://localhost:8082/websocket/" + 25 + "/" + "Tim"), game);
            //game.clientEndPoint = new WebSocketClient(new URI("ws://coms-309-tc-3.misc.iastate.edu:8080/websocket/" + game.lobby.getLobbyID() + "/" + game.getUsername()), game);

            game.setOnBreak(true);
            //game.setStartTimer(true);//Delete this shit

        } catch (Exception e) {
            e.printStackTrace();
        }

        inventoryList = game.items;
    }

    @Override
    public void render(float delta) {
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
        if (game.getCustomerBuyItems()) {
            game.setCustomerBuyItems(false);
            getListOfItemsOnShelves();
            for(int i = 0; i < game.customers.size(); i++)
            {
                    spawnCustomerSprite((int) spawnPoint.x,(int) spawnPoint.y,sprite,game.customers.get(i).itemLocation);
                    game.customers.remove(i);
                    updatePlayerInfo();
            }
        }
        if (game.getUpdateShelves()) {
            game.setUpdateShelves(false);
            game.clearPurchasedShelves();
            updateShelves();
        }
        stage.draw();

        if (!customerSpriteList.isEmpty())
        {
            for(int i = 0; i < customerSpriteList.size(); i++)
            {
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                customerSpriteList.get(i).draw(batch);
                customerSpriteList.get(i).setPosition(customerSpriteList.get(i).getPosition().x, customerSpriteList.get(i).getPosition().y);
                batch.end();

                for (int j = 0; j < wallArrayList.size(); j++) {
                    Wall tempWall = wallArrayList.get(j);
                    if (tempWall.collides(customerSpriteList.get(i).getBounds())) {
                        customerSpriteList.get(i).stop();
                    }
                }
                if (customerSpriteList.get(i).hasReachedEnd())
                {

                    customerSpriteList.remove(i);
                }
            }
        }
    }

    private void updateShelves() {
    	/*
        ArrayList<Tile> tiles = (ArrayList<Tile>) game.purchasedShelves.clone();
        for (int i = 0; i < tiles.size(); i++) {
            int index = shelfTileArray.indexOf(tiles.get(i));
            if (index >= 0) {//If purchased tile is in array then subtract quantity of screen tile by the purchased tile
                shelfTileArray.get(index).getItem().subtractQuantity(tiles.get(i).getItem().getQuantity());
                if (shelfTileArray.get(index).getItem().getQuantity() == 0)
                    shelfTileArray.get(index).removeItemFromShelf();
            }
        }
        */
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {
    	try {
			game.clientEndPoint = new WebSocketClient(new URI("ws://localhost:8080/websocket/" + game.lobby.getLobbyID() + "/" + game.getUsername()), game);
			final String username = game.getUsername();
	        game.clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
	            @Override
				public void handleMessage(String message) {
	            	if (message.contains("money"))
	            	{
	            		String[] tokens = message.split(":");
	            		if (!tokens[1].contains(game.getUsername()))
	            		{
		            		for(int i = 0; i < playerLabels.length; i++)
		            		{
		            			if (playerLabels[i].getText().toString().equals("") || playerLabels[i].getText().toString().contains(tokens[1]))
		            			{
		            				playerLabels[i].setText(tokens[1] + ": " + tokens[2]);
		            				break;
		            			}
		            		}
	            		}
	            	}
	            }
	        });
	        updatePlayerInfo();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        camera.setToOrtho(false, w, h);
        camera.update();
        int i = 0;
        int x = 1;
        for (MapObject shelfObjects : shelfMapObject) {
            if (shelfObjects instanceof RectangleMapObject) {
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
            }
            if (shelfObjects.getName().equals("DayCounter")) {
                Label.LabelStyle textStyle;
                BitmapFont font = new BitmapFont();

                textStyle = new Label.LabelStyle();
                textStyle.font = font;

                day = new Label("Day: " + game.getDay(), textStyle);

                day.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY(), ((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                day.setAlignment(100);
                day.setFontScale(2);
                stage.addActor(day);
                x += 1;
            }
            if (shelfObjects.getName().equals("Clock")) {
                Label.LabelStyle textStyle;
                BitmapFont font = new BitmapFont();

                textStyle = new Label.LabelStyle();
                textStyle.font = font;

                clock = new Label(timeToString(game.getHour()), textStyle);

                clock.setBounds(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY(), ((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                clock.setAlignment(100);
                clock.setFontScale(2);
                stage.addActor(clock);
                x = x + 1;
            }
            for(int j = 0; j < 3; j++)
            {
            	if (shelfObjects.getName().equals("Opponent Info " + (j + 1))){
                    stage.addActor(playerLabels[j]);
                    break;
                }
            }
            if (shelfObjects.getName().equals("BLOCKED")) {
                wallArrayList.add(new Wall((RectangleMapObject) shelfObjects));

                Texture texture = new Texture(Gdx.files.internal("img/transparentPicture.png"));
                Image shelfImage = new Image(texture);
                shelfImage.setPosition(((RectangleMapObject) shelfObjects).getRectangle().getX(), ((RectangleMapObject) shelfObjects).getRectangle().getY());
                shelfImage.setSize(((RectangleMapObject) shelfObjects).getRectangle().getWidth(), ((RectangleMapObject) shelfObjects).getRectangle().getHeight());
                shelfImage.setColor(0, 0, 0, 0);

                stage.addActor(shelfImage);
            }
        }
        
        Label.LabelStyle textStyle;
        BitmapFont font = new BitmapFont();
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        Label inventoryLabel = new Label("Inventory", textStyle);
        inventoryLabel.setBounds(1100, 20, 75, 50);
        stage.addActor(inventoryLabel);
        inventoryLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new InventoryScreen(game));
            }
        });
        
        playerInfo = new Label(game.getUsername() + " :" + game.gameStore.getBalance(), textStyle);
        playerInfo.setBounds(1120, 585, 120, 30);
        stage.addActor(playerInfo);
        
        Texture texture = new Texture(Gdx.files.internal("img/marketing.png"));
        Image image = new Image(texture);
        image.setPosition(1200, 20);
        image.setSize(50, 50);
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.gameStore.addBalance(-10);
                updatePlayerInfo();
                game.gameStore.marketScore =  game.gameStore.marketScore + 1;
            }
        });
        stage.addActor(image);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //maps.dispose();
    	try {
			game.clientEndPoint.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void dispose() {
        maps.dispose();
        render.dispose();
        try {
			game.clientEndPoint.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private String timeToString(int hour) {
        String AMOrPM = "AM";
        int timeOfDay = hour;
        if (timeOfDay > 12) {
            timeOfDay -= 12;
            AMOrPM = "PM";
        }
        if (hour >= closingTime && game.getStartTimer()) {
            //sendShelfListToServer();
            game.setStartTimer(false);
        } else if (!game.getStartTimer() && game.getNextDay()) {
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
    }

    private JSONArray listToJSON() {
        JSONArray jsArray = new JSONArray(getListOfItemsOnShelves());
        return jsArray;
    }

    public ArrayList<Tile> getListOfItemsOnShelves() {
        ArrayList<Tile> tileArr = new ArrayList<>();
        for (int i = 0; i < shelfTileArray.size(); i++) {
            if (shelfTileArray.get(i).getItem() != null && shelfTileArray.get(i).getItem().getQuantity() != 0 ) {
                tileArr.add(shelfTileArray.get(i));
            }
        }
        game.setShelvesToBeBoughtFrom(tileArr);
        return tileArr;
    }

    public void spawnCustomerSprite(int x, int y, Sprite sprite, Vector2 vector2) {
        CustomerSprite tempCustomerSprite = new CustomerSprite((int) spawnPoint.x, (int) spawnPoint.y, sprite, vector2);
        tempCustomerSprite.setWallArrayList(wallArrayList);
        tempCustomerSprite.setPosition((int) spawnPoint.x, (int) spawnPoint.y);
        customerSpriteList.add(tempCustomerSprite);
    }
    
    public void updatePlayerInfo()
    {
    	playerInfo.setText(game.getUsername() + ": " + game.gameStore.getBalance());
    	game.clientEndPoint.sendMessage("money:" + game.getUsername() + ":" + game.gameStore.getBalance());
    }
}
