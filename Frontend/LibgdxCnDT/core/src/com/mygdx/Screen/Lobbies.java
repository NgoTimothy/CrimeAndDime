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
import com.mygdx.cndt.*;
import utility.*;

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
    private Table table;
    private TextButton playButton, exitButton, joinButton[];
    private Label heading;
    private SpriteBatch batch;
    private CrimeandDime game;
    ArrayList<Lobby> lobbyList;
    TextField newLobby;
    Color color;
    
    public Lobbies(CrimeandDime newGame)
    {
    	game = newGame;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	lobbyList = new ArrayList<Lobby>();	
    	stage = new Stage();
    	color = new Color();
    }

    //For testing purposes only
    public Lobbies() {
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
	        final Lobby l = lobbyList.get(i);
	        joinButton[i].addListener(new ClickListener()
	        {
	            @Override
	            public void clicked(InputEvent event, float x, float y) {	
	            	joinLobby(l.getLobbyID());
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
    	String result = APIGetAllLobbies();
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
    	String result = APIAddLobby(lobbyName);
    	getLobbies();
    	System.out.println(result);
    	return result;
    }
    
    public String joinLobby(int lobbyID) {
		String result = APIJoinALobby(lobbyID);
		System.out.println(result);
		return result;
    }

    public String APIGetAllLobbies() {
    	String result = "failure";
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

		}
		catch(Exception e)	{
			System.out.print(e);
		}
		return result;
	}

	public String APIAddLobby(String lobbyName) {
		try {
			String url = "http://coms-309-tc-3.misc.iastate.edu:8080/addLobby";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			String USER_AGENT = "Mozilla/5.0";

			//add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "lobbyName=" + lobbyName;

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

			return response.toString();
		}
		catch(Exception e)	{
			System.out.print(e);
		}
		return "failure";
	}

	public String APIJoinALobby(int lobbyID) {
		try {
			String url = "http://coms-309-tc-3.misc.iastate.edu:8080/addToLobby";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			String USER_AGENT = "Mozilla/5.0";

			//add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String urlParameters = "lobbyID=" + lobbyID;

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

			return response.toString();
		}
		catch(Exception e)	{
			System.out.print(e);
		}
		return "failure";
	}

	public ArrayList<Lobby> getListOfLobbies() {
    	return lobbyList;
	}


}
