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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeandDime;
import com.badlogic.gdx.graphics.Color;
import utility.Lobby;
import utility.WebSocketClient;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.awt.*;

public class LobbyScreen implements Screen {

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private Table table;
    private TextButton playButton,  exitButton;
    private Label heading;
    private SpriteBatch batch;
    private CrimeandDime game;
    private String players[];
    private ArrayList<String> messages;
    private Lobby lobby;
    private WebSocketClient clientEndPoint;
    private String username;
    
    public LobbyScreen(CrimeandDime game, Lobby lobby)
    {
    	this.game = game;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	players = new String[4];
    	for(int i = 0; i < lobby.getNumPlayers(); i++)
    		players[i] = "player" + Integer.toString(i + 1);
    	username = "player" + Integer.toString(lobby.getNumPlayers());
    	messages = new ArrayList<String>();
    	this.lobby = lobby;
    	try {
			newMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    void newMethod() throws Exception
    {
    	clientEndPoint = new WebSocketClient(new URI("ws://localhost:8080/websocket/" + lobby.getLobbyID() + "/" + username));
        clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
                    public void handleMessage(String message) {
                    	messages.add(message);
                    }
                });
        	
            clientEndPoint.sendMessage(username + " has joined this lobby.");
    }
    
    @Override
    public void render(float delta){
    	
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);      
        
        stage.act(delta);

        stage.setDebugAll(true);

        stage.draw();
        
        batch.begin();
        white.draw(batch, lobby.getLobbyName(), 500, 700);
        for(int i = 0; i < 4; i++)
        {
	        if (players[i] != null)
	        	white.draw(batch, players[i], i * 200 + 250, 400);
	        else
	        	white.draw(batch, "Open", i * 200 + 250, 400);
        }
        
        for(int i = messages.size() - 1; i > -1 && i > messages.size() - 6; i--)
        {
        	white.draw(batch, messages.get(i), 50, i * 30 + 50);
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
            	leaveLobby();
            	try {
					clientEndPoint.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	lobby.setNumPlayers(lobby.getNumPlayers() - 1);
            	game.setScreen(new Lobbies(game));
            }
        });
        stage.addActor(exitButton);
        
        playButton = new TextButton("Play", TextButtonStyle());
        playButton.setPosition(1100, 50);
        stage.addActor(playButton);
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
    
    private void leaveLobby()
    {
    	try {
        	String url = "http://coms-309-tc-3.misc.iastate.edu:8080/deleteUser";
    		URL obj = new URL(url);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    		String USER_AGENT = "Mozilla/5.0";
    		
    		//add request header
    		con.setRequestMethod("POST");
    		con.setRequestProperty("User-Agent", USER_AGENT);
    		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

    		String urlParameters = "lobbyID=" + lobby.getLobbyID();
    		
    		// Send post request
    		con.setDoOutput(true);
    		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    		wr.writeBytes(urlParameters);
    		wr.flush();
    		wr.close();

    		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'POST' request to URL : " + url);
    		System.out.println("Post parameters : " + urlParameters);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();
    		
        	}
        	catch(Exception e)	{
        		System.out.print(e);
        	}   
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
