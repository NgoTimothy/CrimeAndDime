package Endpoints;

public class Item
{
    private String name;
    private double price;
    private int itemId;

    public Item() {
        name = "";
        price = 0;
    }

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemId() { return itemId; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
