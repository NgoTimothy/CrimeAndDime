package Endpoints;

/**
 * The Item class contains item name. price, an item ID, and the quantity of the item.
 */
public class Item
{
    private String name;
    private double price;
    private int itemId;
    private int quantity;

    /**
     * Constructs an empty item
     */
    public Item() {
        name = "";
        price = 0;
        quantity = 0;
    }

    /**
     * Contructs an item using String name, Int quantity, and Double price.
     * @param name
     * @param quantity
     * @param price
     */
    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     *
     * @return Returns name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Returns price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return Returns ItemID
     */
    public int getItemId() { return itemId; }

    /**
     *
     * @return Returns Quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Sets the name variable to string name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price variable to double price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the itemID variable to int itemId
     * @param itemId
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Sets the quantity variable to int quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {this.quantity = quantity; }

    public Item(String initName) {
        name = initName;
        price = 0.0;
    }

    public Item(Item originalItem) {
        name = originalItem.getName();
        price = originalItem.getPrice();
        quantity = originalItem.getQuantity();
    }

    public void addQuantity(int addedItems) {
        if(addedItems < 0) {
            return;
        }
        quantity += addedItems;
    }

    public void subtractQuantity(int subtractedItems) {
        if(subtractedItems > quantity) {
            return;
        }
        quantity -= subtractedItems;
    }

    public void clearQuantity() {
        quantity = 0;
    }

    public Item copy() {
        return new Item(this);
    }

    /**
     * If the original object is larger on the alphabet scale then it will return num > 0
     * If the same object based on name return 0
     * If smaller return num < 0
     * @param o
     * @return
     */
    public int compareTo(Item o) {
        String originalString = this.getName();
        String compareString = o.getName();
        int originalStringLength = originalString.length();
        int compareStringLength = compareString.length();
        int smallestLengthString = Math.min(originalStringLength, compareStringLength);

        for (int i = 0; i < smallestLengthString; i++) {
            int originalCharValue = (int)originalString.charAt(i);
            int compareCharValue = (int)compareString.charAt(i);

            if(originalCharValue != compareCharValue) {
                return originalCharValue - compareCharValue;
            }
        }

        if(!originalString.equals(compareString)) {
            return originalStringLength - compareStringLength;
        }
        if(this.getPrice() > o.getPrice()) {
            return 1;
        }
        else if(this.getPrice() < o.getPrice()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        Item compareObject = (Item) object;
        if(this.getName().toLowerCase() == compareObject.getName().toLowerCase() && this.getPrice() == compareObject.getPrice()) {
            return true;
        }
        return false;
    }
}
