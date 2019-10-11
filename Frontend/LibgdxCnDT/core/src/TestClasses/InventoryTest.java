package TestClasses;

import GameClasses.Inventory;
import GameClasses.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory testInventory;
    private Item firstTestItem;
    private Item secondTestItem;

    @Before
    public void init() {
        testInventory = new Inventory();
        firstTestItem = new Item("Coke");
        secondTestItem = new Item("Coke");
    }

    @Test
    public void testAddingSameItemWithDifferentQuantitiesDoesNotMakeNewItemInInventory() {
        firstTestItem.addQuantity(10);
        secondTestItem.addQuantity(20);
        testInventory.addItem(firstTestItem);
        assertEquals(1, testInventory.getSize());
        testInventory.addItem(secondTestItem);
        assertEquals(1, testInventory.getSize());
    }

    @Test
    public void testAddingSameItemWithDifferentQuantitiesChangesQuantityInInventory() {
        String nameOfItem = firstTestItem.getName();
        firstTestItem.addQuantity(10);
        secondTestItem.addQuantity(20);
        testInventory.addItem(firstTestItem);
        assertEquals(10, testInventory.getItem(nameOfItem).getQuantity());
        testInventory.addItem(secondTestItem);
        assertEquals(30, testInventory.getItem(nameOfItem).getQuantity());
    }
}
