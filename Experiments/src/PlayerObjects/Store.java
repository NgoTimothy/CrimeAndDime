package PlayerObjects;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private int level;
    private String name;
    private static List<List<TileSpace.typeOfSpace>> storeMap;
    private List<ItemEx> backStock;

    public Store(String storeName) {
        name = storeName;
        level = 0;
        storeMap = new ArrayList<List<TileSpace.typeOfSpace>>();
    }

    public static void main(String args[]) {
        storeMap = new ArrayList<List<TileSpace.typeOfSpace>>();
        initStore();
        printStore();
    }

    public void upgradeStore() {
        level++;
        //Dynamically resize 2d arraylist
    }

    public static void initStore() {
        for (int i = 0; i < 5; i++) {
            List<TileSpace.typeOfSpace> col = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                col.add(TileSpace.typeOfSpace.EMPTY);
            }
            storeMap.add(col);
        }

    }

    public static void printStore() {
        for (int i = 0; i < storeMap.size(); i++) {
            for (int j = 0; j < storeMap.get(i).size(); j++) {
                if(storeMap.get(i).get(j).equals(TileSpace.typeOfSpace.EMPTY)) {
                    System.out.print("*");
                }
                if(j == (storeMap.get(i).size() - 1)) {
                    System.out.println();
                }
                else {
                    System.out.print(" ");
                }
            }
        }
    }

    public void setStoreSpace(int row, int col, String tileType) {

    }

    public void addItemToBackStock(ItemEx itemExToBeAdded) {
        if(itemExToBeAdded == null || itemExToBeAdded.getQuantity() <= 0) {
            return;
        }
        if(!backStock.contains(itemExToBeAdded)) {
            backStock.add(itemExToBeAdded);
        }
        else {
            backStock.get(backStock.indexOf(itemExToBeAdded)).addItemQuantity(itemExToBeAdded.getQuantity());
        }
    }



}
