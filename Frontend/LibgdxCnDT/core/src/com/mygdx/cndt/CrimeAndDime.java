package com.mygdx.cndt;

import java.io.IOException;
import java.util.ArrayList;

import GameClasses.Customer;
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
	public ArrayList<Customer> customers;
	public tileMapScreen tileMap;
	private Boolean shelfChanged;
	private float accumulator;
	private final float TIME_STEP = 1 / 30f; // 30 times a second
	private int hour;
	private int lobbyID;
	private boolean startTimer;
	private boolean nextDay;
	private boolean onBreak;
	private static final int closingTime = 20;
	private int day;
	private int timeLeftOnBreak;

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
		onBreak = true;
		hour = 8;
		accumulator = 0;
		timeLeftOnBreak = 10;//This is the amount of time on break.
		//printTime(hour);
		day = 1;
	}

	@Override
	public void render() {
		super.render();
		if(startTimer) {
			accumulator += Gdx.graphics.getDeltaTime();
			if(accumulator >= 5f) {//1f is 1 second, 2f is 2 seconds and so forth
				customers.clear();
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
		//For now just generate 10 customers at random
		if(gameStore.getListOfInventoryItems().size() <= 0)
			return;
		int numOfNewCustomers = 10;
		for(int i = 0; i < numOfNewCustomers; i++) {//For testing purposes only one item is added to customers desired items
			if(gameStore.getListOfInventoryItems().isEmpty())
				return;
			int randItemIndex = random.nextInt(gameStore.getListOfInventoryItems().size());
			Item customerDesiredItem = new Item(gameStore.getListOfInventoryItems().get(randItemIndex));
			if(gameStore.getListOfInventoryItems().get(randItemIndex).getQuantity() > 1)
				customerDesiredItem.setQuantity(random.nextInt(gameStore.getListOfInventoryItems().get(randItemIndex).getQuantity() - 1) + 1);
			else
				customerDesiredItem.setQuantity(1);
			ArrayList<Item> desiredCustomerItems = new ArrayList<Item>();
			desiredCustomerItems.add(customerDesiredItem);
			double budget = totalCostOfItems(desiredCustomerItems);
			Customer newCustomer = new Customer(desiredCustomerItems, budget);
			customers.add(newCustomer);
			int quantityPurchased = gameStore.getInventory().purchaseItem(customerDesiredItem);//When customers are generated they are automatically purchased from store, may delete later
			subQuantityFromItemsList(quantityPurchased, customerDesiredItem);
			double priceToBeAdded = Math.round((customerDesiredItem.getRetailCost() * customerDesiredItem.getQuantity()) * 100.0) / 100.0;
			gameStore.addBalance(priceToBeAdded);//May delete this too
			newCustomer.purchaseItem(customerDesiredItem);
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

	/**
	 * Method will calculate how much money the list of desired items cost
	 * @param desiredCustomerItems
	 * @return Amount of money requried to purchase all items
	 */
	private double totalCostOfItems(ArrayList<Item> desiredCustomerItems) {
		double total = 0;
		for(int i = 0; i < desiredCustomerItems.size(); i++)
			total += desiredCustomerItems.get(i).getQuantity() * desiredCustomerItems.get(i).getRetailCost();
		return total;
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

	public void setLobbyID(int lobbyID) { this.lobbyID = lobbyID; }

	public int getLobbyID() { return lobbyID; }
}

