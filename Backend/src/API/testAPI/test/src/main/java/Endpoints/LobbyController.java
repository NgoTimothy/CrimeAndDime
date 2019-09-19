package Endpoints;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LobbyController {

    @RequestMapping("/lobbyList")
    public String getAllLobbies() throws SQLException {
        Connection con = null;
        try {

            ArrayList<Lobby> lobbies = new ArrayList<Lobby>();
            String query = "SELECT * FROM crime_and_dime.lobby";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    // "jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","teamTC3","TC_3CrimeAndDime");
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query);
            Statement stmt = con.createStatement();
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
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
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            con.close();
        }
        return "Error";
    }

    @RequestMapping("/individualLobby")
    public String getlobby(@RequestParam(value = "lobbyID", defaultValue = "15481") Integer lobbyID) throws SQLException {
        Connection con = null;
        try {

            ArrayList<String> players = new ArrayList<String>();
            String query = "SELECT * FROM crime_and_dime.lobby_group WHERE lobbyID = ?";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    // "jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","teamTC3","TC_3CrimeAndDime");
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query);
            prst.setInt(1, lobbyID);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                players.add(rs.getString("username"));
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(players);
            return jsonString;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return "Error";
    }

    @RequestMapping("/addToLobby")
    public String addUser(@RequestParam(value = "lobbyID", defaultValue = "15601") Integer lobbyID, @RequestParam(value = "username", defaultValue = "laknoll") String username) throws SQLException {
        Connection con = null;
        Connection con2 = null;
        try {

            ArrayList<String> players = new ArrayList<String>();
            String query = "UPDATE lobby SET numberOfPlayers = numberofPlayers + 1 WHERE lobbyID = ?";
            String query2 = "INSERT INTO lobby_group(username, lobbyID) VALUES(?, ?)";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    // "jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","teamTC3","TC_3CrimeAndDime");
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query2);
            prst.setString(1, username);
            prst.setInt(2, lobbyID);
            prst.executeUpdate();
            prst = con.prepareStatement(query);
            prst.setInt(1, lobbyID);
            prst.executeUpdate();

            return "Welcome to the lobby!";

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return "Error";
    }
}