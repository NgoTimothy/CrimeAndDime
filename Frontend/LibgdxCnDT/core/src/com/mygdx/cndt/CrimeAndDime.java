package com.mygdx.cndt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GameClasses.Customer;
import GameClasses.Tile;
import GameExceptions.PlacingItemWithNoShelfException;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mygdx.Screen.*;

import GameClasses.Item;
import GameClasses.Store;

import Services.CrimeAndDimeService;

import static com.badlogic.gdx.math.MathUtils.random;

public class CrimeAndDime extends Game {

	public Store gameStore;
	public ArrayList<Item> items;
	public ArrayList<Tile> shelvesToBeBoughtFrom;//Shelves that customers can purchase from
	public ArrayList<Tile> purchasedShelves;
	public ArrayList<Customer> customers;
	public tileMapScreen tileMap;
	private Boolean shelfChanged;
	private float accumulator;
	private final float TIME_STEP = 1 / 30f; // 30 times a second
	private int hour;
	private boolean startTimer;
	private boolean nextDay;
	private boolean onBreak;
	private boolean customerBuyItems;
	private static final int closingTime = 20;
	private int day;
	private int timeLeftOnBreak;
	private int lobbyID;
	private String username;
	private boolean updateLobby;
	private boolean updateShelves;

	@Override
	public void create() {
		gameStore = new Store("My Store");
		tileMap = new tileMapScreen(this);
		CrimeAndDimeService newService = new CrimeAndDimeService();
		createItems(newService);
		customers = new ArrayList<Customer>();
		setScreen(new Splash(this));
		shelfChanged = false;
		startTimer = false;
		nextDay = false;
		onBreak = false;
		hour = 8;
		accumulator = 0;
		timeLeftOnBreak = 10;//This is the amount of time on break.
		lobbyID = -1;
		day = 1;
		updateLobby = false;
		updateShelves = false;
		shelvesToBeBoughtFrom = new ArrayList<Tile>();
		customerBuyItems = false;
		username = "initName";
		purchasedShelves = new ArrayList<Tile>();
	}

	@Override
	public void render() {
		super.render();
		if(startTimer) {
			accumulator += Gdx.graphics.getDeltaTime();
			if(accumulator >= 5f) {//1f is 1 second, 2f is 2 seconds and so forth
				customers.clear();
				customerBuyItems = true;
				createCustomers();
				hour++;
				accumulator = 0;
			}
		}
		else if(onBreak) {//If on break decrement time left on break until there is 0 seconds left
			accumulator += Gdx.graphics.getDeltaTime();
			if(accumulator >= 1f) {
				timeLeftOnBreak -= 1;
				if(timeLeftOnBreak <= 0)
					endBreak();
				accumulator = 0;
			}
		}
		screen.render(TIME_STEP);
	}

	/**
	 * Ends break and starts the game timer
	 */
	private void endBreak() {
		onBreak = false;
		timeLeftOnBreak = 30;
		startTimer = true;
		accumulator = 0;
	}

	/**
	 * Method will generated new customers for the day
	 */
	public void createCustomers() {
		customers.clear();
		//For now just generate 10 customers at random
		int newCustomers = getNumberOfCustomers();
		for(int i = 0; i < newCustomers; i++) {
			cleanShelvesToBeBoughtFrom();
			if(shelvesToBeBoughtFrom.isEmpty())
				break;
			int randomShelfIndex = random.nextInt(shelvesToBeBoughtFrom.size());
			Item customerDesiredItem = new Item(shelvesToBeBoughtFrom.get(randomShelfIndex).getItem());
			if(customerDesiredItem.getQuantity() <= 0) {
				System.out.println(customerDesiredItem.getQuantity());
				shelvesToBeBoughtFrom.remove(randomShelfIndex);
				continue;
			}
			int quantityPurchased = 0;
			if(customerDesiredItem.getQuantity() == 1)
				quantityPurchased = 1;
			else {
				quantityPurchased = random.nextInt(customerDesiredItem.getQuantity()) + 1;
			}
			customerDesiredItem.setQuantity(quantityPurchased);
			ArrayList<Item> desiredCustomerItems = new ArrayList<Item>();
			desiredCustomerItems.add(customerDesiredItem);
			Customer newCustomer = new Customer(desiredCustomerItems);
			customers.add(newCustomer);
			int indexOfItemInItems = items.indexOf(customerDesiredItem);
			if(indexOfItemInItems >= 0)
				items.get(indexOfItemInItems).subtractQuantity(customerDesiredItem.getQuantity());//Removes quantity from items (Inventory Screen)
			//Logic for getting tiles that have been purchased
			int indexOfShelfToBePurchasedFromInPurchasedShelves = purchasedShelves.indexOf(shelvesToBeBoughtFrom.get(randomShelfIndex));
			if(indexOfShelfToBePurchasedFromInPurchasedShelves >= 0) {//If found then simply add the extra items into the list
				purchasedShelves.get(indexOfShelfToBePurchasedFromInPurchasedShelves).getItem().addQuantity(quantityPurchased);
			}
			else {//If not in list then must add it and then set the quantity the amount purchased
				purchasedShelves.add(shelvesToBeBoughtFrom.get(randomShelfIndex));
				purchasedShelves.get(purchasedShelves.size() - 1).getItem().setQuantity(quantityPurchased);
			}
			//Subtract the item from the list of items that can be purchased from
			shelvesToBeBoughtFrom.get(randomShelfIndex).getItem().subtractQuantity(quantityPurchased);
			if(shelvesToBeBoughtFrom.get(randomShelfIndex).getItem().getQuantity() == 0) {
				shelvesToBeBoughtFrom.remove(randomShelfIndex);
			}
			//Adding money to player account
			double priceOfItemsPurchased = Math.round(customerDesiredItem.getRetailCost() * quantityPurchased * 100.0) / 100.0;
			gameStore.addBalance(priceOfItemsPurchased);
			newCustomer.purchaseItem(customerDesiredItem);
		}
		updateShelves = true;
	}

