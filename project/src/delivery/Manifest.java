package delivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import csv.ReadCSV;
import stock.Item;
import stock.Stock;
import stock.StockException;
import stock.Store;

/**
 * @author Joshua Lowe
 *
 */
public class Manifest {
	List<Truck> trucks = new ArrayList<>();
	
	public Manifest() {
		
		
	}
	
	// TODO - generateManifest() - export manifest csv based on current inventory
		// Create a cold truck with the temperature of the item with the lowest temp
		// If there are still cold items, make a new cold truck with the lowest remaining temp
		// Create a reg truck and fill it to the max
	
	// TODO - loadManifest() - load manifest csv and reduce store capital and increase inventory
	
	public static void loadSalesLog(String fileName) throws IOException, StockException {
		// Load in the sales and the store inventory
		Stock sales = ReadCSV.readSalesLog(fileName);
		Stock inventory = Store.getInstance().getInventory();
		
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
