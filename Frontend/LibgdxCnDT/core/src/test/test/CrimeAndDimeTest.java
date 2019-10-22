package test.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mygdx.cndt.CrimeAndDime;

import GameClasses.Item;
import Services.CrimeAndDimeService;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

public class CrimeAndDimeTest {

	
	private CrimeAndDimeService service;
	private CrimeAndDime crimeAndDime;
	
	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException
	{
		String expectedJSON = "[{\"name\":\"charger\",\"price\":5.0,\"itemId\":2,\"quantity\":0}]";
		Item item = new Item("charger");
		item.setWholesaleCost(5.0);
		item.setQuantity(0);
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(item);
		service = mock(CrimeAndDimeService.class);
		when(service.loadItems()).thenReturn(items);
		crimeAndDime = new CrimeAndDime();
	}
	
	@Test
	public void itemNameTest() {
		crimeAndDime.createItems(service);
		assertEquals("charger", crimeAndDime.getItems().get(0).getName());
	}
	
	@Test
	public void itemQuantityTest() {
		crimeAndDime.createItems(service);
		assertEquals(0, crimeAndDime.getItems().get(0).getQuantity());
	}
	
	@Test
	public void itemPriceTest() {
		crimeAndDime.createItems(service);
		assertEquals(5.0, crimeAndDime.getItems().get(0).getWholesaleCost(), 0.001);
	}
	
	

}
