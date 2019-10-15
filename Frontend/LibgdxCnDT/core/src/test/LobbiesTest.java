import com.mygdx.Screen.Lobbies;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LobbiesTest {
    private Lobbies mockLobbies;
    private Lobbies testLobbies;
    private String expectedJson;

    @Before
    public void setup() {
        testLobbies = new Lobbies();
        mockLobbies = spy(testLobbies);
        expectedJson = "[{\"lobbyId\":15481,\"lobbyName\":\"Iowa State Lobby\",\"hasPassword\":false,\"numberOfPlayers\":1}]";
        when(mockLobbies.APIGetAllLobbies()).thenReturn(expectedJson);
    }

    @Test
    public void getAllLobbiesAddsToArrayList() {
        assertEquals(0, mockLobbies.getListOfLobbies().size());
        mockLobbies.getLobbies();
        assertEquals(1, mockLobbies.getListOfLobbies().size());
    }

    @Test
    public void getAllLobbiesAddsCorrectLobbyName() {
        mockLobbies.getLobbies();
        assertEquals("Iowa State Lobby", mockLobbies.getListOfLobbies().get(0).getLobbyName());
    }

    @Test
    public void getAllLobbiesAddsCorrectNumberOfPlayersInLobby() {
        mockLobbies.getLobbies();
        assertEquals(1, mockLobbies.getListOfLobbies().get(0).getNumPlayers());
    }

    @Test
    public void getAllAddsCorrectLobbyId() {
        mockLobbies.getLobbies();
        assertEquals(15481, mockLobbies.getListOfLobbies().get(0).getLobbyID());
    }
}
