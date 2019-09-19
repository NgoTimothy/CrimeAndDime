package Endpoints;

public class Item
{
    private String item;
    private int price;
    private int itemId;

    public String getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
