package delivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public Manifest() throws StockException, IOException, DeliveryException {
		// Create a cold truck with the temperature of the item with the lowest temp
		// If there are still cold items, make a new cold truck with the lowest remaining temp
		// Create a reg truck and fill it to the max
		
		// Initialise variables
		Store store = Store.getInstance();
		Stock inventory = store.getInventory();
		List<Item> reorderItems = new ArrayList<>();
		List<Item> coldReorderItems = new ArrayList<>();

		
		// Find out which items need reordering
		for (Item i : store.getItemList()) {
			if (inventory.reorderRequired(i)) {
				if (i.getTemp() != null) {
					coldReorderItems.add(i);
				} else {
					reorderItems.add(i);
				}
			}
		}
		
		// Sort the list from lowest temperature to highest
		Collections.sort(coldReorderItems, Comparator.comparingInt(Item::getTemp));
		
		// Find out the item with the lowest required temperature
		Integer lowestTemp = null;
		if (coldReorderItems != null) {
			for (Item i : coldReorderItems) {
				if (lowestTemp == null) {
					lowestTemp = i.getTemp();
				}  else if (i.getTemp() < lowestTemp) {
					lowestTemp = i.getTemp();
				}
			}		
		}
		
		boolean itemsRemaining = true;
		// While there are still items
		int coldItr = 0;
		int regItr = 0;
		while(itemsRemaining) {
			if (lowestTemp != null) {
				// Create a cold truck with the lowest temp
				Truck coldTruck = new ColdTruck(lowestTemp);
				
				// While the truck isn't full, keep adding items in order of lowest temperature
				int currentCapacity = 0;
				for (int coldCheck = 0; coldCheck < coldReorderItems.size(); coldCheck++) {
					Item item = coldReorderItems.get(coldCheck);
					if (coldCheck >= coldItr && coldTruck.cargoCapacity > currentCapacity + item.getReorderQuantity()) {
						coldTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getName());
						coldItr++;
					}
				}
				for (int regCheck = 0; regCheck < reorderItems.size(); regCheck++) {
					Item item = reorderItems.get(regCheck);
					if (regCheck >= regItr && coldTruck.cargoCapacity > currentCapacity + item.getReorderQuantity()) {
						coldTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getName());
						regItr++;
					}
				}
				
				// Add the truck to the manifest
				trucks.add(coldTruck);
				System.out.println("added cold truck (" + coldTruck.getCost() + ") with capacity " + coldTruck.getCargo());
			} else {
				// Create a regular truck if there is no more cold items
				Truck regTruck = new RegTruck();
				int currentCapacity = 0;
				for (int regCheck = 0; regCheck < reorderItems.size(); regCheck++) {
					Item item = reorderItems.get(regCheck);
					if (regCheck >= regItr && regTruck.cargoCapacity > currentCapacity + item.getReorderQuantity()) {
						regTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getName());
						regItr++;
					}
				}
				
				// Add the truck to the manifest
				trucks.add(regTruck);
				System.out.println("added reg truck with capacity " + regTruck.getCargo());
			}
			
			// Update the lowest temp
			if (coldReorderItems != null && coldItr != coldReorderItems.size()) {
				lowestTemp = coldReorderItems.get(coldItr).getTemp();
			} else {
				lowestTemp = null;
			}

			// Stop the loop if all the items are in trucks
			if (coldItr == coldReorderItems.size() && regItr == reorderItems.size()) {
				itemsRemaining = false;
			}
			
		}

		System.out.println("reached");
		for (Truck truck : trucks) {
			System.out.println(truck.getCargo());
		}
		
	}
	
	// TODO - generateManifest() - export manifest csv based on manifest and reduce capital
	public static void generateManifest(Manifest trucks) {

		
	}

	
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
