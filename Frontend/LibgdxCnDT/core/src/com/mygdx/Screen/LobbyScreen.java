package com.mygdx.Screen;

import Services.LobbyScreenService;
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

import java.net.URI;
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
    private LobbyScreenService lobbyScreenService;
    private ArrayList<String> usernames;
    
    public LobbyScreen(CrimeAndDime newGame, Lobby newLobby)
    {
    	lobby = newLobby;
    	usernames = new ArrayList<>();
    	lobbyScreenService = new LobbyScreenService();
    	getLobby();
    	game = newGame;
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	username = game.getUsername();
    	messages = new ArrayList<String>();
    	try {
			connect();
            fillUsernames();
        } catch (Exception e) {
			e.printStackTrace();
		}
    }

    //For testing purposes only
	public LobbyScreen(LobbyScreenService newLobbyScreenService) {
		lobby = new Lobby(0, "", 0);
		lobbyScreenService = newLobbyScreenService;
	}

	void connect() throws Exception
    {
    	//clientEndPoint = new WebSocketClient(new URI("ws://coms-309-tc-3.misc.iastate.edu:8080/websocket/" + lobby.getLobbyID() + "/" + username), game);
        clientEndPoint = new WebSocketClient(new URI("ws://localhost:8080/websocket/" + lobby.getLobbyID() + "/" + username), game);
        clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
                    @Override
					public void handleMessage(String message) {
                        if(message.equals("updateLobby")) {
                            getLobby();
                            fillUsernames();
                            game.setUpdateLobby(false);
                            System.out.println("Fuck");
                        }
                        else {
                            messages.add(message);
                            getLobby();
                        }
                    }
                });
        clientEndPoint.sendMessage(username + " has joined this lobby.");
        clientEndPoint.sendMessage("updateLobby:" + lobby.getLobbyID());
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
	        if (i < lobby.getNumPlayers() && i < usernames.size()) {
				white.draw(batch, usernames.get(i), i * 200 + 250, 400);
				//white.draw(batch, "player" + Integer.toString(i + 1), i * 200 + 250, 400);
			}
	        else
	        	white.draw(batch, "Open", i * 200 + 250, 400);
        }
        
        for(int i = 0; i < messages.size() && i < 5; i++) {
        	white.draw(batch, messages.get(messages.size() - i - 1), 50, i * 30 + 50);
        }
        batch.end();
        //System.out.println(game.getUpdateLobby());
        if(game.getUpdateLobby()) {
            getLobby();
            fillUsernames();
            game.setUpdateLobby(false);
        }
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
                clientEndPoint.sendMessage("updateLobby:" + lobby.getLobbyID());
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
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {
    	
    }

    @Override
    public void dispose () {
        leaveLobby();
    }
    
    public String leaveLobby() {
    	return lobbyScreenService.APIDelete(username);
    }
    
    public void getLobby() {
    	String result = lobbyScreenService.callAPIGet(lobby.getLobbyID());
    	String delims = "[{}\":,]+";
    	String[] tokens = result.split(delims);
    	for(String s : tokens)
    		System.out.println(s);
    		
    	if(tokens[6].equals("false"))
    		lobby.setNumPlayers(Integer.parseInt(tokens[8]));
    	else
    		lobby.setNumPlayers(Integer.parseInt(tokens[10]));
    }

    public void fillUsernames() {
        usernames.clear();
        String result = lobbyScreenService.getUsernames(lobby.getLobbyID());
        result =  result.replace("[", "");
        result = result.replace("]", "");
        result = result.replace("\"", "");
        String[] tokens = result.split(",");
        System.out.println(result);
        for(int i = 0; i < tokens.length; i++)
            usernames.add(tokens[i]);
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

	public Lobby returnCurrentLobby() {
    	return lobby;
	}
    
}
