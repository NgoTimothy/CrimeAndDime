package GameClasses;

import GameExceptions.PlacingItemWithNoShelfException;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<List<Tile>> storeMap;
    private String name;
    private double balance;
    private Inventory storeInventory;

    public Store(String initName) {
        name = initName;
        storeMap = new ArrayList<List<Tile>>();
        balance = 0;
        storeInventory = new Inventory();
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

    public Tile getTile(int row, int col) {
        return storeMap.get(row).get(col);
    }

    public boolean removeItemFromShelf(int row, int col) {
        Tile adjustedTile = getTile(row, col);
        if(!adjustedTile.getTileType().equals(Tile.tileType.SHELF)) {
            return false;
        }
        storeInventory.addItem(adjustedTile.getItem());
        adjustedTile.removeItemFromShelf();
        return true;
    }

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

    public boolean purchaseItemToInventory(Item item) {
        if (item == null || item.getQuantity() <= 0) {
            return false;
        }
        Double totalWholesaleCost = item.getWholesaleCost().doubleValue() * item.getQuantity();
        if(totalWholesaleCost > balance) {
            return false;
        }
        else {
            storeInventory.addItem(item);
            balance -= totalWholesaleCost;
            return true;
        }
    }
}
