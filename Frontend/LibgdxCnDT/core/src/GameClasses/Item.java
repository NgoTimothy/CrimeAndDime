package GameClasses;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item implements Comparable<Item> {
    private String name;
    private int quantity;
    @JsonProperty("price")
    private BigDecimal wholesaleCost;
    private BigDecimal retailCost;

    public Item() {
        name = "";
        wholesaleCost = BigDecimal.valueOf(0);
        retailCost = BigDecimal.valueOf(0);
    }

    public Item(String initName) {
        name = initName;
        wholesaleCost = BigDecimal.valueOf(0);
        retailCost = BigDecimal.valueOf(0);
    }

    public Item(Item originalItem) {
        name = originalItem.getName();
        wholesaleCost = originalItem.getWholesaleCost();
        retailCost = originalItem.getRetailCost();
        quantity = originalItem.getQuantity();
    }

    public String getName() {
        return name;
    }

    public boolean setName(String newName) {
        if(newName == null || newName.trim().isEmpty()) {
            return  false;
        }
        name = newName;
        return true;
    }

    public int getQuantity() {
        return  quantity;
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

    public BigDecimal getWholesaleCost() {
        return wholesaleCost;
    }

    public void setWholesaleCost(double newWholesaleCost) {
        if(newWholesaleCost < 0) {
            return;
        }
        wholesaleCost = BigDecimal.valueOf(newWholesaleCost);
    }

    public BigDecimal getRetailCost() {
        return retailCost;
    }

    public void setRetailCost(double newRetailCost) {
        if(newRetailCost < 0) {
            return;
        }
        retailCost = BigDecimal.valueOf(newRetailCost);
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

    @Override
    public boolean equals(Object object) {
        Item compareObject = (Item) object;
        if(this.getName().toLowerCase() == compareObject.getName().toLowerCase()) {
            return true;
        }
        return false;
    }
}