package PlayerObjects;

public class TileSpace {
    //Maybe I should inherit Item and only have three enums here
    enum typeOfSpace {
        CUSTOMER, EMPTY, SHELF
    }

    enum direction {
        NORTH, WEST, EAST, SOUTH, NONE
    }

    private typeOfSpace type;
    private ItemEx stockedItemEx;
    private direction shelfDirection;

    public TileSpace(typeOfSpace initType, direction initDirection) {
        type = initType;
        if(type.equals(typeOfSpace.SHELF)) {
            shelfDirection = initDirection;
        }
    }

    public void setSpace(typeOfSpace newType) {
        type = newType;
    }

    public String getType() {
        return type.toString();
    }

    public Boolean setItem(ItemEx newItemEx) {
        if  (type.equals(typeOfSpace.SHELF)) {
            stockedItemEx = newItemEx;
            return true;
        }
        return false;
    }

    public ItemEx getItem() {
        if  (type.equals(typeOfSpace.SHELF)) {
            return stockedItemEx;
        }
        return null;
    }

    public direction getDirection() {
        if  (type == null || type != typeOfSpace.SHELF) {
            return direction.NONE;
        }
        return shelfDirection;
    }


}
