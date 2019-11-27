package com.mygdx.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.TileMapGame.TileMapGame;
import com.mygdx.cndt.*;
import utility.*;
import Services.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Lobbies implements Screen {

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private TextButton playButton, exitButton, joinButton[];
    private SpriteBatch batch;
    private CrimeAndDime game;
    private ArrayList<Lobby> lobbyList;
    private TextField newLobby;
    private Color color;
    private LobbyService lobbyService;
    
    public Lobbies(CrimeAndDime newGame)
    {
    	game = newGame;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	lobbyList = new ArrayList<Lobby>();	
    	stage = new Stage();
    	color = new Color();
    	lobbyService = new LobbyService();//This should be passed in the constructor
    }

    //For testing purposes only
    public Lobbies(LobbyService newService) {
    	lobbyService = newService;
    	lobbyList = new ArrayList<Lobby>();
	}//Pass it in here
    
    @Override
    public void render(float delta){
    	
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);      
        
        stage.act(delta);

        stage.setDebugAll(true);

        stage.draw();
        
        batch.begin();
        white.draw(batch, "Lobbies", 500, 700);
        
        for(int i = 0; i < 10 && i < lobbyList.size(); i++)
		{
        	white.draw(batch, lobbyList.get(i).getLobbyName(), 300, 600 - i * 35);
        	white.draw(batch, lobbyList.get(i).getNumPlayers() + "/4", 600, 600 - i * 35);
        }
        
        batch.end();
    }


    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
        
        exitButton = new TextButton("X", TextButtonStyle());
        exitButton.setPosition(1100, 600);
        exitButton.setWidth(50);
        exitButton.setHeight(50);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new Splash(game));
            }
        });
        stage.addActor(exitButton);
        
        playButton = new TextButton("Play", TextButtonStyle());
        playButton.setPosition(1100, 50);
        playButton.addListener(new ClickListener() {
        	@Override
			public void clicked(InputEvent event, float x, float y) {
        		game.setScreen(new tileMapScreen(game));
			}
        });
        stage.addActor(playButton);
        
        getLobbies();
        
        joinButton = new TextButton[lobbyList.size()];
        for(int i = 0; i < 10 && i < lobbyList.size(); i++)
        {
	        joinButton[i] = new TextButton("Join", TextButtonStyle());
	        joinButton[i].setPosition(700, 575 - i * 35);
	        final Lobby l = lobbyList.get(i);
	        joinButton[i].addListener(new ClickListener()
	        {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {	
	            	joinLobby(l.getLobbyID(), game.getUsername());
	            	game.setScreen(new LobbyScreen(game, l));
	            }
	        });
	        stage.addActor(joinButton[i]);
        }
        
    	newLobby = new TextField("", new TextFieldStyle(white, Color.WHITE, new BaseDrawable(), new BaseDrawable(), new BaseDrawable()));
    	newLobby.setWidth(300);
        newLobby.setPosition(200, 200);
        stage.addActor(newLobby);
        
        TextButton startLobbyButton = new TextButton("Create Lobby", TextButtonStyle());
        startLobbyButton.setPosition(550, 200);
        startLobbyButton.addListener(new ClickListener()
	        {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {	            	
	            	addLobby(newLobby.getText());
	            	System.out.println(newLobby.getText());
	            }
	        });
        stage.addActor(startLobbyButton);
    }


    @Override
    public void resize(int y, int x)	{
    	
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
    
    public ArrayList<Lobby> getLobbies()
    {
    	//Get a string of lobby info from the API
    	String result = lobbyService.APIGetAllLobbies();
    	String delims = "[{}\":,]+";
    	String[] tokens = result.split(delims);
    		
    		//Parse the string
		Lobby lobby;
		for (int i = 1; i < tokens.length - 8; i += 8) {
			if(tokens[i + 5].equals("false"))
				lobby = new Lobby(Integer.parseInt(tokens[i + 1]), tokens[i + 3], Integer.parseInt(tokens[i + 7]));
			else {
				lobby = new Lobby(Integer.parseInt(tokens[i + 1]), tokens[i + 3], Integer.parseInt(tokens[i + 9]));
				i += 2;
			}
			lobbyList.add(lobby);
		}
    		
		return lobbyList;
    }   
    
    public String addLobby(String lobbyName) {
    	String result = lobbyService.APIAddLobby(lobbyName);
    	getLobbies();
    	System.out.println(result);
    	return result;
    }
    
    public String joinLobby(int lobbyID, String username) {
		String result = lobbyService.APIJoinALobby(lobbyID, username);
		System.out.println(result);
		return result;
    }

	public ArrayList<Lobby> getListOfLobbies() {
    	return lobbyList;
	}


}
