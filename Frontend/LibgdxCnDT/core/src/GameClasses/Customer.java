package GameClasses;

import java.util.ArrayList;

public class Customer {

    //private double budget;
    private Item item;
   // private ArrayList<Item> preferences; // must be within budget

    public Customer(){
        preferences = new ArrayList<Item>();
        budget = 0;
    }

    public void buyItems(){
        /*
        loop through list of items and check which store has preferred items

           Purchase item
            -subtract budget from customer
            -subtract item from store inventory
            -removed item from list of preferences and update preferences

        repeat process if able to purchase another item
         */
    }
    public void setBudget(){
        budget = Math.floor(Math.random() * 495) +5;
    }

    public void setPreferences(double budget){
        /*
            -loops through all items
            -randomly chooses 5-10 items that are less than or equal to budget
        */
    }

    public double getBudget() {
        return budget;
    }

    public ArrayList<Item> getPreferences() {
        return preferences;
    }

    public void updatePreferences(double budget, ArrayList<Item> preferences, Item item){
        /*
            -loop through current preferences
            -remove item purchased
            -remove items they can no longer afford
        */

    }

    public void subtractBudgjet(double budget, double price){
        budget = budget - price;
    }

    public void removeItemFromStock(/*?*/){
        //should be in inventory class
    }


}
