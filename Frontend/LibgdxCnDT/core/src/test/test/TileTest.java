package test.test;

import GameClasses.Item;
import GameClasses.Tile;
import GameExceptions.CustomerMovingIntoShelfException;
import GameExceptions.PlacingItemWithNoShelfException;
import GameExceptions.ShelfWithNoDirectionException;
import org.junit.*;

import static org.junit.Assert.*;

public class TileTest {
    private Tile testTile;
    private Item testItem;

    @Before
    public void init() {
        testTile = new Tile();
        testItem = new Item("Diapers");
        testItem.addQuantity(20);
    }

    @Test
    public void testNewTileWithNoParameterConstructor() {
        assertEquals(Tile.tileType.EMPTY, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
    }

    @Test
    public void testNewTileWithTypeParameterConstructor() {
        testTile = new Tile(Tile.tileType.EMPTY);
        assertEquals(Tile.tileType.EMPTY, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
        testTile = new Tile(Tile.tileType.CUSTOMER);
        assertEquals(Tile.tileType.CUSTOMER, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
    }

    @Test
    public void testNewTileWithDirectionParameterConstructor() {
        testTile = new Tile(Tile.shelfDirection.NORTH);
        assertEquals(Tile.tileType.SHELF, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NORTH, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
    }

    @Test
    public void testNewTileTypeParameterConstructorWithShelfAsNewType() {
        testTile = new Tile(Tile.tileType.SHELF);
        assertEquals(Tile.tileType.EMPTY, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
    }

    @Test
    public void testNewTileDirectionParameterConstructorWithNoneDirection() {
        testTile = new Tile(Tile.shelfDirection.NONE);
        assertEquals(Tile.tileType.SHELF, testTile.getTileType());
        assertEquals(Tile.shelfDirection.NORTH, testTile.getShelfDirection());
        assertEquals(null, testTile.getItem());
    }

    @Test
    public void testValidSetShelfTile() throws ShelfWithNoDirectionException {
        testTile.setShelfTile(Tile.shelfDirection.NORTH);
        assertEquals(Tile.tileType.SHELF, testTile.getTileType());
        assertEquals(null, testTile.getItem());
        assertEquals(Tile.shelfDirection.NORTH, testTile.getShelfDirection());
    }

    @Test(expected = ShelfWithNoDirectionException.class)
    public void testInvalidSetShelfTile() throws ShelfWithNoDirectionException {
        testTile.setShelfTile(Tile.shelfDirection.NONE);
    }

    @Test
    public void testValidSetNonShelfTile() throws ShelfWithNoDirectionException, CustomerMovingIntoShelfException {
        testTile.setNonShelfTile(Tile.tileType.CUSTOMER);
        assertEquals(Tile.shelfDirection.NONE, testTile.getShelfDirection());
        assertEquals(Tile.tileType.CUSTOMER, testTile.getTileType());
        assertEquals(null, testTile.getItem());
    }

    @Test(expected = ShelfWithNoDirectionException.class)
    public void testInvalidSetNonShelfTile() throws ShelfWithNoDirectionException, CustomerMovingIntoShelfException {
        testTile.setNonShelfTile(Tile.tileType.SHELF);
    }

    @Test
    public void testSetItemOnShelf() throws PlacingItemWithNoShelfException {
        testTile = new Tile(Tile.shelfDirection.SOUTH);
        testTile.setItemOnShelf(testItem, testItem.getQuantity());
        assertEquals(10, testTile.getItem().getQuantity());
        assertEquals("Diapers", testTile.getItem().getName());
    }

    @Test(expected = PlacingItemWithNoShelfException.class)
    public void testInvalidSetItemOnShelf() throws PlacingItemWithNoShelfException {
        testTile.setItemOnShelf(testItem, testItem.getQuantity());
    }

    @Test
    public void testRemoveItemFromShelf() throws PlacingItemWithNoShelfException {
        testTile = new Tile(Tile.shelfDirection.SOUTH);
        testTile.setItemOnShelf(testItem, testItem.getQuantity());
        testTile.removeItemFromShelf();
        assertEquals(null, testTile.getItem());
    }


}
