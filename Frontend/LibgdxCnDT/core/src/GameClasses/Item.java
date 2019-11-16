package GameClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Item represents the main object of the game.
 * An Item has a quantity, name, wholesale and retail cost.
 * Players order and add Items, while customers can buy Items.
 */
public class Item implements Comparable<Item> {
    private String name;
    private int quantity;
    @JsonProperty("price")
    private Double wholesaleCost;
    private Double retailCost;
    private boolean forSale;

    public Item() {
        name = "";
        wholesaleCost = 0.0;
        retailCost = 0.0;
        quantity = 0;
        forSale = false;
    }

    public Item(String initName) {
        name = initName;
        wholesaleCost =  0.0;
        retailCost = 0.0;
        quantity = 0;
        forSale = false;
    }

    public Item(String initName, double initWholesaleCost, double initRetailCost, int initQuantity) {
        name = initName;
        wholesaleCost = initWholesaleCost;
        retailCost = initRetailCost;
        quantity = initQuantity;
        forSale = false;
    }

    public Item(Item originalItem) {
        name = originalItem.getName();
        wholesaleCost = originalItem.getWholesaleCost();
        retailCost = originalItem.getRetailCost();
        quantity = originalItem.getQuantity();
        forSale = false;
    }

    /**
     * This returns the name of the item
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * This sets the name of the item
     * @param newName
     * @return
     */
    public boolean setName(String newName) {
        if(newName == null || newName.trim().isEmpty()) {
            return  false;
        }
        name = newName;
        return true;
    }

    /**
     * Returns the current quantity of the item
     * @return
     */
    public int getQuantity() {
        return  quantity;
    }

    /**
     * Sets the quantity of the item
     * @param newQuantity
     */
    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

    /**
     * Add onto the current quantity of the item
     * @param addedItems
     */
    public void addQuantity(int addedItems) {
        if(addedItems < 0) {
            return;
        }
        quantity += addedItems;
    }

    /**
     * Subtracts some number of quantity from the item
     * @param subtractedItems
     */
    public void subtractQuantity(int subtractedItems) {
        if(subtractedItems > quantity) {
            return;
        }
        quantity -= subtractedItems;
    }

    /**
     * Gets and returns the wholesale cost of the item
     * @return
     */
    public double getWholesaleCost() {
        return wholesaleCost;
    }

    /**
     * Sets the wholesale cost of the item
     * @param newWholesaleCost
     */
    public void setWholesaleCost(double newWholesaleCost) {
        if(newWholesaleCost < 0) {
            return;
        }
        wholesaleCost = newWholesaleCost;
    }

    /**
     * Gets and returns the retail cost of an item
     * @return
     */
    public double getRetailCost() {
        return retailCost;
    }

    /**
     * Sets the retail cost of an item.
     * @param newRetailCost
     */
    public void setRetailCost(double newRetailCost) {
        if(newRetailCost < 0) {
            return;
        }
        retailCost = newRetailCost;
    }

    /**
     * @return Whether this item is for sale or not
     */
    public boolean getForSale() { return forSale; }

    /**
     * Sets whether the item is for sale or not
     * @param forSale
     */
    public void setForSale(boolean forSale) { this.forSale = forSale; }

    /**
     * Clears quantity from item
     */
    public void clearQuantity() {
        quantity = 0;
    }

    /**
     * Returns a deep copy of the item
     * This means that a new item is created in a different memory
     * space and returned.
     * @return
     */
    public Item copy() {
        return new Item(this);
    }

    /**
     * If the original object is larger on the alphabet scale then it will return num greater than 0
     * If the same object based on name return 0
     * If smaller return num less than 0
     * @param o
     * @return
     */
    @Override
    public int compareTo(Item o) {
        String originalString = this.getName();
        String compareString = o.getName();
        int originalStringLength = originalString.length();
        int compareStringLength = compareString.length();
        int smallestLengthString = Math.min(originalStringLength, compareStringLength);

        for (int i = 0; i < smallestLengthString; i++) {
            int originalCharValue = originalString.charAt(i);
            int compareCharValue = compareString.charAt(i);

            if(originalCharValue != compareCharValue) {
                return originalCharValue - compareCharValue;
            }
        }

        if(originalString != compareString) {
            return originalStringLength - compareStringLength;
        }
        return 0;
    }

    /**
     * This returns true if two items are equal based off of the name of the two items
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        Item compareObject = (Item) object;
        if(this.getName().toLowerCase() == compareObject.getName().toLowerCase()) {
            return true;
        }
        return false;
    }

    /**
     * Overrides toString method to return parsable JSON string
     * @return
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append("{\"name\":" + "\"" + name + "\"" + ",")
                .append("\"quantity\":" + quantity + ",")
                .append("\"wholesaleCost\":" + wholesaleCost +  ",")
                .append("\"retailCost\":" + retailCost + "}")
                .toString();
    }

}
