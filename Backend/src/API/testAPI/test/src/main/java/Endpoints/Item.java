package Endpoints;

public class Item
{
    private String name;
    private double price;
    private int itemId;
    private int quantity;

    public Item() {
        name = "";
        price = 0;
        quantity = 0;
    }

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemId() { return itemId; }

    public int getQuantity() { return quantity; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {this.quantity = quantity; }
}
