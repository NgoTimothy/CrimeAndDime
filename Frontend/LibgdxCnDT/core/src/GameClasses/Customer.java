package GameClasses;

import java.util.*;



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

    public void setItem(Item item) {
        this.item = item;
    }

    public void setHasBought(boolean hasBought) {
        this.hasBought = hasBought;
    }

    public Item getItem() {
        return item;
    }

    public boolean isHasBought() {
        return hasBought;
    }

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


//    public void setBudget(){
//        budget = Math.floor(Math.random() * 495) +5;
//    }

    public void setPreferences(double budget){
        /*
            -loops through all items
            -randomly chooses 5-10 items that are less than or equal to budget
        */
    }

    public Double getBudget() {
        return budget;
    }

    public ArrayList<Item> getPreferences() {
        return preferences;
    }

    public void updatePreferences(){
        /*
            -loop through current preferences

            -remove item purchased
            -remove items they can no longer afford
        */
        for(int i =0; i < preferences.size(); i++){
            if(item.getName() == preferences.get(i).getName()){
                preferences.get(i).subtractQuantity(1);
                budget = budget - item.getRetailCost();
            }
        }
        for(int i =0; i< preferences.size(); i++){
            if(preferences.get(i).getRetailCost() == budget){
                preferences.get(i).subtractQuantity(10);
            }
        }

    }

    public void subtractBudget(double price){
        budget = budget - price;
    }
}
