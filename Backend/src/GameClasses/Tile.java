package GameClasses;

import GameExceptions.DirectionWithNoShelfException;
import GameExceptions.ShelfWithNoDirectionException;

public class Tile {
    public enum tileType {
        CUSTOMER, EMPTY, SHELF
    }

    public enum shelfDirection {
        NORTH, WEST, EAST, SOUTH, NONE
    }

    private tileType curTileType;
    private Item stockedItem;
    private shelfDirection curShelfDirection;

    public Tile() {
        curTileType = tileType.EMPTY;
        stockedItem = null;
        curShelfDirection = shelfDirection.NONE;
    }

    /**
     * Constructor to initialize tile that is not a shelf
     * @param initType
     * @throws ShelfWithNoDirectionException
     */
    public Tile(tileType initType) throws DirectionWithNoShelfException {
        if(initType.equals(tileType.SHELF)) {
            throw new DirectionWithNoShelfException("Cannot place a shelf without a direction");
        }
        curTileType = initType;
        curShelfDirection = shelfDirection.NONE;
        stockedItem = null;
    }

    /**
     * Constructor to initialize tile that is a shelf
     * @param initShelfDirection
     */
    public Tile(shelfDirection initShelfDirection) throws ShelfWithNoDirectionException {
        if(initShelfDirection.equals(shelfDirection.NONE)) {
            throw new ShelfWithNoDirectionException("Cannot place a shelf without a direction");
        }
        curTileType = tileType.SHELF;
        curShelfDirection = initShelfDirection;
        stockedItem = null;
    }

    public tileType getTileType() {
        return curTileType;
    }

    public Item getItem() {
        return stockedItem;
    }

    public shelfDirection getShelfDirection() {
        return curShelfDirection;
    }

    /**
     * This method sets any tile space to a shelf and sets the direction of the shelf
     * @param newShelfDirection
     */
    public void setShelfTile(shelfDirection newShelfDirection) {
        curTileType = tileType.SHELF;
        curShelfDirection = newShelfDirection;
    }

    /**
     * This method sets any tile to something besides a shelf
     * @param newTileType
     * @throws ShelfWithNoDirectionException
     */
    public void setNonShelfTile(tileType newTileType) throws ShelfWithNoDirectionException {
        if(newTileType.equals(tileType.SHELF)) {
            throw new ShelfWithNoDirectionException("Cannot place a shelf without a direction");
        }
        if(curTileType.equals(tileType.SHELF)) {
            curShelfDirection = shelfDirection.NONE;
        }
        curTileType = newTileType;
    }
}
