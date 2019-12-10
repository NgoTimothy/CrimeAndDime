package Endpoints;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Inventory Controller queries the Inventory table in the database for the front end to use.
 */
@RestController
public class InventoryController {

    /**
     * This method queries the database for the entire inventory table
     * @return Returns an ArrayList of all possible items a user can buy
     */
    @RequestMapping("/inventory")
    public ArrayList<Item> retrieveInventory() {
        try {
            //create array list of items
            ArrayList<Item> items = new ArrayList<Item>();
            //query to query the database
            String query = "select * from crime_and_dime.inventory";

            //connect to the database and execute query
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    //"jdbc:mysql://localhost:3306/dime_and_crime?allowPublicKeyRetrieval=true&useSSL=false","root","");
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3_CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query);
            Statement stmt = con.createStatement();
            ResultSet rs = prst.executeQuery();
            //loop through each line of result and add all items to item list
            while (rs.next()) {
                //create instance of individual item class
                Item tempItem = new Item();
                tempItem.setName(rs.getString("name"));
                tempItem.setPrice(rs.getDouble("price"));
                tempItem.setItemId(rs.getInt("itemid"));
                items.add(tempItem);
            }
            con.close();
            return items;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
