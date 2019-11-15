package Sockets;

import Endpoints.Customer;
import Endpoints.Item;
import org.junit.Before;

import java.util.ArrayList;

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
}
