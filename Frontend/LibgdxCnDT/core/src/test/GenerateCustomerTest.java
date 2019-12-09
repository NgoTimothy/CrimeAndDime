import GameClasses.Inventory;
import GameClasses.Item;
import GameClasses.Store;
import com.github.javafaker.Faker;
import com.mygdx.cndt.CrimeAndDime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static org.junit.Assert.assertEquals;

public class GenerateCustomerTest {
    CrimeAndDime fakeGame;
    Faker faker;

    @Before
    public void setup() {
        fakeGame = new CrimeAndDime();
        fakeGame.gameStore = new Store("testStore", 0, 0, 0);
        fakeGame.customers = new ArrayList<>();
        faker = new Faker();

        for(int i = 0; i < 20; i++) {
            String itemName = faker.gameOfThrones().character();
            double itemRetail = Double.parseDouble(faker.commerce().price(1, 30));
            itemRetail = Math.round(itemRetail * 100.0) / 100.0;
            double itemWholesale = itemRetail - 1;
            itemWholesale = Math.round(itemWholesale * 100.0) / 100.0;
            int quantity = random.nextInt(20 - 1) + 1;
            Item newItem = new Item(itemName, itemWholesale, itemRetail, quantity);
            fakeGame.gameStore.getInventory().addItem(newItem);
        }
    }

    @Test
    public void generatedCustomersPurchaseValidAmountOfItems() {
        double total = 0;
        fakeGame.createCustomers();
        for(int i = 0; i < fakeGame.customers.size(); i++) {
            for(int j = 0; j < fakeGame.customers.get(i).getDesiredItems().size(); j++) {
                Item cusItem = fakeGame.customers.get(i).getDesiredItems().get(j);
                total += cusItem.getRetailCost() * cusItem.getQuantity();
            }
        }
        assertEquals("Customers did not properly generate and/or add enough balance to store", fakeGame.gameStore.getBalance(), total, .000001);
    }
}
