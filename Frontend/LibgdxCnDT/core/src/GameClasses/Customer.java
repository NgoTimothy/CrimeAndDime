package GameClasses;

import java.util.*;

/**
 * Customer class represents a customer in a store
 * Customers give money to store in exchange for an item
 * They shop and move around in the store itself
 */
public class Customer {
    boolean doneShopping;
    private double budget;
    private ArrayList<Item> desiredItems; // must be within budget

    public Customer(ArrayList<Item> desiredItems) {
        this.desiredItems = desiredItems;
        doneShopping = false;
        budget = 0.0;
    }

    /**
     * Signals that a customer has bought an item
     * @param doneShopping
     */
    public void setDoneShopping(boolean doneShopping) {
        this.doneShopping = doneShopping;
    }

    /**
     *
     * @return Whether the customer is done shopping or not
     */
    public boolean isDoneShopping() {
        return doneShopping;
    }

    /**
     * This method purchases the item for the customer and subtracts the cost of the item from the budget
     * @param item Returns the item bought from the store. If null, no item was purchased
     */
    public Item purchaseItem(Item item){
        for(int i = 0; i < desiredItems.size(); i++) {
            if(item.equals(desiredItems.get(i))) {
                item.setQuantity(getMaxPurchasableQuantity(item, desiredItems.get(i)));
                budget -= item.getQuantity() * item.getRetailCost();
                desiredItems.get(i).subtractQuantity(item.getQuantity());
                if(desiredItems.get(i).getQuantity() == 0) {
                    desiredItems.remove(i);
                }
                return item;
            }
        }
        return null;
    }

    /**
     * TODO test this later please
     * @param purchasableItem The item you are planning to purchase
     * @param stockedItem What the store currently stocks of said item you want to purchase
     * @return The max quantity you can purchase
     */
    private int getMaxPurchasableQuantity(Item purchasableItem, Item stockedItem) {
        while(purchasableItem.getQuantity() > stockedItem.getQuantity() && (purchasableItem.getQuantity() * purchasableItem.getRetailCost()) > budget) {
            purchasableItem.setQuantity(purchasableItem.getQuantity() - 1);
        }
        return purchasableItem.getQuantity();
    }

    /**
     * Returns the amount of money a customer has left to spend
     * @return
     */
    public Double getBudget() {
        return budget;
    }

    /**
     * Sets the budget of the customer
     * @param budget
     */
    public void setBudget(double budget) { this.budget = budget; }

    /**
     * Gets and returns the items that a customer wants to buy
     * @return
     */
    public ArrayList<Item> getDesiredItems() {
        return desiredItems;
    }

    public void setDesiredItems(ArrayList<Item> desiredItems) { this.desiredItems = desiredItems; }

    /**
     * Updates the list of items a customer wants to buy
     */
    /*public void updatePreferences(){
        for(int i = 0; i < desiredItems.size(); i++){
            if(item.getName() == desiredItems.get(i).getName()){
                desiredItems.get(i).subtractQuantity(1);
                budget -= item.getRetailCost();
            }
        }
        for(int i = 0; i< desiredItems.size(); i++){
            if(desiredItems.get(i).getRetailCost() > budget){
                desiredItems.get(i).subtractQuantity(10);
            }
        }
    }*/

    /**
     * Deducts money from the customers amount of money
     * @param price
     */
    public void subtractBudget(double price){
        budget -= price;
    }

    /**
     * Adds money to customers budget
     * @param price
     */
    public void addBudget(double price){
        budget += price;
    }
}
