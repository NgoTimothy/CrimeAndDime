package GameClasses;

import GameExceptions.CustomerMovingIntoShelfException;
import GameExceptions.PlacingItemWithNoShelfException;
import GameExceptions.ShelfWithNoDirectionException;

/**
 * Tile class is used to represent one space in the store
 */
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
    private static final int shelfMax = 10;

    public Tile() {
        curTileType = tileType.EMPTY;
        stockedItem = null;
        curShelfDirection = shelfDirection.NONE;
    }

    /**
     * Constructor to initialize tile that is not a shelf
     * @param initType
     */
    public Tile(tileType initType) {
        curTileType = initType;
        if(initType.equals(tileType.SHELF)) {
            curTileType = tileType.EMPTY;
        }
        curShelfDirection = shelfDirection.NONE;
        stockedItem = null;
    }

    /**
     * Constructor to initialize tile that is a shelf
     * @param initShelfDirection
     */
    public Tile(shelfDirection initShelfDirection) {
        curTileType = tileType.SHELF;
        curShelfDirection = initShelfDirection;
        if(initShelfDirection.equals(shelfDirection.NONE)) {
            curShelfDirection = shelfDirection.NORTH;
        }
        stockedItem = null;
    }

    /**
     * Returns the type of tile
     * @return
     */
    public tileType getTileType() {
        return curTileType;
    }

    /**
     * Returns the item currently stored
     * or null if there is no item
     * @return
     */
    public Item getItem() {
        return stockedItem;
    }

    /**
     * Gets and returns direction shelf is pointing towards
     * @return
     */
    public shelfDirection getShelfDirection() {
        return curShelfDirection;
    }

    /**
     * This method sets any tile space to a shelf and sets the direction of the shelf
     * @param newShelfDirection
     */
    public void setShelfTile(shelfDirection newShelfDirection) throws ShelfWithNoDirectionException {
        if(newShelfDirection.equals(shelfDirection.NONE)) {
            throw new ShelfWithNoDirectionException("Cannot place shelf without a direction");
        }
        curTileType = tileType.SHELF;
        curShelfDirection = newShelfDirection;
    }

    /**
     * This method sets any tile to something besides a shelf
     * @param newTileType
     * @throws ShelfWithNoDirectionException
     */
    public void setNonShelfTile(tileType newTileType) throws ShelfWithNoDirectionException, CustomerMovingIntoShelfException {
        if(newTileType.equals(tileType.SHELF)) {
            throw new ShelfWithNoDirectionException("Cannot place a shelf without a direction");
        }
        if(curTileType.equals(tileType.SHELF) && newTileType.equals(tileType.CUSTOMER)) {
            throw new CustomerMovingIntoShelfException("Customer cannot be placed in a existing shelf tile");
        }
        curShelfDirection = shelfDirection.NONE;
        curTileType = newTileType;
    }

    /**
     * Places item onto shelf and by number
     * Front end guarantees that stock number is not larger than passed item quantity
     * @param passedItem
     * @throws PlacingItemWithNoShelfException
     */
    public void setItemOnShelf(Item passedItem, int stockNum) throws PlacingItemWithNoShelfException {
        if(!curTileType.equals(tileType.SHELF)) {
            throw new PlacingItemWithNoShelfException("Cannot place item on tile that is not a shelf");
        }
        if(stockedItem != null && (stockedItem.compareTo(passedItem) == 0)) {
            if((stockedItem.getQuantity() + stockNum) > shelfMax)
                addToFirstItemSubToSecondItem(stockedItem, passedItem, (shelfMax - stockedItem.getQuantity()));
            else
                addToFirstItemSubToSecondItem(stockedItem, passedItem, stockNum);
        }
        else {
            stockedItem = passedItem.copy();
            stockedItem.clearQuantity();
            if(stockNum > shelfMax)
                addToFirstItemSubToSecondItem(stockedItem, passedItem, shelfMax);//I am passing the same fucking item
            else
                addToFirstItemSubToSecondItem(stockedItem, passedItem, stockNum);
        }
    }

    /**
     * Removes item from shelf
     */
    public void removeItemFromShelf() {
        stockedItem = null;
    }

    /**
     * Helper method to add quantity to first item and subtract quantity from second item
     * @param increaseItem
     * @param decreaseItem
     * @param quantity
     */
    public void addToFirstItemSubToSecondItem(Item increaseItem, Item decreaseItem, int quantity) {
        increaseItem.addQuantity(quantity);
        decreaseItem.subtractQuantity(quantity);
    }

    /**
     * Overrides toString method to print out the item or else nothing prints out
     * @return
     */
    @Override
    public String toString() {
        if(stockedItem == null) {
            return "";
        }
        return stockedItem.toString();
    }
}
