package test;

import org.junit.*;
import org.mockito.Mock;
import utility.Lobby;
import utility.Player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LobbyTest {
    private Lobby mockLobby;
    private Lobby testLobby;
    private String json;

    @Before
    public void setup() {
        mockLobby = mock(Lobby.class);
        when(mockLobby.getLobbyID()).thenReturn(125);
        when(mockLobby.getLobbyName()).thenReturn("mockLobby");
        when(mockLobby.getNumPlayers()).thenReturn(1);
        testLobby = new Lobby(200, "testLobby", 2);
    }

    @Test
    public void mockedLobbyReturnsCorrectLobbyId() {
        assertEquals(125, mockLobby.getLobbyID());
    }

    @Test
    public void mockedLobbyReturnsCorrectNumberOfPlayers() { assertEquals(1, mockLobby.getNumPlayers()); }

    @Test
    public void mockedLobbyReturnsCorrectLobbyName() { assertEquals("mockLobby", mockLobby.getLobbyName()); }

    @Test
    public void changeLobbyNameIsCorrect() {
        assertEquals("testLobby", testLobby.getLobbyName());
        testLobby.setLobbyName("newName");
        assertEquals("newName", testLobby.getLobbyName());
    }

    @Test
    public void changeLobbyIdIsCorrect() {
        assertEquals(200, testLobby.getLobbyID());
        testLobby.setLobbyID(250);
        assertEquals(250, testLobby.getLobbyID());
    }

    @Test
    public void changeLobbySetPlayersIsCorrect() {
        assertEquals(2, testLobby.getNumPlayers());
        testLobby.setNumPlayers(1);
        assertEquals(1, testLobby.getNumPlayers());
    }
    
    @Test
    public void isLobbyReadyFalse() {
    	Player p1 = mock(Player.class);
    	when(p1.isReady()).thenReturn(false);
    	Player p2 = mock(Player.class);
    	when(p2.isReady()).thenReturn(true);
    	testLobby.setNumPlayers(0);
    	testLobby.addPlayer(p1);
    	testLobby.addPlayer(p2);
    	assertFalse(testLobby.isLobbyReady());
    }
    
    @Test
    public void isLobbyReadyTrue() {
    	testLobby = new Lobby(0, "lobby", 0);
    	Player p1 = mock(Player.class);
    	when(p1.isReady()).thenReturn(true);
    	Player p2 = mock(Player.class);
    	when(p2.isReady()).thenReturn(true);
    	testLobby.setNumPlayers(0);
    	testLobby.addPlayer(p1);
    	testLobby.addPlayer(p2);
    	assertTrue(testLobby.isLobbyReady());
    }
    
    @Test
    public void addPlayer() {
        Player p1 = mock(Player.class);
        when(p1.getUsername()).thenReturn("player1");
        testLobby.setNumPlayers(0);
        testLobby.addPlayer(p1);
        assertEquals(1, testLobby.getNumPlayers());
        assertEquals("player1", testLobby.getPlayers().get(0).getUsername());        
    }

}
