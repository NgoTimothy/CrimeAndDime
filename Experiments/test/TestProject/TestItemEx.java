package TestProject;

import PlayerObjects.ItemEx;
import org.junit.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TestItemEx {

    private ItemEx testItemEx;

    @Before
    public void init() {
        testItemEx = new ItemEx();
    }

    @Test
    public void testInitItemNameRetrieval() {
        assertEquals("", testItemEx.getName());
    }

    @Test
    public void testChangingGoodItemNameWasChanged() {
        testItemEx.setName("Cat Litter");
        assertEquals("Cat Litter", testItemEx.getName());
    }

    @Test
    public void testChangingGoodItemNameWasSuccess() {
        assertTrue(testItemEx.setName("Cat Litter"));
    }

    @Test
    public void testSettingItemNameToEmptyFails() {
        assertFalse(testItemEx.setName(""));
    }

    @Test
    public void testSettingItemNameToNullFails() {
        assertFalse(testItemEx.setName(null));
    }

    @Test
    public void testSettingItemNameToNullOrEmptyDoesNotChangeName() {
        testItemEx.setName("apples");
        assertEquals("apples", testItemEx.getName());
        testItemEx.setName("");
        assertEquals("apples", testItemEx.getName());
        testItemEx.setName(null);
        assertEquals("apples", testItemEx.getName());
    }

    @Test
    public void testInitGetQuantity() {
        assertEquals(0, testItemEx.getQuantity());
    }

    @Test
    public void testAddingItemQuantity() {
        testItemEx.addItemQuantity(2);
        assertEquals(2, testItemEx.getQuantity());
    }

    @Test
    public void testAddingNegativeQuantity() {
        testItemEx.addItemQuantity(-2);
        assertEquals(0, testItemEx.getQuantity());
    }

    @Test
    public void testSubtractingQuantity() {
        testItemEx.addItemQuantity(100);
        testItemEx.subtractItemQuantity(20);
        assertEquals(80, testItemEx.getQuantity());
    }

    @Test
    public void testSubtractingQuantityThatIsGreaterThanQuantityOnHand() {
        testItemEx.addItemQuantity(5);
        testItemEx.subtractItemQuantity(10);
        assertEquals(5, testItemEx.getQuantity());
    }

    @Test
    public void testInitGetWholesaleCost() {
        assertEquals(BigDecimal.valueOf(0), testItemEx.getWholesaleCost());
    }

    @Test
    public void testSettingGoodValueForWholesaleCost() {
        testItemEx.setWholesaleCost(2.50);
        assertEquals(BigDecimal.valueOf(2.50), testItemEx.getWholesaleCost());
    }

    @Test
    public void testSettingBadValueForWholesaleCost() {
        testItemEx.setWholesaleCost(-29);
        assertEquals(BigDecimal.valueOf(0), testItemEx.getWholesaleCost());
    }

    @Test
    public void testInitRetailCost() {
        assertEquals(BigDecimal.valueOf(0), testItemEx.getRetailCost());
    }

    @Test
    public void testSettingGoodValueForRetailCost() {
        testItemEx.setRetailCost(10);
        assertEquals(BigDecimal.valueOf(10.0), testItemEx.getRetailCost());
    }

    @Test
    public void testSettingBadValueForRetailCost() {
        testItemEx.setRetailCost(-25.55);
        assertEquals(BigDecimal.valueOf(0), testItemEx.getRetailCost());
    }

}
