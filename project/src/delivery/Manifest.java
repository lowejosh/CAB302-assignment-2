package delivery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import csv.CSVFormatException;
import csv.ReadCSV;
import stock.Item;
import stock.Stock;
import stock.StockException;
import stock.Store;

/**
 * @author Joshua Lowe
 *
 *	This class represents a manifest, which is a list of truck
 *	with cargo of their own. This class includes the automateManifest algorithm which
 *	creates an efficient manifest when given an inventory. It also holds different methods
 *	for retrieving manifests and sales logs and updating the stores values.
 *
 */
public class Manifest {
	private List<Truck> trucks;
	
	/**
	 * This constructor intialises the trucks list as an empty arrayList
	 */
	public Manifest() {
		trucks = new ArrayList<>();
	}
	
	/**
	 * This method adds a truck to the list of trucks
	 * @param truck The truck object to be added
	 */
	public void addTruck(Truck truck) {
		trucks.add(truck);
	}
	
	/**
	 * This method returns the list of trucks
	 * @return The list of trucks
	 */
	public List<Truck> getManifest() {
		return trucks;
	}
	
	/**
	 * This method retrieves the sum of the cost of all of the trucks and returns it
	 * @return The cost of the manifest
	 */
	public double getCost() {
		double sum = 0;
		// Sum the cost of the truck + the cost of the items
		for (Truck truck : trucks) {
			sum+=truck.getCost() + truck.getStock().getTotalCost();
			
		}
		return sum;
	}
	
