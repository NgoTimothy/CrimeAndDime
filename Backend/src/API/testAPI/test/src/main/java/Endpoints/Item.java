package Endpoints;

public class Item
{
    private String name;
    private double price;
    private int itemId;
    private int quantity;

    public Item() {
        name = "";
        price = 0;
        quantity = 0;
    }

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemId() { return itemId; }

    public int getQuantity() { return quantity; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

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
