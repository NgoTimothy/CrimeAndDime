package TestClasses;

import GameClasses.Tile;
import org.junit.*;

import static org.junit.Assert.*;

public class TileTest {
    private Tile testTile;

    @Test
    public void testNewTileWithNoParameterConstructorHasDefaultValues() {
        testTile = new Tile();
        assertEquals(Tile.tileType.EMPTY, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        
    }

}