	/**
	 * This static method generates a manifest given an inventory, it finds out which items need to be
	 * stocked up and goes about assigning the orders to different trucks effectively.
	 * @param inventory The inventory which to generate the manifest from
	 * @return The manifest created
	 * @throws StockException If the object cannot be created
	 * @throws IOException If the file cannot be found
	 * @throws DeliveryException If the truck cannot be created or the cargo cannot be added
	 */
	public static Manifest automateManifest(Stock inventory) throws StockException, IOException, DeliveryException {
		// Initialise variables
		Manifest manifest = new Manifest();
		List<Item> reorderItems = new ArrayList<>();
		List<Item> coldReorderItems = new ArrayList<>();

		// Find out which items need reordering
		for (Item i : Store.getInstance().getItemList()) {
			if (inventory.reorderRequired(i)) {
				if (i.getTemp() != null) {
					coldReorderItems.add(i);
				} else {
					reorderItems.add(i);
				}
			}
		}
		
		// Initialize an array of booleans for holding whether the item has been removed or not
		boolean[] coldItemRemovedIndex = new boolean[coldReorderItems.size()];
		boolean[] regItemRemovedIndex = new boolean[reorderItems.size()];
		// Initialize the values as false
		for (int i = 0; i < coldReorderItems.size(); i++) {
			coldItemRemovedIndex[i] = false;
		}
		for (int i = 0; i < reorderItems.size(); i++) {
			regItemRemovedIndex[i] = false;
		}
		
		// Sort the cold list from lowest temperature to highest
		Collections.sort(coldReorderItems, Comparator.comparingInt(Item::getTemp));
		// Sort the reg list from highest to lowest reorder quantity, for best optimization
		Collections.sort(reorderItems, Comparator.comparingInt(Item::getReorderQuantity).reversed());
		
		// Find out the item with the lowest required temperature and set the variable
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
				System.out.println("CREATING COLD TRUCK");
				// Create a cold truck with the lowest temp
				Truck coldTruck = new ColdTruck(lowestTemp);
				
				// While the truck isn't full, keep adding items in order of lowest temperature
				int currentCapacity = 0;
				for (int coldCheck = 0; coldCheck < coldReorderItems.size(); coldCheck++) {
					Item item = coldReorderItems.get(coldCheck);
					if (!coldItemRemovedIndex[coldCheck] && coldTruck.cargoCapacity >= currentCapacity + item.getReorderQuantity()) {
						coldTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getReorderQuantity() + " " + item.getName());
						coldItemRemovedIndex[coldCheck] = true;
						coldItr++;
					}
				}
				// If there can be no more coldItems fit, try to squeeze in some reg items
				// Squeeze in as many as you can
				for (int regCheck = 0; regCheck < reorderItems.size(); regCheck++) {
					Item item = reorderItems.get(regCheck);
	
					if (!regItemRemovedIndex[regCheck] && coldTruck.cargoCapacity >= currentCapacity + item.getReorderQuantity()) {
						coldTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getReorderQuantity() + " " + item.getName());
						regItemRemovedIndex[regCheck] = true;
						
						regItr++;
					}
				}
						
				// Add the truck to the manifest
				manifest.addTruck(coldTruck);
				System.out.println("added cold truck ($" + coldTruck.getCost() + ") with capacity " + coldTruck.getCargo());
				System.out.println("item cost ($" + coldTruck.getStock().getTotalCost() + ")\n");
			// If there are no more cold items - create a reg truck
			} else {
				System.out.println("CREATING REG TRUCK");
				Truck regTruck = new RegTruck();
				int currentCapacity = 0;
				// Squeeze in as many as you can after adding the largest cargo
				
				for (int regCheck = 0; regCheck < reorderItems.size(); regCheck++) {
					Item item = reorderItems.get(regCheck);
	
					if (!regItemRemovedIndex[regCheck] && regTruck.cargoCapacity >= currentCapacity + item.getReorderQuantity()) {
						regTruck.addCargo(item, item.getReorderQuantity());
						currentCapacity+=item.getReorderQuantity();
						System.out.println("\tadded " + item.getReorderQuantity() + " " + item.getName());
						regItemRemovedIndex[regCheck] = true;
						
						regItr++;
					}
				}
				
				// Add the truck to the manifest
				manifest.addTruck(regTruck);
				System.out.println("added reg truck ($" + regTruck.getCost() + ") with capacity " + regTruck.getCargo());
				System.out.println("item cost ($" + regTruck.getStock().getTotalCost() + ")\n");
			}
			
			// Update the lowest temp
			if (coldReorderItems != null && coldItr != coldReorderItems.size()) {
				lowestTemp = coldReorderItems.get(coldItr).getTemp();
			} else {
				lowestTemp = null;
			}
			
			// Find out the item with the lowest required temperature again and set the variable
			if (coldReorderItems != null) {
				for (int i = 0; i < coldReorderItems.size(); i++) {
					Item item = coldReorderItems.get(i);
					if (lowestTemp == null && !coldItemRemovedIndex[i]) {
						lowestTemp = item.getTemp();
					} else if (lowestTemp != null) {
						if (item.getTemp() < lowestTemp && !coldItemRemovedIndex[i]) {
							lowestTemp = item.getTemp();
						}
					} else {
						lowestTemp = null;
					}
				}		
			}

			// Stop the loop if all the items are in trucks
			if (coldItr == coldReorderItems.size() && regItr == reorderItems.size()) {
				itemsRemaining = false;
			}
		}
		return manifest;
	}
	
	/**
	 * This static method loads a manifest file and updates the capital and stock according to
	 * the values retrieved
	 * @param fileName File name of the manifest file
	 * @throws IOException If the file does not exist
	 * @throws StockException If an item cannot be created
	 * @throws CSVFormatException If the file is in the incorrect format
	 */
	public static void loadManifest(String fileName) throws IOException, StockException, CSVFormatException {
		// Initialise variables
		Manifest manifest = ReadCSV.readManifest(fileName);
		double cost = manifest.getCost();
		Store store = Store.getInstance();
		store.modifyCapital(-cost);
		Stock inventory = store.getInventory();
		
		// Iterate through every truck in the manifest
	    for (Truck truck : manifest.getManifest()) {
	    	Stock cargo = truck.getStock();
			// Iterate through every item in the cargo
		    Iterator<Entry<Item, Integer>> itr = cargo.getStock().entrySet().iterator();
		    while (itr.hasNext()) {
		        Entry<Item, Integer> pair = itr.next();
		        Item i = pair.getKey();
		        // Add the amount of items received to the store inventory
		        int quantity = cargo.getQuantity(i);
		        inventory.addQuantity(i, quantity);
		        itr.remove(); // avoids a ConcurrentModificationException
		    }
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
