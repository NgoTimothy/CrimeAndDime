package GameClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item implements Comparable<Item> {
    private String name;
    private int quantity;
    @JsonProperty("price")
    private Double wholesaleCost;
    private Double retailCost;

    public Item() {
        name = "";
<<<<<<< HEAD
        wholesaleCost = 0.0;
        retailCost = 0.0;
=======
        wholesaleCost = Double.valueOf(0);
        retailCost = Double.valueOf(0);
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
    }

    public Item(String initName) {
        name = initName;
<<<<<<< HEAD
        wholesaleCost =  0.0;
        retailCost = 0.0;
    }

    public Item(String initName, double initWholesaleCost, double initRetailCost, int initQuantity) {
        name = initName;
        wholesaleCost = initWholesaleCost;
        retailCost = initRetailCost;
        quantity = initQuantity;
=======
        wholesaleCost = Double.valueOf(0);
        retailCost = Double.valueOf(0);
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
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

<<<<<<< HEAD
    public void setQuantity(int newQuantity) {
        quantity = newQuantity;
    }

=======
    public void setQuantity(int numItems) {
        quantity = numItems;
    }
    
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
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

<<<<<<< HEAD
    public double getWholesaleCost() {
=======
    public Double getWholesaleCost() {
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
        return wholesaleCost;
    }

    public void setWholesaleCost(double newWholesaleCost) {
        if(newWholesaleCost < 0) {
            return;
        }
<<<<<<< HEAD
        wholesaleCost = newWholesaleCost;
    }

    public double getRetailCost() {
=======
        wholesaleCost = Double.valueOf(newWholesaleCost);
    }

    public Double getRetailCost() {
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
        return retailCost;
    }

    public void setRetailCost(double newRetailCost) {
        if(newRetailCost < 0) {
            return;
        }
<<<<<<< HEAD
        retailCost = newRetailCost;
=======
        retailCost = Double.valueOf(newRetailCost);
>>>>>>> d32591f691e0eb17c91c88071f951bbbf6aca60e
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

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{\"name\":" + "\"" + name + "\"" + ",")
                .append("\"quantity\":" + quantity + ",")
                .append("\"wholesaleCost\":" + wholesaleCost +  ",")
                .append("\"retailCost\":" + retailCost + "}")
                .toString();
        //return "\"name\":" + name + " quantity: " + quantity + " wholesaleCost: " + wholesaleCost + " retailCost " + retailCost;
    }


}