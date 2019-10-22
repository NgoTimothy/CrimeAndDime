package test.test;

import Services.LobbyScreenService;
import com.mygdx.Screen.LobbyScreen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utility.Lobby;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LobbyScreenTest {
    private LobbyScreen mockScreen;
    private String expectedJson;
    private LobbyScreenService mockLobbyScreenService;

    @Before
    public void setup() {
        expectedJson = "[{\"lobbyId\":15481,\"lobbyName\":\"Iowa State Lobby\",\"hasPassword\":false,\"numberOfPlayers\":1}]";
        mockLobbyScreenService = mock(LobbyScreenService.class);
        when(mockLobbyScreenService.callAPIGet(anyInt())).thenReturn(expectedJson);
        when(mockLobbyScreenService.APIDelete(anyInt())).thenReturn("success");
        mockScreen = new LobbyScreen(mockLobbyScreenService);
    }

    @Test
    public void testGetLobbyMethodChangesNumberOfPlayers() {
        mockScreen.getLobby();
        assertEquals(1, mockScreen.returnCurrentLobby().getNumPlayers());
    }

    @Test
    public void testLeaveLobbyReturnsCorrectMessage() {
        String result = mockScreen.leaveLobby();
        assertEquals("success", result);
    }
}
