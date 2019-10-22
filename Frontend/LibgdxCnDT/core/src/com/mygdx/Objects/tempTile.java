package com.mygdx.Objects;

public class tempTile {
    public enum tileType {
        CUSTOMER, EMPTY, SHELF
    }

    public enum shelfDirection {
        NORTH, WEST, EAST, SOUTH, NONE
    }

    private tileType curTileType;
    private shelfDirection curShelfDirection;
    private static final int shelfMax = 10;

    public tempTile() {
        curTileType = tileType.SHELF;
        curShelfDirection = shelfDirection.NONE;
    }

    public String toString(){
        return "Shelf";
    }
}
