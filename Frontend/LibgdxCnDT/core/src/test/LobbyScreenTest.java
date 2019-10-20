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
    private LobbyScreen realScreen;
    private String expectedJson;
    private Lobby testLobby;
    private LobbyScreenService mockLobbyScreenService;

    @Before
    public void setup() {
        mockLobbyScreenService = mock(LobbyScreenService.class);
        //realScreen = new LobbyScreen();
        //mockScreen = spy(realScreen);
        mockScreen = new LobbyScreen(mockLobbyScreenService);
        //mockScreen = mock(LobbyScreen.class, CALLS_REAL_METHODS);
        expectedJson = "[{\"lobbyId\":15481,\"lobbyName\":\"Iowa State Lobby\",\"hasPassword\":false,\"numberOfPlayers\":1}]";
        when(mockScreen.callAPIGet()).thenReturn(expectedJson);
        when(mockScreen.APIDelete()).thenReturn("success");
        testLobby = new Lobby(0, "", 0);
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
