package GameClasses;

import java.util.ArrayList;

/**
 * This class is used to hold all the items
 * that a store currently has
 */
public class Inventory {
    private ArrayList<Item> inventory;

    public Inventory() {
        inventory = new ArrayList<Item>();
    }

    /**
     * This method will subtract the quantity in the inventory by the quantity purchased
     * @param itemToBePurchased
     * @return Quantity purchased
     */
    public int purchaseItem(Item itemToBePurchased) {
        for(int i = 0; i < inventory.size(); i++) {
            if(itemToBePurchased.equals(inventory.get(i))) {
                int quantityLeftover = inventory.get(i).getQuantity() - itemToBePurchased.getQuantity();
                if(quantityLeftover == 0)
                    removeItem(itemToBePurchased);
                else
                    inventory.get(i).subtractQuantity(quantityLeftover);
                return itemToBePurchased.getQuantity();
            }
        }
        return 0;
    }

    /**
     * Adds an item to the inventory ArrayList
     * @param itemToBeAdded
     */
    public void addItem(Item itemToBeAdded) {
        if(itemToBeAdded == null || itemToBeAdded.getQuantity() == 0) {
            return;
        }
        for(int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).compareTo(itemToBeAdded) == 0) {
                inventory.get(i).addQuantity(itemToBeAdded.getQuantity());
                return;
            }
        }
        inventory.add(itemToBeAdded);
    }

    /**
     * Removes an item from the inventory
     * @param itemToBeRemoved
     */
    public void removeItem(Item itemToBeRemoved) {
        if(itemToBeRemoved == null) {
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).compareTo(itemToBeRemoved) == 0) {
                inventory.remove(i);
                return;
            }
        }
    }

    /**
     * Fetches and returns item based on the item itself
     * @param searchItem
     * @return
     */
    public Item getItem(Item searchItem) {
        for (int i = 0; i < inventory.size(); i++) {
            if(searchItem.equals(inventory.get(i))) {
                return inventory.get(i);
            }
        }
        return null;
    }

    /**
     * Fetches and returns item based on the item name
     * @param searchName
     * @return
     */
    public Item getItem(String searchName) {
        for (int i = 0; i < inventory.size(); i++) {
            if(searchName.equals(inventory.get(i).getName())) {
                return inventory.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the size of the inventory list
     * @return
     */
    public int getSize() {
        return inventory.size();
    }

    /**
     * Returns the current ArrayList of Items
     * @return
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
