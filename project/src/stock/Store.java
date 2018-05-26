package stock;

import java.io.IOException;
import java.util.List;

import csv.ReadCSV;

public class Store {
	
	private final double STARTING_CAPITAL = 100000.001;
	private final String STORE_NAME = "SuperMart";
	
	private double capital = STARTING_CAPITAL;
	private static Stock inventory = null;
	private String name = STORE_NAME;
	private static Store instance;
	
	private Store() {
	}

	public static Store getInstance() throws StockException, IOException {
		if (instance == null) instance = new Store();
		List<Item> itemList = ReadCSV.initialiseItems("item_properties.txt");
		for (Item i : itemList) {
			inventory.addQuantity(i, 0);
		}
		return instance;
	}

	public double getCapital() {
		return capital;
	}

	public void modifyCapital(double amountToAdd) {
		capital += amountToAdd;
	}

	public Stock getInventory() {
		return inventory;
	}

}
