package Endpoints;

import java.util.ArrayList;

/**
 * Store info contains a users store's key info such as storeName, cash, market_score, and nextDay
 */
public class StoreInfo
{
    private String storeName;
    private Double cash;
    private int market_score;
    private boolean nextDay;
    private String owner;
    private Inventory inventory;

    /**
     * Create an empty StoreInfo class
     */
    public StoreInfo() {
        storeName = "";
        cash = 0.0;
        market_score = 0;
        nextDay = false;
        owner = "";
        inventory = new Inventory();
    }

    /**
     * Create a non-empty StoreInfo cash based on parameters
     * @param storeName
     * @param cash
     * @param market_score
     * @param nextDay
     */
    public StoreInfo(String storeName, double cash, int market_score, boolean nextDay) {
        this.storeName = storeName;
        this.cash = cash;
        this.market_score = market_score;
        this.nextDay = nextDay;
        this.owner = owner;
        inventory = new Inventory();
    }

    /**
     *
     * @return Returns storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     *
     * @return Returns cash
     */
    public Double getCash() {
        return cash;
    }

    /**
     *
     * @return Returns market_score
     */
    public int getMarket_score() {
        return market_score;
    }

    /**
     *
     * @return Returns nextDay
     */
    public boolean isNextDay() {
        return nextDay;
    }

    /**
     * Sets storeName variable to string storeName
     * @param storeName
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * Sets cash variable to double cash
     * @param cash
     */
    public void setCash(Double cash) {
        this.cash = cash;
    }

    /**
     * Sets market_score variable to int market_score
     * @param market_score
     */
    public void setMarket_score(int market_score) {
        this.market_score = market_score;
    }

    /**
     * sets nextDay variable to boolean nextDay
     * @param nextDay
     */
    public void setNextDay(boolean nextDay) {
        this.nextDay = nextDay;
    }

    public void setOwner(String newOwner) { owner = newOwner; }

    public Inventory getInventory() { return  inventory; }

    public ArrayList<Item> getList() {
        return inventory.getInventory();
    }

    public boolean getNextDay() { return nextDay; }
}
