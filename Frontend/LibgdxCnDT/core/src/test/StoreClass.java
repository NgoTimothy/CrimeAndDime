import GameClasses.Store;
import GameClasses.Tile;
import org.junit.*;

import static org.junit.Assert.*;

public class StoreClass {
    private Store testStore;

    @Before
    public void init() { testStore = new Store("Tim's Store"); }

    @Test
    public void testChangeTileInStore() {
        Tile testTile = testStore.getTile(0, 2);
        assertEquals(Tile.tileType.SHELF, testTile.getTileType());
    }
}
