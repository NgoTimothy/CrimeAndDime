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
    }

    private void printArr(ArrayList<Item> printableArr) {
        System.out.println(printableArr.size());
        for(int i = 0; i < printableArr.size(); i++) {
            System.out.println(printableArr.get(i).getName() + " " + printableArr.get(i).getQuantity() + " " +
                    printableArr.get(i).getWholesaleCost() + " " + printableArr.get(i).getRetailCost());
        }
    }
}
