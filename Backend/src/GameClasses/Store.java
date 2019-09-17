package GameClasses;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<List<Tile>> storeMap;
    private String name;

    public Store(String initName) {
        name = initName;
        storeMap = new ArrayList<List<Tile>>();
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
}
