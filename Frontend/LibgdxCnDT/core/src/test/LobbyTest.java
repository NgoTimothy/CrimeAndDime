import com.google.gson.GsonBuilder;
import org.junit.*;
import org.mockito.Mock;
import utility.Lobby;
import com.google.gson.Gson;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LobbyTest {
    private Lobby mockLobby;
    private String json;

    @Before
    public void setup() {
        mockLobby = mock(Lobby.class);
        when(mockLobby.getLobbyID()).thenReturn(125);
        when(mockLobby.getLobbyName()).thenReturn("mockLobby");
        when(mockLobby.getNumPlayers()).thenReturn(1);
    }

    @Test
    public void testMockedLobby() {
        assertEquals(125, mockLobby.getLobbyID());
    }

}
