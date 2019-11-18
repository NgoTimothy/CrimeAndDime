package Endpoints;

import java.util.ArrayList;

public class Customer {
    private ArrayList<Item> desiredItems;
    private double budget;
    private boolean hasShopped;

    public Customer(ArrayList<Item> desiredItems, double budget) {
        this.desiredItems = desiredItems;
        this.budget = budget;
        this.hasShopped = false;
    }

    public void setHasShopped(boolean doneShopping) { hasShopped = doneShopping; }

    public boolean getHasShopped(){ return hasShopped; }

    public void setBudget(double newBudget) { budget = newBudget; }

    public double getBudget() { return budget; }

    public void setDesiredItems(ArrayList<Item> newDesiredItems) { desiredItems = newDesiredItems; }

    public ArrayList<Item> getDesiredItems() { return desiredItems; }
}
