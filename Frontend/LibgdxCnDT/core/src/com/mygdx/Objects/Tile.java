<<<<<<< HEAD:Frontend/LibgdxCnDT/core/src/com/mygdx/Objects/Tile.java
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
=======
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
>>>>>>> SpriteMovement-Hung:Frontend/LibgdxCnDT/core/src/com/mygdx/Objects/tempTile.java
