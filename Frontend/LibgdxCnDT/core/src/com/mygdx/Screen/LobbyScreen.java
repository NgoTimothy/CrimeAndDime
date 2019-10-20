package com.mygdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.cndt.CrimeAndDime;
import utility.Lobby;
import utility.WebSocketClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class LobbyScreen implements Screen {

    private com.badlogic.gdx.scenes.scene2d.Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont white, black;
    private TextButton playButton,  exitButton;
    private SpriteBatch batch;
    private CrimeAndDime game;
    private ArrayList<String> messages;
    private Lobby lobby;
    private WebSocketClient clientEndPoint;
    private String username;
    
    public LobbyScreen(CrimeAndDime newGame, Lobby newLobby)
    {
    	lobby = newLobby;
    	getLobby();
    	game = newGame;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	username = "player" + Integer.toString(lobby.getNumPlayers());
    	messages = new ArrayList<String>();
    	try {
			connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	System.out.println(lobby.getLobbyID());
    }

    //For testing purposes only
	public LobbyScreen() {
		lobby = new Lobby(0, "", 0);
	}

	void connect() throws Exception
    {
    	clientEndPoint = new WebSocketClient(new URI("ws://localhost:8080/websocket/" + lobby.getLobbyID() + "/" + username));
        clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
                    public void handleMessage(String message) {
                    	messages.add(message);
                    	getLobby();
                    }
                });
        	
            	clientEndPoint.sendMessage(username + " has joined this lobby.");
//            String m = "[{\"action\": \"join\", \"message\":\"" + username + " has joined this lobby.\"}]";
//            clientEndPoint.sendMessage(m);
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
	        if (i < lobby.getNumPlayers())
	        	white.draw(batch, "player" + Integer.toString(i + 1), i * 200 + 250, 400);
	        else
	        	white.draw(batch, "Open", i * 200 + 250, 400);
        }
        
        for(int i = 0; i < messages.size() && i < 5; i++)
        {
        	white.draw(batch, messages.get(messages.size() - i - 1), 50, i * 30 + 50);
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
            	lobby.setNumPlayers(lobby.getNumPlayers() - 1);
            	game.setScreen(new Lobbies(game));
            }
        });
        stage.addActor(exitButton);
        
        playButton = new TextButton("Play", TextButtonStyle());
        playButton.setPosition(1100, 50);
        playButton.addListener(new ClickListener()
        {
        	@Override
            public void clicked(InputEvent event, float x, float y) {
        		clientEndPoint.sendMessage(username + " is ready.");
        	}
        });
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
    	leaveLobby();
    }
    
    public String leaveLobby()
    {
    	return APIDelete();
    }
    
    public void getLobby()
    {
    	String result = callAPIGet();
    	String delims = "[{}\":,]+";
    	String[] tokens = result.split(delims);
    	for(String s : tokens)
    		System.out.println(s);
    		
    	if(tokens[6].equals("false"))
    		lobby.setNumPlayers(Integer.parseInt(tokens[8]));
    	else
    		lobby.setNumPlayers(Integer.parseInt(tokens[10]));
    }
    
    private TextButton.TextButtonStyle TextButtonStyle() {
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

	public String callAPIGet() {
		String result = "";
		try {
			String url = "http://coms-309-tc-3.misc.iastate.edu:8080/lobbyInfo?lobbyID=" + lobby.getLobbyID();

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
			return result;
		}
		catch(Exception e)	{
			System.out.print(e);
		}
    	return "failure";
	}

	public String APIDelete() {
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
			return "success";
		}
		catch(Exception e)	{
			System.out.print(e);
		}
		return "failure";
	}

	public Lobby returnCurrentLobby() {
    	return lobby;
	}
    
}
