import GameClasses.Item;
import GameClasses.Tile;
import GameExceptions.PlacingItemWithNoShelfException;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class webSocketClientTest {
    private JSONArray testJsonArray;
    private ArrayList<Tile> fakeShelfArray;

    @Before
    public void setup() {
        fakeShelfArray = new ArrayList<>();
        Tile tileFake1 = new Tile(Tile.shelfDirection.SOUTH);
        Tile tileFake2 = new Tile(Tile.shelfDirection.SOUTH);
        Item itemFake1 = new Item("apples", 1.02, 2.00, 10);
        Item itemFake2 = new Item("orange juice", 1.75, 3.00, 10);
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
    public void testJSONArray() {
        JSONArray returnJSONArray = new JSONArray(fakeShelfArray);
        //returnJSONArray.toString();
        System.out.println(returnJSONArray.toString().replace("\\", ""));
    }
}