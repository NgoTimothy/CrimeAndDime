package Endpoints;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import Endpoints.LobbyController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LobbyControllerTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement prst;
    @Mock
    private ResultSet rs;
    @Mock
    private Lobby lobby;
    private String json;

    @Before
    public void setUp() throws Exception {

        //Tests if connection is set up properly
        when(connection.prepareStatement(any(String.class))).thenReturn(prst);
        //tests to see if we get a full set of results
        when(rs.first()).thenReturn(true);
        //tests if our results we added correctly
        when(prst.executeQuery()).thenReturn(rs);

        //This test works for all API methods. You could also check the fields if they match but that was so redundant I decided to check if the connection was proper and the results were okay.
    }
    @Test
    public void lobbyTest() throws Exception {
        String result = "fail";
        json = "[\"aswenson\",\"laknoll\",\"tngo\"]";
        Lobby lobby = mock(Lobby.class);
        String URL = "http://coms-309-tc-3.misc.iastate.edu:8080/individualLobby";
        java.net.URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        String USER_AGENT = "Mozzilla/5.0";
        con.setRequestProperty("User_agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        result = response.toString();

        assertEquals(result, json);

    }

    @Test
    public void lobbyInfoTest() throws Exception {
        String result = "fail";
        json = "[{\"lobbyId\":15481,\"lobbyName\":\"Iowa State Lobby\",\"hasPassword\":false,\"numberOfPlayers\":2}]";
        Lobby lobby = mock(Lobby.class);
        Integer lobbyID = 15481;
        when(lobby.getHasPassword()).thenReturn(false);
        when(lobby.getLobbyId()).thenReturn(15481);
        when(lobby.getLobbyName()).thenReturn("Iowa State Lobby");
        when(lobby.getLobbyPassword()).thenReturn("");
        when(lobby.getNumberOfPlayers()).thenReturn(1);
        String expected = lobby.toString();
        String URL = "http://coms-309-tc-3.misc.iastate.edu:8080/lobbyInfo";
        java.net.URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        String USER_AGENT = "Mozzilla/5.0";
        con.setRequestProperty("User_agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        result = response.toString();

        assertEquals(result, json);
    }
}

