package PlayerObjects;

import java.math.BigDecimal;

public class ItemEx {
    private String name;
    private int quantity;
    private BigDecimal wholesaleCost;
    private BigDecimal retailCost;

    public ItemEx() {
        name = "";
        wholesaleCost = BigDecimal.valueOf(0);
        retailCost = BigDecimal.valueOf(0);
    }

    public String getName() {
        return name;
    }

    public boolean setName(String newName) {
        if(newName == null || newName.trim().isEmpty()) {
            return  false;
        }
        name = newName;
        return true;
    }

    public int getQuantity() {
        return  quantity;
    }

    public void addItemQuantity(int addedItems) {
        if(addedItems < 0) {
            return;
        }
        quantity += addedItems;
    }

    public void subtractItemQuantity(int subtractedItems) {
        if(subtractedItems > quantity) {
            return;
        }
        quantity -= subtractedItems;
    }

    public BigDecimal getWholesaleCost() {
        return wholesaleCost;
    }

    public void setWholesaleCost(double newWholesaleCost) {
        if(newWholesaleCost < 0) {
            return;
        }
        wholesaleCost = BigDecimal.valueOf(newWholesaleCost);
    }

    public BigDecimal getRetailCost() {
        return retailCost;
    }

    public void setRetailCost(double newRetailCost) {
        if(newRetailCost < 0) {
            return;
        }
        retailCost = BigDecimal.valueOf(newRetailCost);
    }
}
