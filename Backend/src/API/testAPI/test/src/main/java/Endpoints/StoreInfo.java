package Endpoints;

public class StoreInfo
{
    private String storeName;
    private Double cash;
    private int market_score;
    private boolean nextDay;

    public StoreInfo() {
        storeName = "";
        cash = 0.0;
        market_score = 0;
        nextDay = false;
    }

    public StoreInfo(String storeName, double cash, int market_score, boolean nextDay) {
        this.storeName = storeName;
        this.cash = cash;
        this.market_score = market_score;
        this.nextDay = nextDay;
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
}