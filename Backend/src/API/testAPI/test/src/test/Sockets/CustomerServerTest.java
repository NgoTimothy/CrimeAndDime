package Sockets;

import Endpoints.Customer;
import Endpoints.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CustomerServerTest {
    private ArrayList<Customer> customers;
    private Customer dummyCustomer1;
    private Customer dummyCustomer2;
    private ArrayList<Item> desiredItemsForCustomer1;
    private ArrayList<Item> desiredItemsForCustomer2;
    private Item fakeItem1;
    private Item fakeItem2;

    @Before
    public void setup() {
        fakeItem1 = new Item("apples", 10, 1.00);
        fakeItem2 = new Item("milk", 2, 3.50);
        desiredItemsForCustomer1 = new ArrayList<Item>();
        desiredItemsForCustomer2 = new ArrayList<Item>();
        desiredItemsForCustomer1.add(fakeItem1);
        desiredItemsForCustomer2.add(fakeItem2);
        dummyCustomer1 = new Customer(desiredItemsForCustomer1, 10);
        dummyCustomer2 = new Customer(desiredItemsForCustomer2, 7);
    }

    @Test
    public void whenNewCustomerIsCreatedItHasCorrectFieldValues() {
        assertEquals(1, dummyCustomer1.getDesiredItems().size());
        assertEquals(1, dummyCustomer2.getDesiredItems().size());
        assertEquals(fakeItem1, dummyCustomer1.getDesiredItems().get(0));
        assertEquals(fakeItem2, dummyCustomer2.getDesiredItems().get(0));
        assertEquals("Customer budget is not correct", 10, dummyCustomer1.getBudget(), 0);
        assertEquals("Customer budget is not correct", 7, dummyCustomer2.getBudget(), 0);
        assertFalse(dummyCustomer1.getHasShopped());
        assertFalse(dummyCustomer2.getHasShopped());
    }
}