	private int getNumberOfCustomers() {
		return 2;
	}

	private void cleanShelvesToBeBoughtFrom() {
		for(int i = 0; i < shelvesToBeBoughtFrom.size(); i++) {
			if(shelvesToBeBoughtFrom.get(i).getItem() == null || shelvesToBeBoughtFrom.get(i).getItem().getQuantity() == 0)
				shelvesToBeBoughtFrom.remove(i);
		}
	}

	private void subQuantityFromItemsList(int quantityPurchased, Item customerDesiredItem) {
		if(quantityPurchased <= 0)
			return;
		System.out.println("Hello");
		for(int i = 0; i < items.size(); i++) {
			if(customerDesiredItem.equals(items.get(i))) {
				items.get(i).subtractQuantity(quantityPurchased);
				System.out.println(items.get(i));
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width,height);
	}

	public void createItems(CrimeAndDimeService service) {
		try {
			items = service.loadItems();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printTime(int seconds) {
		String AMOrPM = "AM";
		int timeOfDay = seconds;
		if(timeOfDay > 12) {
			timeOfDay -= 12;
			AMOrPM = "PM";
		}
		System.out.println(timeOfDay + ":00 " + AMOrPM);
		if(seconds == closingTime)
			startTimer = false;
	}

	public void advanceDay() {
		nextDay = false;
		hour = 8;
		day++;
		startTimer = true;
	}
	
	public ArrayList<Item> getItems() { return items; }

	public boolean isShelfChanged() { return shelfChanged; }

	public void setShelfChanged(boolean shelfChanged) { this.shelfChanged = shelfChanged; }

	public void setStartTimer(boolean startOrStop) { startTimer = startOrStop; }

	public boolean getStartTimer() { return startTimer; }

	public void setHour(int newHour) {
		hour = newHour;
		accumulator = 0;
	}

	public int getHour() { return hour; }

	public void increaseDay() { day++; }

	public int getDay() { return  day; }

	public void setNextDay(boolean readyForNewDay) { nextDay = readyForNewDay; }

	public boolean getNextDay() { return nextDay; }

	public void setOnBreak(boolean onBreak) { this.onBreak = onBreak; }

	public boolean getOnBreak() {return onBreak; }

	public void setUsername(String username) { this.username = username; }

	public String getUsername() { return username; }

	public void setUpdateLobby(boolean updateLobby) { this.updateLobby = updateLobby; }

	public boolean getUpdateLobby() { return updateLobby; }

	public int getLobbyID() { return lobbyID; }

	public void setLobbyID(int lobbyID) { this.lobbyID = lobbyID; }

	public void setShelvesToBeBoughtFrom(ArrayList<Tile> shelvesToBeBoughtFrom) { this.shelvesToBeBoughtFrom = new ArrayList<Tile>(shelvesToBeBoughtFrom); }

	public ArrayList<Tile> getShelvesToBeBoughtFrom() { return shelvesToBeBoughtFrom; }

	public boolean getCustomerBuyItems() { return customerBuyItems; }

	public void setCustomerBuyItems(boolean customerBuyItems) { this.customerBuyItems = customerBuyItems; }

	public void setUpdateShelves(boolean updateShelves) { this.updateShelves = updateShelves; }

	public boolean getUpdateShelves() { return updateShelves; }

	public void clearPurchasedShelves() { purchasedShelves.clear(); }
}
