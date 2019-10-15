import com.mygdx.Screen.LobbyScreen;
import org.junit.Before;
import org.junit.Test;
import utility.Lobby;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LobbyScreenTest {
    private LobbyScreen mockScreen;
    private LobbyScreen realScreen;
    private String expectedJson;
    private Lobby testLobby;

    @Before
    public void setup() {
        realScreen = new LobbyScreen();
        mockScreen = spy(realScreen);
        //mockScreen = mock(LobbyScreen.class, CALLS_REAL_METHODS);
        expectedJson = "[{\"lobbyId\":15481,\"lobbyName\":\"Iowa State Lobby\",\"hasPassword\":false,\"numberOfPlayers\":1}]";
        when(mockScreen.callAPIGet()).thenReturn(expectedJson);
        testLobby = new Lobby(0, "", 0);
    }

    @Test
    public void testGetLobbyMethod() {
        mockScreen.getLobby();
        assertEquals(1, mockScreen.returnCurrentLobby().getNumPlayers());
    }
}
