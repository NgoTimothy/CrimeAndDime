package com.example.demo.GameLogicClasses;

import java.util.ArrayList;

public class StoreInfo {
    private String storeName;
    private Double cash;
    private int market_score;
    private boolean nextDay;
    private String owner;
    private Inventory inventory;

    public StoreInfo() {
        storeName = "";
        cash = 0.0;
        market_score = 0;
        nextDay = false;
        owner = "";
        inventory = new Inventory();
    }

    public StoreInfo(String storeName, double cash, int market_score, boolean nextDay, String owner) {
        this.storeName = storeName;
        this.cash = cash;
        this.market_score = market_score;
        this.nextDay = nextDay;
        this.owner = owner;
        inventory = new Inventory();
    }

    public String getStoreName() {
        return storeName;
    }

    public Double getCash() {
        return cash;
    }

    public int getMarket_score() {
        return market_score;
    }

    public boolean isNextDay() {
        return nextDay;
    }

    public String getOwner() {
        return owner;
    }

    public Inventory getInventory() { return inventory; }

    public ArrayList<Item> getList() { return inventory.getInventory(); }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public void setMarket_score(int market_score) {
        this.market_score = market_score;
    }

    public void setNextDay(boolean nextDay) {
        this.nextDay = nextDay;
    }

    public void setOwner(String newOwner) { owner = newOwner; }

}
