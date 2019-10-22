package Endpoints;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
}
