package GameClasses;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;

    public Inventory() {
        inventory = new ArrayList<Item>();
    }

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

    public Item getItem(Item searchItem) {
        for (int i = 0; i < inventory.size(); i++) {
            if(searchItem.equals(inventory.get(i))) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public Item getItem(String searchName) {
        for (int i = 0; i < inventory.size(); i++) {
            if(searchName.equals(inventory.get(i).getName())) {
                return inventory.get(i);
            }
        }
        return null;
    }

    public int getSize() {
        return inventory.size();
    }

}
