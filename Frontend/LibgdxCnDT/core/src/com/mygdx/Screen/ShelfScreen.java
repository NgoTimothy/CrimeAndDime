package com.mygdx.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;

import GameClasses.Item;
import GameClasses.Tile;
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

	public ShelfScreen(CrimeAndDime game, Tile shelfTile)
	{
		white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
		stage = new Stage();
		this.shelfTile = shelfTile;
		this.game = game;
		batch = new SpriteBatch();
	}

	public ShelfScreen(CrimeAndDime game, Item itemsOnShelf)
	{
		white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
		stage = new Stage();
		this.game = game;
		batch = new SpriteBatch();
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
				game.setScreen(new tileMapScreen(game));
			}
		});
		stage.addActor(exitButton);

		Texture texture = new Texture(Gdx.files.internal("img/shelf.jpeg"));
		Image shelfImage = new Image(texture);
		shelfImage.setPosition(0, 200);
		stage.addActor(shelfImage);
		texture = new Texture(Gdx.files.internal("img/banana.png"));
		shelfImage = new Image(texture);
		shelfImage.setSize(75, 75);
		stage.addActor(shelfImage);

		if (shelfTile.getItem() == null || shelfTile.getItem().getQuantity() == 0)
		{
			addButton = new TextButton[game.gameStore.getInventory().size()];
			for (int i = 0; i < game.gameStore.getInventory().size() && i < 12; i++)
			{
				addButton[i] = new TextButton("Add", TextButtonStyle());
				addButton[i].setPosition(1200, 525 - i * 35);
				addButton[i].addListener(new ClickListener()
				{
					@Override
					public void clicked(InputEvent event, float x, float y) {
						//game.gameStore.getInventory().get(i);
					}
				});
				stage.addActor(addButton[i]);
			}
		}

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.setDebugAll(true);
		stage.draw();

		batch.begin();
		white.draw(batch, String.format("%-12s %-8s %s", "Item", "Quantity", "Price"), 650, 585);
		for (int i = 0; i < game.gameStore.getInventory().size(); i++)
		{
			String s = String.format("%-12s %-8d $%.2f", game.gameStore.getInventory().get(i).getName(), game.gameStore.getInventory().get(i).getQuantity(), game.gameStore.getInventory().get(i).getWholesaleCost());
			//String s = game.gameStore.getInventory().getInventory().get(i).getName() + " " + game.gameStore.getInventory().getInventory().get(i).getQuantity() + " $" + game.gameStore.getInventory().getInventory().get(i).getWholesaleCost();
			white.draw(batch, s, 650, 550 - i * 35);
		}

		white.draw(batch, game.gameStore.getInventory().get(0).getName(), 100, 100);
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
