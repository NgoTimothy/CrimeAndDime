import GameClasses.Item;
import GameClasses.Tile;
import GameExceptions.PlacingItemWithNoShelfException;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class WebSocketClientTest {
    private JSONArray testJsonArray;
    private ArrayList<Tile> fakeShelfArray;
    private String expectedString;

    @Before
    public void setup() {
        fakeShelfArray = new ArrayList<>();
        Tile tileFake1 = new Tile(Tile.shelfDirection.SOUTH);
        Tile tileFake2 = new Tile(Tile.shelfDirection.SOUTH);
        Item itemFake1 = new Item("apples", 1.02, 2.00, 10);
        Item itemFake2 = new Item("orange juice", 1.75, 3.00, 10);
        expectedString = "\"{\"name\":\"apples\",\"quantity\":10,\"wholesaleCost\":1.02,\"retailCost\":2.0}\"," +
                "\"{\"name\":\"orange juice\",\"quantity\":10,\"wholesaleCost\":1.75,\"retailCost\":3.0}\"]";
        try {
            tileFake1.setItemOnShelf(itemFake1, 10);
            tileFake2.setItemOnShelf(itemFake2, 10);
            fakeShelfArray.add(tileFake1);
            fakeShelfArray.add(tileFake2);
        } catch (PlacingItemWithNoShelfException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJSONArrayConvertsCorrectly() {
        JSONArray returnJSONArray = new JSONArray(fakeShelfArray);
        //returnJSONArray.toString();
        Item item = new Item();
        ArrayList<Item> itemArr = new ArrayList<>();
        String fakeStr = returnJSONArray.toString().replace("\\", "");
        fakeStr = fakeStr.replace("[", "");//We will do up to this point for the server
        System.out.println(fakeStr);
        assertEquals(expectedString, fakeStr);
        /*String[] tokens = fakeStr.split("}");
        for(int i = 0; i < tokens.length; i++) {
            if(tokens[i].length() > 5) { //Do something here
                if(i != 0) {
                    tokens[i] = tokens[i].substring(3);
                }
                tokens[i] = tokens[i].replace("\"{", "");
                tokens[i] = tokens[i].replace("{", "");
                String[] itemFields = tokens[i].split(",");
                for(int j = 0; j < itemFields.length; j++) {
                    if(itemFields[j].contains("name")) {
                        item = new Item();
                        item.setName(itemFields[j].substring(7).replace("\"", ""));
                        //System.out.println(item.getName());

                    }
                    else if(itemFields[j].contains("quantity")) {
                        item.setQuantity(Integer.parseInt(itemFields[j].substring(11)));
                        //System.out.println(item.getQuantity());
                    }
                    else if(itemFields[j].contains("wholesaleCost")) {
                        item.setWholesaleCost(Double.parseDouble(itemFields[j].substring(16)));
                        //System.out.println(item.getWholesaleCost());
                    }
                    else if(itemFields[j].contains("retailCost")) {
                        item.setRetailCost(Double.parseDouble(itemFields[j].substring(13)));
                        //System.out.println(item.getRetailCost());
                        itemArr.add(item);
                    }

                    //System.out.println(itemFields[j]);
                }

                //System.out.println(tokens[i]);
            }
        }
        printArr(itemArr);*/
    }

    private void printArr(ArrayList<Item> printableArr) {
        System.out.println(printableArr.size());
        for(int i = 0; i < printableArr.size(); i++) {
            System.out.println(printableArr.get(i).getName() + " " + printableArr.get(i).getQuantity() + " " +
                    printableArr.get(i).getWholesaleCost() + " " + printableArr.get(i).getRetailCost());
        }
    }
}
