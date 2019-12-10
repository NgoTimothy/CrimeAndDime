package GameClasses;

import GameExceptions.PlacingItemWithNoShelfException;

import java.util.ArrayList;
import java.util.List;

/**
 * Store class is the map for the main game screen and keeps track of
 * all the individual tiles in the game
 */
public class Store {
    private List<List<Tile>> storeMap;
    private String name;
    private double balance;
    public Inventory storeInventory;
    public int marketScore;
    public int crime;

    public Store(String initName) {
        name = initName;
        storeMap = new ArrayList<List<Tile>>();
        balance = 9999.99;
        storeInventory = new Inventory();
        marketScore = 0;
        crime =0;
        initStore();
    }

    public Store(String initName, double balance/*, int marketScore, int crime*/) {
        name = initName;
        storeMap = new ArrayList<List<Tile>>();
        this.balance = balance;
        storeInventory = new Inventory();
        /*this.marketScore = marketScore;
        this.crime = crime;*/
        initStore();
    }

    public static void main(String[] args) {
        Store newStore = new Store("Tim's Store");
        newStore.printStore();
    }

    public void initStore() {
        for(int i = 0; i < 11; i++) {
            List<Tile> rowOfTile =  new ArrayList<Tile>();
            for(int j = 0; j < 10; j++) {
                Tile newTile;
                if(i == 0) {
                    newTile = new Tile(Tile.shelfDirection.SOUTH);
                    rowOfTile.add(newTile);
                }
                else if(i % 2 == 0 && (j <= 3 || j >= 6) && i != 10) {
                    newTile = new Tile(Tile.shelfDirection.SOUTH);
                    rowOfTile.add(newTile);
                }
                else {
                    newTile = new Tile();
                    rowOfTile.add(newTile);
                }
            }
            storeMap.add(rowOfTile);
        }
    }

    public void printStore() {
        for (int i = 0; i < storeMap.size(); i++) {
            for (int j = 0; j < storeMap.get(i).size(); j++) {
                if(getTile(i, j).getTileType().equals(Tile.tileType.EMPTY)) {
                    System.out.print("*");
                }
                else if(getTile(i, j).getTileType().equals(Tile.tileType.SHELF)) {
                    System.out.print("#");
                }
                if(j != (storeMap.get(i).size() - 1)) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Returns the tile of a given cell in the list of lists
     * @param row
     * @param col
     * @return
     */
    public Tile getTile(int row, int col) {
        return storeMap.get(row).get(col);
    }

    /**
     * Removes an item from the shelf based off position in map
     * @param row
     * @param col
     * @return
     */
    public boolean removeItemFromShelf(int row, int col) {
        Tile adjustedTile = getTile(row, col);
        if(!adjustedTile.getTileType().equals(Tile.tileType.SHELF)) {
            return false;
        }
        storeInventory.addItem(adjustedTile.getItem());
        adjustedTile.removeItemFromShelf();
        return true;
    }

    /**
     * Places item on the shelf based on cell parameter passed in
     * @param row
     * @param col
     * @param quantity
     * @param itemToBePlacedOnShelf
     * @return
     */
    public boolean placeItemOnShelf(int row, int col, int quantity, String itemToBePlacedOnShelf) {
        Tile adjustedTile = getTile(row, col);
        if(!adjustedTile.getTileType().equals(Tile.tileType.SHELF)) {
            return false;
        }
        try {
            Item itemToBePlaced = storeInventory.getItem(itemToBePlacedOnShelf);
            adjustedTile.setItemOnShelf(itemToBePlaced, quantity);
        } catch (PlacingItemWithNoShelfException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Allows customers to buy an item from the shelf
     * @param item
     * @return
     */
    public boolean purchaseItemToInventory(Item item) {
        if (item == null || item.getQuantity() <= 0) {
            return false;
        }
        Double totalWholesaleCost = item.getWholesaleCost() * item.getQuantity();
        if(totalWholesaleCost > balance) {
            return false;
        }
        else {
            storeInventory.addItem(item);
            balance -= totalWholesaleCost;
            return true;
        }
    }

    /**
     * @return the actual inventory object of the store
     */
    public Inventory getInventory() {
        return storeInventory;
    }

    /**
     * @return the arraylist of items in the store
     */
    public ArrayList<Item> getListOfInventoryItems() {
        return storeInventory.getInventory();
    }

    /**
     * Returns the current total of money that a store has
     * @return
     */
    public double getBalance()
    {
    	return balance;
    }

    /**
     * Will add amountTobeAdded to the balance of the store and makes it so the balance is always two decimals
     * @param amountToBeAdded
     */
    public void addBalance(double amountToBeAdded) {
        balance += amountToBeAdded;
        balance = Math.round(balance * 100.0) / 100.0;
    }
    public void positiveMarketingOne(){
        if(balance > 100){
            balance = balance - 100;
            marketScore = marketScore + 10;
        }

    }
    public void positiveMarketingTwo(){
        if(balance > 500){
            balance = balance - 500;
            marketScore = marketScore + 50;
        }
    }

    public void positiveMarketingThree(){
        if(balance > 1000){
            balance = balance - 1000;
            marketScore = marketScore + 100;
        }
    }

    public void negativeMarketingOne(Store store){
        if(balance > 100){
            balance = balance - 100;
            store.setMarketScore(store.getMarketScore() - 10);
        }

    }

    public void negativeMarketingTwo(Store store){
        if(balance > 500){
            balance = balance - 500;
            store.setMarketScore(store.getMarketScore() - 50);
        }
    }

    public void negativeMarketingThree(Store store){
        if(balance > 1000){
            balance = balance - 1000;
            store.setMarketScore(store.getMarketScore() - 100);
        }
    }

    public void crimeOne(Store store){
        if(balance > 10){
            balance = balance - 10;
            crime = crime + 10;
            store.setMarketScore(store.getMarketScore() - 10);
        }
    }
    public void crimeTwo(Store store){
        if(balance > 50){
            balance = balance - 50;
            crime = crime + 20;
            store.setMarketScore(store.getMarketScore() - 50);
        }
    }
    public void crimeThree(Store store){
        if(balance > 100){
            balance = balance - 100;
            crime = crime + 30;
            store.setMarketScore(store.getMarketScore() - 100);
        }
    }

    public int getMarketScore() {
        return marketScore;
    }

    public void setMarketScore(int marketScore) {
        this.marketScore = marketScore;
    }

    public int getCrime() {
        return crime;
    }

    public void setCrime(int crime) {
        this.crime = crime;
    }

    public void setStoreInventory()
    {
        storeInventory = new Inventory();
    }
}
