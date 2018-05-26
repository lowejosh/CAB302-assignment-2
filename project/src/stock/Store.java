package stock;

import java.io.IOException;
import java.util.List;

import csv.ReadCSV;

public class Store {
    
    private final double STARTING_CAPITAL = 100000.00;
    private final String STORE_NAME = "SuperMart";
    
    private double capital = STARTING_CAPITAL;
    private static Stock inventory = null;
    private String name = STORE_NAME;
    private static Store instance;
    static List<Item> itemList;
    
    private Store() {
    }

    public static Store getInstance() throws StockException, IOException {
        if (instance == null) instance = new Store();
        inventory = new Stock();
        itemList = ReadCSV.initialiseItems("item_properties.txt");
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

	public List<Item> getItemList() {
		return itemList;
	}
}