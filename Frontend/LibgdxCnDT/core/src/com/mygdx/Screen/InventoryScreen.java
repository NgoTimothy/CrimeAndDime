package com.mygdx.Screen;

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
import com.mygdx.cndt.CrimeandDime;

import GameClasses.Item;
import GameExceptions.PlacingItemWithNoShelfException;

public class InventoryScreen implements Screen {

	private BitmapFont white, black;
	private CrimeandDime game;
	private Stage stage;
	private TextButton exitButton, buyButton[];
	private TextureAtlas atlas;
    private Skin skin;
    private SpriteBatch batch;
    private Stage itemsStage;
	
	public InventoryScreen(CrimeandDime game)
	{
		white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
		this.game = game;
		stage = new Stage();
		batch = new SpriteBatch();
		itemsStage = new Stage();
	}
	
	@Override
	public void show() {
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
        
        buyButton = new TextButton[game.items.size()];
    	for (int i = 0; i < game.items.size() && i < 12; i++)
    	{
    		buyButton[i] = new TextButton("Buy", TextButtonStyle());
    		buyButton[i].setPosition(900, 575 - i * 50);
    		final int index = i;
        	buyButton[i].addListener(new ClickListener()
        	{
	            @Override
	            public void clicked(InputEvent event, float x, float y) {	
	            	Item item = game.items.get(index).copy();
	            	item.setQuantity(1);
	            	boolean tf = game.gameStore.purchaseItemToInventory(item);
	            	if (tf)
	            		game.items.get(index).addQuantity(1);
	            }
	        });
	        stage.addActor(buyButton[i]);
        }
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.setDebugAll(true);
		itemsStage.setDebugAll(true);
		itemsStage.clear();
		
		for (int i = 0; i < game.items.size(); i++)
		{
			Texture texture = new Texture(Gdx.files.internal("img/" + game.items.get(i).getName() + ".png"));
	        Image image = new Image(texture);
	        image.setPosition(290, 565 - i * 50);
	        image.setSize(50, 50);
	        itemsStage.addActor(image);
		}
		
		itemsStage.draw();
		stage.draw();
		
		batch.begin();
        white.draw(batch, String.format("%-12s %-8s %s", "Item", "Quantity", "Price"), 350, 650);
        for (int i = 0; i < game.items.size(); i++)
        {
        	String s = String.format("%-12s %-8d $%.2f", game.items.get(i).getName(), game.items.get(i).getQuantity(), game.items.get(i).getWholesaleCost());
        	white.draw(batch, s, 350, 600 - i * 50);
        }
        
        white.draw(batch, "$" + Double.toString(game.gameStore.getBalance()), 700, 700);
        
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
