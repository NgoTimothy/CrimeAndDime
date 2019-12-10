package Endpoints;

import com.mysql.jdbc.Connection;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

/**
 * Login controller handles all API call and queries to the login table.
 *
 */
@RestController
public class LoginController {

    /**
     *
     * @param username
     * @param password
     * @return This method takes in a username and password and registers a new user to our game.
     * It then returns "accepted" or "rejected" depending on the information entered by the user.
     * @throws SQLException
     */
    @RequestMapping("/register")
    public String register(@RequestParam(value = "username", defaultValue = "Player 1") String username ,@RequestParam(value = "password", defaultValue = "1234") String password) throws SQLException {
        Connection con = null;
        try {
            
            String query2 = "INSERT INTO login(username, password) VALUES(?, ?)";
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query2);
            prst.setString(1, username);
            prst.setString(2, password);
            prst.executeUpdate();

            return "Accepted";

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return "Rejected";
    }

    /**
     *
     * @param username
     * @param password
     * @return Takes in a username and password and checks to see if the user exists. If they do it logs them in and returns "Accepted".
     * If they don't it rejects them and returns "Rejected".
     * @throws SQLException
     */
    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "Player 1") String username ,@RequestParam(value = "password", defaultValue = "1234") String password) throws SQLException {
        Connection con = null;
        try {
            String query2 = "SELECT * FROM login WHERE username = ? AND password = ?";
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query2);
            prst.setString(1, username);
            prst.setString(2, password);
            ResultSet resultSet = prst.executeQuery();
            if(resultSet.next() == false){
                return "Rejected";
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return "Accepted";
    }

}
