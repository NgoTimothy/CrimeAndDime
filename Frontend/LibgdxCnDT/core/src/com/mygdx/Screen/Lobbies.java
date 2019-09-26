package com.mygdx.Screen;

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
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.cndt.CrimeandDime;
import com.badlogic.gdx.graphics.Color;

import utility.Lobby;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.awt.*;

public class Lobbies implements Screen {

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private Table table;
    private TextButton playButton, exitButton, joinButton[];
    private Label heading;
    private SpriteBatch batch;
    private CrimeandDime game;
    ArrayList<Lobby> lobbyList;
    TextField newLobby;
    Color color;
    
    public Lobbies(CrimeandDime game)
    {
    	this.game = game;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	lobbyList = new ArrayList<Lobby>();	
    }
    
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
    	stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        exitButton = new TextButton("X", TextButtonStyle());
        exitButton.setPosition(1100, 600);
        exitButton.setWidth(50);
        exitButton.setHeight(50);
        exitButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(new MainMenuCnD(game));
            }
        });
        stage.addActor(exitButton);
        
        playButton = new TextButton("Play", TextButtonStyle());
        playButton.setPosition(1100, 50);
        stage.addActor(playButton);
        
        getLobbies();
        joinButton = new TextButton[lobbyList.size()];
        for(int i = 0; i < 10 && i < lobbyList.size(); i++)
        {
	        joinButton[i] = new TextButton("Join", TextButtonStyle());
	        joinButton[i].setPosition(700, 575 - i * 35);
	        joinButton[i].addListener(new ClickListener()
	        {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {
	            	game.setScreen(new LobbyScreen(game));
	            }
	        });
	        stage.addActor(joinButton[i]);
        }
        
//        TextFieldStyle style = new TextFieldStyle(black, );
//    	newLobby = new TextField("", style);
//        newLobby.setPosition(200, 200);
//        stage.addActor(newLobby);
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
    
    private ArrayList<Lobby> getLobbies()
    {
    	//Get a string of lobby info from the API
    	String result = "";
    	try {
        	String url = "http://coms-309-tc-3.misc.iastate.edu:8080/lobbyList";
    		
    		URL obj = new URL(url);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    		// optional default is GET
    		con.setRequestMethod("GET");

    		String USER_AGENT = "Mozilla/5.0";
    		
    		//add request header
    		con.setRequestProperty("User-Agent", USER_AGENT);

    		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'GET' request to URL : " + url);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();

    		result = response.toString();
    		//print result
    		System.out.println(response.toString());
    		
        	}
        	catch(Exception e)	{
        		System.out.print(e);
        	}    	    	
    	
    		String delims = "[{}\":,]+";
    		String[] tokens = result.split(delims);
    		for (String s : tokens) {
    			System.out.println(s);
    		}
    		
    		//Parse the string
    		Lobby lobby;
    		for (int i = 1; i < tokens.length - 8; i += 8)
    		{
    			if(tokens[i + 5].equals("false"))
    				lobby = new Lobby(Integer.parseInt(tokens[i + 1]), tokens[i + 3], Integer.parseInt(tokens[i + 7]));
    			else
    			{
    				lobby = new Lobby(Integer.parseInt(tokens[i + 1]), tokens[i + 3], Integer.parseInt(tokens[i + 9]));
    				i += 2;
    			}
    			lobbyList.add(lobby);
    		}
    		
    		return lobbyList;
    }
    
}
