package Endpoints;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;

/**
 * StoreInfo controller handles all API call and queries to the StoreInfo table.
 */
@RestController
public class StoreInfoController {

    /**
     * Queries the storeInfo table for one store based on storeName
     * @param storeName
     * @return Returns a storeInfo class filled with that stores information
     * @throws SQLException
     */
    @RequestMapping("/storeInfo")
    public StoreInfo getStoreInfo(@RequestParam(value = "storeName", defaultValue = "walmart") String storeName) throws SQLException {
        Connection con = null;
        try {

            StoreInfo storeInfo = new StoreInfo();
            String query = "SELECT * FROM crime_and_dime.store_info WHERE storeName = ?";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query);
            prst.setString(1, storeName);
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                storeInfo.setStoreName(rs.getString("storeName"));
                storeInfo.setCash(rs.getDouble("cash"));
                storeInfo.setMarket_score(rs.getInt("market_score"));
                storeInfo.setNextDay(rs.getBoolean("nextDay"));
            }

            return storeInfo;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            con.close();
        }
        return null;
    }
}
