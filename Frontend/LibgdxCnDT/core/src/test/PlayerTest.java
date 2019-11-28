package test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.mygdx.Screen.Lobbies;

import Services.LobbyService;
import utility.Lobby;
import utility.Player;

public class PlayerTest {
	private Player player;
	private Player mockPlayer;
	
    @Before
    public void setup() {
        player = new Player("username1");
        mockPlayer = mock(Player.class);
        when(mockPlayer.getUsername()).thenReturn("mockUsername");
        when(mockPlayer.isReady()).thenReturn(true);
    }

    @Test
    public void setUsernameTest() {
    	player.setUsername("aaa");
    	assertEquals("aaa", player.getUsername());
    }
    
    @Test
    public void readyPlayerTest() {
    	assertFalse(player.isReady());
    	player.readyPlayer();
    	assertTrue(player.isReady());
    }
}
