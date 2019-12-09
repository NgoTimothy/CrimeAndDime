package GameClasses;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

/**
 * Customer class represents a customer in a store
 * Customers give money to store in exchange for an item
 * They shop and move around in the store itself
 */
public class Customer {
    private double budget;
    private ArrayList<Item> desiredItems;

    //
    private ArrayList<Vector2> itemLocation;
    //

    public Customer(ArrayList<Item> desiredItems, Double budget) {
        this.desiredItems = desiredItems;
        this.budget = budget;
    }

    public Customer(ArrayList<Item> desiredItems) {
        this.desiredItems = desiredItems;
        budget = totalCostOfItems(desiredItems);
    }

    /**
     * @return Whether the customer is done shopping or not
     */
    public boolean isDoneShopping() {
        return desiredItems.isEmpty();
    }


    /**
     * This method purchases the item for the customer and subtracts the cost of the item from the budget
     * @param itemToBePurchased Returns the item bought from the store. If null, no item was purchased
     */
    public Item purchaseItem(Item itemToBePurchased) {
        if(itemToBePurchased.getQuantity() <= 0)
            return null;
        for(int i = 0; i < desiredItems.size(); i++) {
            if(itemToBePurchased.equals(desiredItems.get(i))) {
                itemToBePurchased.setQuantity(getMaxPurchasableQuantity(itemToBePurchased, desiredItems.get(i)));
                desiredItems.remove(i);
                budget -= itemToBePurchased.getQuantity() * itemToBePurchased.getRetailCost();//In the edge case where you cannot buy the item, the total subtracted from the budget should be 0
                if(itemToBePurchased.getQuantity() > 0)
                    return itemToBePurchased;
                else
                    return null;
            }
        }
        return null;
    }

    /**
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
    public Double getBudget() { return budget; }

    /**
     * Sets the budget of the customer
     * @param budget
     */
    public void setBudget(double budget) { this.budget = budget; }

    /**
     * Gets and returns the items that a customer wants to buy
     * @return
     */
    public ArrayList<Item> getDesiredItems() { return desiredItems; }

    /**
     * Sets the list of desired items by customer to the passed list
     * @param desiredItems
     */
    public void setDesiredItems(ArrayList<Item> desiredItems) { this.desiredItems = desiredItems; }

    /**
     * Method will add an item to the list of items the customer wants to purchase
     * @param itemToBeAdded
     * @return true if the item was added or false if the item could not be added
     */
    public boolean addItem(Item itemToBeAdded) {
        if(itemToBeAdded.getQuantity() <= 0 || itemToBeAdded.getRetailCost() < 0)
            return false;
        desiredItems.add(itemToBeAdded);
        return true;
    }

    /**
     * Deducts money from the customers amount of money
     * @param price
     */
    public void subtractBudget(double price) {
        budget -= price;
    }

    /**
     * Adds money to customers budget
     * @param price
     */
    public void addBudget(double price) {
        budget += price;
    }

    /**
     * Method will calculate how much money the list of desired items cost
     * @param desiredCustomerItems
     * @return Amount of money requried to purchase all items
     */
    private double totalCostOfItems(ArrayList<Item> desiredCustomerItems) {
        double total = 0;
        for(int i = 0; i < desiredCustomerItems.size(); i++)
            total += desiredCustomerItems.get(i).getQuantity() * desiredCustomerItems.get(i).getRetailCost();
        return total;
    }
}
