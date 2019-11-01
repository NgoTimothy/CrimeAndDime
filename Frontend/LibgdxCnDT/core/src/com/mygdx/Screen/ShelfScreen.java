package com.mygdx.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;

import GameClasses.Item;
import GameClasses.Tile;
import GameExceptions.PlacingItemWithNoShelfException;
import utility.Lobby;

public class ShelfScreen implements Screen {

	private BitmapFont white, black;
	private Stage stage;
	private CrimeAndDime game;
	private TextButton exitButton, addButton[];
	private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private Tile shelfTile;
    private Stage itemsStage;
    private TextField price;

	public ShelfScreen(CrimeAndDime game, int shelfIndex)
	{
		white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
		stage = new Stage();
		shelfTile = game.tileMap.shelfTileArray.get(shelfIndex);
		this.game = game;
		batch = new SpriteBatch();
		itemsStage = new Stage();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);

		exitButton = new TextButton("X", TextButtonStyle());
        exitButton.setPosition(1150, 650);
        exitButton.setWidth(50);
        exitButton.setHeight(50);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(game.tileMap);
            }
        });
        stage.addActor(exitButton);
		
		Texture texture = new Texture(Gdx.files.internal("img/shelf.jpeg"));
        Image shelfImage = new Image(texture);
        shelfImage.setPosition(0, 200);
        stage.addActor(shelfImage);
        
        	addButton = new TextButton[game.gameStore.getInventory().size()];
        	for (int i = 0; i < game.gameStore.getInventory().size() && i < 12; i++)
        	{
        		addButton[i] = new TextButton("Add", TextButtonStyle());
        		addButton[i].setPosition(1200, 525 - i * 35);
        		final int index = i;
	        	addButton[i].addListener(new ClickListener()
	        	{
		            @Override
		            public void clicked(InputEvent event, float x, float y) {	
		            	try {
		            		if (game.gameStore.getInventory().get(index).getQuantity() > 0)
		            		{
		            			if (shelfTile.getItem() != null && !shelfTile.getItem().getName().equals(game.gameStore.getInventory().get(index).getName()))
		            				game.gameStore.storeInventory.addItem(shelfTile.getItem());
		            			shelfTile.setItemOnShelf(game.gameStore.getInventory().get(index), 1);
		            			game.setShelfChanged(true);
		            		}
						} catch (PlacingItemWithNoShelfException e) {
							e.printStackTrace();
						}
		            }
		        });
		        stage.addActor(addButton[i]);
	        }  
        	
        	price = new TextField("", new TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
        	price.setWidth(150);
            price.setPosition(75, 30);
            stage.addActor(price);
            
            TextButton setPrice = new TextButton("Set", TextButtonStyle());
            setPrice.setPosition(250, 30);
            setPrice.addListener(new ClickListener()
    	        {
    	            @Override
    	            public void clicked(InputEvent event, float x, float y) {
    	            	shelfTile.getItem().setRetailCost(Math.round(Double.valueOf(price.getText()) * 100D) / 100D);
    	            	game.setShelfChanged(true);
    	            }
    	        });
            stage.addActor(setPrice);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.setDebugAll(true);
		itemsStage.setDebugAll(true);
		itemsStage.clear();
		
		if (shelfTile.getItem() != null)
		{
			Texture texture = new Texture(Gdx.files.internal("img/" + shelfTile.getItem().getName() + ".png"));
			for (int i = 0; i < shelfTile.getItem().getQuantity(); i++)
			{
		        Image image = new Image(texture);
		        if (i < 5)
		        	image.setPosition(i * 100 + 60, (i / 5) * 100 + 480);
		        else
		        {
		        	int k = i - 5;
		        	image.setPosition(k * 100 + 60, (k / 5) * 100 + 380);
		        }
		        itemsStage.addActor(image);
			}
		}
		
		
        stage.draw();
        itemsStage.draw();
        
        batch.begin();
        white.draw(batch, String.format("%-12s %-8s %s", "Item", "Quantity", "Price"), 650, 585);
        for (int i = 0; i < game.gameStore.getInventory().size(); i++)
        {
        	String s = String.format("%-12s %-8d $%.2f", game.gameStore.getInventory().get(i).getName(), game.gameStore.getInventory().get(i).getQuantity(), game.gameStore.getInventory().get(i).getWholesaleCost());
        	white.draw(batch, s, 650, 550 - i * 35);
        }
        
        if (shelfTile.getItem() != null)
        {
        	black.draw(batch, Integer.toString(shelfTile.getItem().getQuantity()) + "/10", 270, 300);        
        	white.draw(batch, String.format("Price: $%.2f", shelfTile.getItem().getRetailCost()), 75, 100);
        }
        batch.end();
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

	private TextButton.TextButtonStyle TextButtonStyle()
	{
		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(atlas);

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up.9");
		textButtonStyle.down = skin.getDrawable("button.down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;
		return textButtonStyle;
	}

}
