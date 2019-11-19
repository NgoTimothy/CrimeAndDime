package com.mygdx.Objects;

public class Tile {
    public enum tileType {
        CUSTOMER, EMPTY, SHELF
    }

    public enum shelfDirection {
        NORTH, WEST, EAST, SOUTH, NONE
    }

    private tileType curTileType;
    private shelfDirection curShelfDirection;
    private static final int shelfMax = 10;

    public Tile() {
        curTileType = tileType.SHELF;
        curShelfDirection = shelfDirection.NONE;
    }
}