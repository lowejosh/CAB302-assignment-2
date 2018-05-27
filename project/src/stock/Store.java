package stock;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import csv.CSVFormatException;
import csv.ReadCSV;

public class Store {
    
    private final double STARTING_CAPITAL = 100000.00;
    private final String STORE_NAME = "SuperMart";
    
    private double capital = STARTING_CAPITAL;
    private static Stock inventory = null;
    private String name = STORE_NAME;
    private static Store instance;
    private static List<Item> itemList;
    
    private Store() {
    }

    public static Store getInstance() throws StockException, IOException, CSVFormatException {
        if (instance == null) {
        	instance = new Store();
            inventory = new Stock();
            //itemList = ReadCSV.initialiseItems("item_properties.txt");
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
	
	public void setItemList(List<Item> list) throws StockException {
		itemList = list;
		for (Item i : itemList) {
			inventory.addQuantity(i, 0);
		}
		
	}
	
	public static void loadSalesLog(String fileName) throws IOException, StockException, CSVFormatException {
		// Load in the sales and the store inventory
		Stock sales = ReadCSV.readSalesLog(fileName);
		
		// Increase the capital depending on sales
		Store.getInstance().modifyCapital(sales.getTotalPrice());
		
		// Iterate through every item in sales log
	    Iterator<Entry<Item, Integer>> itr = sales.getStock().entrySet().iterator();
	    while (itr.hasNext()) {
	        Entry<Item, Integer> pair = itr.next();
	        Item i = pair.getKey();
	        // Remove the amount of items sold from the store inventory
	        int quantity = sales.getQuantity(i);
	        inventory.removeQuantity(i, quantity);
	        itr.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
}