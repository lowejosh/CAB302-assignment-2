package stock;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import csv.CSVFormatException;
import csv.ReadCSV;
/**
 * An object representing a store. Since the program
 * only supports one store, the class was written using the
 * Singleton pattern.
 * @author Ashley Husband
 *
 */
public class Store {
    
    private final double STARTING_CAPITAL = 100000.00;
    private final String STORE_NAME = "SuperMart";
    
    private double capital = STARTING_CAPITAL;
    private static Stock inventory = null;
    private String name = STORE_NAME;
    private static Store instance;
    private static List<Item> itemList;
    
    /**
     * Privately constructs the store so other classes cannot call it
     */
    private Store() {
    }

    /**
     * Returns the instance of the Store, initialising it if it does not already exist
     * @return the Store
     * @throws CSVFormatException 
     * @throws StockException 
     * @throws IOException 
     */
    public static Store getInstance() {
        if (instance == null) {
        	instance = new Store();
            inventory = new Stock();
            /*for debugging
            itemList = ReadCSV.initialiseItems("item_properties.txt");
            for (Item i : itemList) {
            	inventory.addQuantity(i, 0);
            }
            
            
        }
        return instance;
    }
    /**
     * Returns the capital of the Store
     * @return the capital
     */
    public double getCapital() {
        return capital;
    }
    
    /**
     * Modifies the capital of the Store by adding a number
     * (number can be negative to subtract money)
     * @param amountToAdd the amount to modify the capital by
     */
    public void modifyCapital(double amountToAdd) {
        capital += amountToAdd;
    }
    /**
     * Returns the inventory of the Store
     * @return the inventory as a Stock
     */
    public Stock getInventory() {
        return inventory;
    }
    
    /**
     * Returns the list of items carried by the Store
     * @return the item list
     */
	public List<Item> getItemList() {
		return itemList;
	}
	
	/**
	 * Sets the Store's item list and inventory as the passed item list
	 * (This method is called by the GUI to set up the item properties)
	 * @param list the list of items being passed
	 * @throws StockException if one of the items in the list cannot be added
	 */
	public void setItemList(List<Item> list) throws StockException {
		itemList = list;
		for (Item i : itemList) {
			inventory.addQuantity(i, 0);
		}
	}
	/**
	 * This static method loads a sales log file and updates the inventory and captital accordingly.
	 * @param fileName The name of the sales log file
	 * @throws IOException If the file does not exist
	 * @throws StockException If an item cannot be created
	 * @throws CSVFormatException If the file is in the incorrect format
	 */
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