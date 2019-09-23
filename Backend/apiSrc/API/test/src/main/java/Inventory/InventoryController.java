package Inventory;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @RequestMapping("/inventory")
    public String retrieveInventory(@RequestParam(value="storeID", defaultValue="1") String storeID) {

        try
        {
            //set up variable in while loop
            int i = 0;
            //create array list of items
            ArrayList<Inventory.Item> items = new ArrayList<Inventory.Item>();
            //query to query the database
            String query = "select * from crime_and_dime.inventory";

            //connect to the database and execute query
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://coms-309-tc-3.misc.iastate.edu:3306/crime_and_dime?allowPublicKeyRetrieval=true&useSSL=false", "teamTC3", "TC_3CrimeAndDime");
            PreparedStatement prst = con.prepareStatement(query);
            Statement stmt=con.createStatement();
            ResultSet rs = prst.executeQuery();
            //loop through each line of result and add all items to item list
            while(rs.next()) {
                //create instance of individual item class
                Inventory.Item item = new Inventory.Item();
                item.setItem(rs.getString("item"));
                item.setItemId(rs.getInt("itemid"));
                item.setPrice(rs.getInt("price"));
                items.add(item);
                i++;
            }
            con.close();
//            would return as a json array
//            JSONArray jsonArray = new JSONArray();
//            for (int i=0; i < inventories.size(); i++) {
//                jsonArray.put(inventories.get(i).getJSONObject());
//            }
            //return inventories as json string
            Gson gson = new Gson();
            String jsonString = gson.toJson(items);
            return jsonString;
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return "Error";
    }

    @RequestMapping("/name")
    public String testCall(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello, " + name;
    }

}
