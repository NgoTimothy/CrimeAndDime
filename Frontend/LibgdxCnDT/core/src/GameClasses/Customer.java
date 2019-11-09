package GameClasses;

import java.util.*;

/**
 * Customer class represents a customer in a store
 * Customers give money to store in exchange for an item
 * They shop and move around in the store itself
 */
public class Customer {

    private Item item;
    boolean hasBought;
    private double budget;
    private ArrayList<Item> preferences; // must be within budget

    public Customer(){
        item = new Item();
        hasBought = false;
        budget = 0.0;
        preferences = new ArrayList<Item>();
    }

    /**
     * Sets item that it bought
     * @param item
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Signals that a customer has bought an item
     * @param hasBought
     */
    public void setHasBought(boolean hasBought) {
        this.hasBought = hasBought;
    }

    /**
     * Returns the item that a customer has bought
     * @return
     */
    public Item getItem() {
        return item;
    }

    /**
     *
     * @return
     */
    public boolean isHasBought() {
        return hasBought;
    }

    /**
     * If customer has not bought an item, they will buy the item(s)
     * @param inventory
     */
    public void buyItems(Inventory inventory){
        /*
        loop through list of items and check which store has preferred items

           Purchase item
            -subtract budget from customer
            -subtract item from store inventory
            -removed item from list of preferences and update preferences

        repeat process if able to purchase another item
         */
        if(hasBought == false) {
            inventory.removeItem(item);
            hasBought = true;
        }

    }

    /**
     * Customer will buy item if they have not bought an item yet
     * @param inventories
     */
    public void buyItems(ArrayList<Inventory> inventories){
        if (hasBought == false){
            for (int i = 0; i < inventories.size(); i++){
                for (int j = 0; j < inventories.get(j).getSize(); j++){
                    if(inventories.get(j).getItem(item) == item){
                        inventories.get(j).removeItem(item);
                        hasBought = true;
                    }
                }
            }
        }
    }

    /**
     * Returns the amount of money a customer has left to spend
     * @return
     */
    public Double getBudget() {
        return budget;
    }

    /**
     * Gets and returns the items that a customer wants to buy
     * @return
     */
    public ArrayList<Item> getPreferences() {
        return preferences;
    }

    /**
     * Updates the list of items a customer wants to buy
     */
    public void updatePreferences(){
        /*
            -loop through current preferences

            -remove item purchased
            -remove items they can no longer afford
        */
        for(int i =0; i < preferences.size(); i++){
            if(item.getName() == preferences.get(i).getName()){
                preferences.get(i).subtractQuantity(1);
                budget -= item.getRetailCost();
            }
        }
        for(int i =0; i< preferences.size(); i++){
            if(preferences.get(i).getRetailCost() > budget){
                preferences.get(i).subtractQuantity(10);
            }
        }

    }

    /**
     * Deducts money from the customers amount of money
     * @param price
     */
    public void subtractBudget(double price){
        budget = budget - price;
    }
}
