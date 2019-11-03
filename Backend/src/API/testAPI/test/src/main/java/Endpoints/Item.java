package Endpoints;

/**
 * The Item class contains item name. price, an item ID, and the quantity of the item.
 */
public class Item
{
    private String name;
    private double price;
    private int itemId;
    private int quantity;

    /**
     * Constructs an empty item
     */
    public Item() {
        name = "";
        price = 0;
        quantity = 0;
    }

    /**
     * Contructs an item using String name, Int quantity, and Double price.
     * @param name
     * @param quantity
     * @param price
     */
    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     *
     * @return Returns name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Returns price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return Returns ItemID
     */
    public int getItemId() { return itemId; }

    /**
     *
     * @return Returns Quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Sets the name variable to string name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price variable to double price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the itemID variable to int itemId
     * @param itemId
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Sets the quantity variable to int quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {this.quantity = quantity; }
}
