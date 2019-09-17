package Inventory;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {

    @RequestMapping("/lobby")
    public String getAllLobbies() throws SQLException {
        Connection con = null;
        try {

            ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
            String query = "SELECT * FROM dime_and_crime.lobby";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","root","");
            PreparedStatement prst = con.prepareStatement(query);
            Statement stmt = con.createStatement();
            ResultSet rs = prst.executeQuery();
            while(rs.next()) {
                Lobby lobby = new Lobby();
                lobby.setLobbyId(rs.getInt("lobbyId"));
                lobby.setLobbyName(rs.getString("lobbyName"));
                lobby.setHasPassword(rs.getBoolean("hasPassword"));
                lobby.setLobbyPassword(rs.getString("lobbyPassword"));
                lobby.setNumberOfPlayers(rs.getInt("numberOfPlayers"));
                lobbies.add(lobby);
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(lobbies);
            return jsonString;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        finally {
            con.close();
        }
        return "Error";
    }
}
