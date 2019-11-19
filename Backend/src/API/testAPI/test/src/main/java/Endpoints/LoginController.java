package Endpoints;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class LoginController {

    @RequestMapping("/register")
    public String register(@RequestParam(value = "username", defaultValue = "Player 1") String username ,@RequestParam(value = "password", defaultValue = "1234") String password) throws SQLException {
        Connection con = null;
        try {
            
            String query2 = "INSERT INTO login(username, password) VALUES(?, ?)";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
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

    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "Player 1") String username ,@RequestParam(value = "password", defaultValue = "1234") String password) throws SQLException {
        Connection con = null;
        try {

            String query2 = "SELECT * FROM login WHERE username = ? AND password = ?";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    // "jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","teamTC3","TC_3CrimeAndDime");
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
