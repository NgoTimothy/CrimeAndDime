package com.mygdx.Screen;

import Services.LobbyScreenService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import utility.Player;
import utility.WebSocketClient;

import java.io.IOException;
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
    private String username;
    private LobbyScreenService lobbyScreenService;
    private boolean ready;

    public LobbyScreen(CrimeAndDime newGame, Lobby newLobby)
    {
    	game = newGame;
    	game.lobby = newLobby;
    	lobbyScreenService = new LobbyScreenService();
    	white = new BitmapFont(Gdx.files.internal("font/WhiteFNT.fnt"), false);
    	black = new BitmapFont(Gdx.files.internal("font/BlackFNT.fnt"),false);
    	batch = new SpriteBatch();
    	username = game.getUsername();
    	messages = new ArrayList<String>();
    	try {
			connect();
            ready = false;
            System.out.println(game.lobby.getNumPlayers());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //For testing purposes only
	public LobbyScreen(LobbyScreenService newLobbyScreenService) {
		game.lobby = new Lobby(0, "", 0);
		lobbyScreenService = newLobbyScreenService;
	}

    void connect() throws Exception
    {
        //clientEndPoint = new WebSocketClient(new URI("ws://coms-309-tc-3.misc.iastate.edu:8080/websocket/" + lobby.getLobbyID() + "/" + username), game);
        //Change to line above to use the socket on server
        game.clientEndPoint = new WebSocketClient(new URI("ws://localhost:8080/websocket/" + game.lobby.getLobbyID() + "/" + game.getUsername()), game);
        game.clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
                    @Override
					public void handleMessage(String message) {
                    	if (message.contains("has joined this lobby.") || message.contains("has left this lobby."))
                    	{
                    		fillUsers();
                    		messages.add(message);
                    		ready = false;
                    	}
                    	else if(message.contains("is not ready")) {
                            String[] tokens = message.split(":");
                            for(int i = 0; i < game.lobby.getPlayers().size(); i++) {
                                if(game.lobby.getPlayers().get(i).getUsername().equals(tokens[0]))
                                	game.lobby.getPlayers().get(i).setIsReady(false);
                            }
                        }
                        else if(message.contains("is ready")) {
                            String[] tokens = message.split(":");
                            for(int i = 0; i < game.lobby.getPlayers().size(); i++) {
                                if(game.lobby.getPlayers().get(i).getUsername().equals(tokens[0]))
                                	game.lobby.getPlayers().get(i).setIsReady(true);
                            }
                        }
                        else
                        	messages.add(message);
                    }
                });
        game.clientEndPoint.sendMessage(username + " has joined this lobby.");
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.setDebugAll(true);

        stage.draw();

        batch.begin();
        white.draw(batch, game.lobby.getLobbyName(), 500, 700);
        for(int i = 0; i < 4; i++)
        {
	        if (i < game.lobby.getNumPlayers()) {
	            BitmapFont font = new BitmapFont();
	            try {
                    if(game.lobby.getPlayers().get(i).getIsReady())
                        font.setColor(Color.GREEN);
                    else
                        font.setColor(Color.RED);
                } catch(Exception e) {
	                System.out.println(game.lobby.getPlayers().size());
                }
                font.getData().setScale(2);
	            try {
                    font.draw(batch, game.lobby.getPlayers().get(i).getUsername(), i * 200 + 250, 400);
                } catch (Exception e) {
                    white.draw(batch, "Open", i * 200 + 250, 400);
                }
			}
	        else
	        	white.draw(batch, "Open", i * 200 + 250, 400);
        }

        for(int i = 0; i < messages.size() && i < 5; i++) {
            white.draw(batch, messages.get(messages.size() - i - 1), 50, i * 30 + 50);
        }
        if(game.lobby.isLobbyReady())
            game.setScreen(new tileMapScreen(game));
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
        	    if(game.clientEndPoint != null) {
                    if(!ready) {
                        game.clientEndPoint.sendMessage(username + ":is ready.");
                        ready = true;
                    }
                    else {
                        game.clientEndPoint.sendMessage(username + ":is not ready.");
                        ready = false;
                    }
                }
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
    	dispose();
    }

    @Override
    public void dispose () {
        leaveLobby();
        try {
            game.clientEndPoint.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String leaveLobby() {
    	game.clientEndPoint.sendMessage(username + " has left this lobby.");
    	String s = lobbyScreenService.APIDelete(username);
    	if (game.lobby.getNumPlayers() <= 1)
    		lobbyScreenService.APIDeleteLobby(game.lobby.getLobbyID());
    	return s;
    }

    public void getLobby() {
        String result = lobbyScreenService.callAPIGet(game.lobby.getLobbyID());
        String delims = "[{}\":,]+";
        String[] tokens = result.split(delims);
        for(String s : tokens)
            System.out.println(s);

        if(tokens[6].equals("false"))
            game.lobby.setNumPlayers(Integer.parseInt(tokens[8]));
        else
            game.lobby.setNumPlayers(Integer.parseInt(tokens[10]));
    }

    
    public void fillUsers() {
    	game.lobby.setPlayers(new ArrayList<Player>());
        String result = lobbyScreenService.getUsernames(game.lobby.getLobbyID());
        result =  result.replace("[", "");
        result = result.replace("]", "");
        result = result.replace("\"", "");
        String[] tokens = result.split(",");
        System.out.println(result);
        for(int i = 0; i < tokens.length; i++) {
            Player player = new Player(tokens[i], false);
            game.lobby.addPlayer(player);
        }
        for(Player p : game.lobby.getPlayers())
        	System.out.println("user: " + p.getUsername());
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
    	return game.lobby;
	}

	public boolean readyToStart() {
        if(game.lobby.getNumPlayers() < 2)
            return false;
        for(int i = 0; i < game.lobby.getPlayers().size(); i++) {
            if(!game.lobby.getPlayers().get(i).getIsReady())
                return false;
        }
        return true;
    }

}
