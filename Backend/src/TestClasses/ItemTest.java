package TestClasses;

import GameClasses.Item;
import org.junit.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ItemTest {
    private Item testItem;

    @Before
    public void init() {
        testItem = new Item();
    }

    @Test
    public void testInitItemNameRetrieval() {
        assertEquals("", testItem.getName());
    }

    @Test
    public void testChangingGoodItemNameWasChanged() {
        testItem.setName("Cat Litter");
        assertEquals("Cat Litter", testItem.getName());
    }

    @Test
    public void testChangingGoodItemNameWasSuccess() {
        assertTrue(testItem.setName("Cat Litter"));
    }

    @Test
    public void testSettingItemNameToEmptyFails() {
        assertFalse(testItem.setName(""));
    }

    @Test
    public void testSettingItemNameToNullFails() {
        assertFalse(testItem.setName(null));
    }

    @Test
    public void testSettingItemNameToNullOrEmptyDoesNotChangeName() {
        testItem.setName("apples");
        assertEquals("apples", testItem.getName());
        testItem.setName("");
        assertEquals("apples", testItem.getName());
        testItem.setName(null);
        assertEquals("apples", testItem.getName());
    }

    @Test
    public void testInitGetQuantity() {
        assertEquals(0, testItem.getQuantity());
    }

    @Test
    public void testAddingItemQuantity() {
        testItem.addItemQuantity(2);
        assertEquals(2, testItem.getQuantity());
    }

    @Test
    public void testAddingNegativeQuantity() {
        testItem.addItemQuantity(-2);
        assertEquals(0, testItem.getQuantity());
    }

    @Test
    public void testSubtractingQuantity() {
        testItem.addItemQuantity(100);
        testItem.subtractItemQuantity(20);
        assertEquals(80, testItem.getQuantity());
    }

    @Test
    public void testSubtractingQuantityThatIsGreaterThanQuantityOnHand() {
        testItem.addItemQuantity(5);
        testItem.subtractItemQuantity(10);
        assertEquals(5, testItem.getQuantity());
    }

    @Test
    public void testInitGetWholesaleCost() {
        assertEquals(BigDecimal.valueOf(0), testItem.getWholesaleCost());
    }

    @Test
    public void testSettingGoodValueForWholesaleCost() {
        testItem.setWholesaleCost(2.50);
        assertEquals(BigDecimal.valueOf(2.50), testItem.getWholesaleCost());
    }

    @Test
    public void testSettingBadValueForWholesaleCost() {
        testItem.setWholesaleCost(-29);
        assertEquals(BigDecimal.valueOf(0), testItem.getWholesaleCost());
    }

    @Test
    public void testInitRetailCost() {
        assertEquals(BigDecimal.valueOf(0), testItem.getRetailCost());
    }

    @Test
    public void testSettingGoodValueForRetailCost() {
        testItem.setRetailCost(10);
        assertEquals(BigDecimal.valueOf(10.0), testItem.getRetailCost());
    }

    @Test
    public void testSettingBadValueForRetailCost() {
        testItem.setRetailCost(-25.55);
        assertEquals(BigDecimal.valueOf(0), testItem.getRetailCost());
    }

}
