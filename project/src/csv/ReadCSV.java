package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import delivery.ColdTruck;
import delivery.DeliveryException;
import delivery.Manifest;
import delivery.RegTruck;
import delivery.Truck;
import stock.Item;
import stock.Stock;
import stock.StockException;
import stock.Store;

/**
 * 
 * This class is used for reading .csv formatted files in
 * ways that are necessary for the project. It consists
 * of a few specifically designed methods that achieve this.
 * 
 * @author Joshua Lowe
 * 
 */
public class ReadCSV {

	/**
	 * Initialises a list of Item objects from the imported item properties file
	 * @param fileName The name of the file
	 * @return A list of items retrieved from the file
	 * @throws IOException If the file does not exist
	 * @throws CSVFormatException If the file is in the incorrect format
	 * @throws StockException If an item cannot be created
	 */
	public static List<Item> initialiseItems(String fileName) throws IOException, StockException, CSVFormatException {
		// Initialise a list of Item objects and retrieve the File Path
		List<Item> items = new ArrayList<>();
		Path filePath = Paths.get(fileName);
		
		// Try to create a BufferedReader from the file
		BufferedReader buff = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII);
			// Read the first line
			String line = buff.readLine();
			
			// While the whole document file hasn't been read
			while (line!=null) {
				// Split the line into an array from the ,'s
				String[] values = line.split(",");
				
				// Create the Item object and add it to the list
				Item item = createItem(values);
				items.add(item);
				
				// Read the next line
				line = buff.readLine();
			}
		
		
		
		// Return the list of items retrieved
		return items;
	}
	
	
	/**
	 * Creates an Item object from a given String array of data
	 * @param data The array of data retrieved from the .csv file
	 * @return The Item object created
	 * @throws StockException If the Item object cannot be created
	 * @throws CSVFormatException If the data is not in the correct format
	 */
	private static Item createItem(String[] data) throws StockException, CSVFormatException {
		
		// Initialise variables
		String name;
		int cost;
		int price;
		int reorderPoint;
		int reorderQuantity;
		Integer temp;
		
		// Try to assign the variables to the given data
		
			name = data[0];
			cost = Integer.parseInt(data[1]);
			price = Integer.parseInt(data[2]);
			reorderPoint = Integer.parseInt(data[3]);
			reorderQuantity = Integer.parseInt(data[4]);
		
		// Throw a CSVFormatException if it's in the wrong format
		
		
		// Try to assign temperature if it exists, if not, assign null
		try {
			temp = Integer.parseInt(data[5]);
		} catch(ArrayIndexOutOfBoundsException e) {
			temp = null;
		}
		
		// Try to create the item object and return it
		
			Item item = new Item(name, cost, price, reorderPoint, reorderQuantity, temp);
			return item;
		
		// Throw a StockException if the item object cannot be created
		
	}
	
	
	/**
	 * Creates a Stock object from the imported sales log file
	 * @param fileName The name of the file
	 * @return A Stock object detailing the items and quantities present in the file
	 * @throws IOException If the file does not exist
	 * @throws StockException If an invalid quantity is added
	 * @throws CSVFormatException 
	 * @throws NumberFormatException 
	 */
	public static Stock readSalesLog(String fileName) throws IOException, StockException, NumberFormatException, CSVFormatException {
		// Initialise a Stock object for representing the store's sales and get the file path
		Stock sales = new Stock();
		Path filePath = Paths.get(fileName);
		
		// Try to create a BufferedReader from the file
		BufferedReader buff = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII);
			// Read the first line
			String line = buff.readLine();
			
			// While the whole document file hasn't been read
			while (line!=null) {
				// Split the line into an array from the ,'s
				String[] values = line.split(",");
				
				// Initialise quantity integer
				int quantity;
				
				for (Item i : Store.getInstance().getItemList()) {
					// If the string from csv is equal to a string from the inventory's item name
					if (values[0].equals(i.getName())) {
						quantity = Integer.parseInt(values[1]);
						// Add the item and quantity to the sales log
						sales.addQuantity(i, quantity);
					} 
				}
				// Read the next line
				line = buff.readLine();

			}
		
		// Return the sales log Stock object
		return sales;
	}
	/* Reads a "manifest" text file and parses it into a manifest object
	 * @param fileName Name of the file
	 * @return The manifest object created
	 * @throws IOException When the file doesn't exist
	 * @throws StockException If the item object cannot be created
	 */
	public static Manifest readManifest(String fileName) throws IOException, StockException, DeliveryException, NumberFormatException, CSVFormatException {
		// Initialise variables
		Stock stock = new Stock();
		Path filePath = Paths.get(fileName);
		Manifest manifest = new Manifest();

		// Try to create a BufferedReader from the file
		BufferedReader buff = Files.newBufferedReader(filePath, StandardCharsets.US_ASCII); 
			// Read the first line
			String line = buff.readLine();
			boolean finished = false;
			
			// Truck type buffer
			boolean willTruckBeCold = false; // doesn't matter what value it starts at
			
			// For checking if in correct format
			int itr = 0;
			
			// While the whole document file hasn't been read
			while (line!=null && finished!=true) {
				// If the line represents a truck declaration or the document has no more lines
				if (line.charAt(0) == '>') {
					// Create the truck if previously found
					if (itr != 0) {
						// If the truck is cold
						if (willTruckBeCold) {
							// Find out the item with the lowest required temperature and set the variable
							Integer lowestTemp = null;
							Iterator<Entry<Item, Integer>> tempCheck = stock.getStock().entrySet().iterator();
							while (tempCheck.hasNext()) {
								Entry<Item, Integer> storePair = tempCheck.next();
								Item item = storePair.getKey();
								if (item.getTemp() != null) {
									if (lowestTemp == null) {
										lowestTemp = item.getTemp();
									} else if (item.getTemp() < lowestTemp) {
										lowestTemp = item.getTemp();
									}
								}
							}
							
							// Create the truck
							Truck truck = new ColdTruck(lowestTemp);
							
							// Add the items to the truck
						    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
						    while (stockIterator.hasNext()) {
						        Entry<Item, Integer> stockPair = stockIterator.next();
						        Item item = stockPair.getKey();
						        int stockQuantity = stockPair.getValue();
						        
						        truck.addCargo(item, stockQuantity);
						    }
						    
						    // Add the truck to the manifest
							manifest.addTruck(truck);
						} else {
							// Create the truck
							Truck truck = new RegTruck();
							
							// Add the items to the truck
						    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
						    while (stockIterator.hasNext()) {
						        Entry<Item, Integer> stockPair = stockIterator.next();
						        Item item = stockPair.getKey();
						        int stockQuantity = stockPair.getValue();
						        
						        truck.addCargo(item, stockQuantity);
						    }
						    
						    // Add the truck to the manifest
						    manifest.addTruck(truck);
						}
					}
					
					// Initialise the stock buffer
					stock = new Stock();
					
					// Find out which truck it is
					if (line.equals(">Ordinary")) {
						willTruckBeCold = false;
					} else if (line.equals(">Refrigerated")) {
						willTruckBeCold = true;
					}
					
					// Increase the iteration counter
					itr++;
					
					System.out.println(">" + line);
					
					// Read the next line
					line = buff.readLine();
					
				} else if (itr == 0) {
					// Throw exception because each manifest should begin with a truck declaration
					throw new CSVFormatException();
					
				} else if ("".equals(line)) {
					/*// Split the line into an array from the ,'s
					String[] values = line.split(",");
					System.out.println("null" + line);
					
					// Initialise quantity integer
					int quantity;
					
					for (Item i : Store.getInstance().getItemList()) {
						// If the string from csv is equal to a string from the inventory's item name
						if (values[0].equals(i.getName())) {
							quantity = Integer.parseInt(values[1]);
							// Add the item and quantity to the cargo
							stock.addQuantity(i, quantity);
						} 
					}
					*/
					// If the truck is cold
					if (willTruckBeCold) {
						// Find out the item with the lowest required temperature and set the variable
						Integer lowestTemp = null;
						Iterator<Entry<Item, Integer>> tempCheck = stock.getStock().entrySet().iterator();
						while (tempCheck.hasNext()) {
							Entry<Item, Integer> storePair = tempCheck.next();
							Item item = storePair.getKey();
							if (item.getTemp() != null) {
								if (lowestTemp == null) {
									lowestTemp = item.getTemp();
								} else if (item.getTemp() < lowestTemp) {
									lowestTemp = item.getTemp();
								}
							}
						}
						
						// Create the truck
						Truck truck = new ColdTruck(lowestTemp);
						
						// Add the items to the truck
					    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
					    while (stockIterator.hasNext()) {
					        Entry<Item, Integer> stockPair = stockIterator.next();
					        Item item = stockPair.getKey();
					        int stockQuantity = stockPair.getValue();
					        
					        truck.addCargo(item, stockQuantity);
					    }
					    
					    // Add the truck to the manifest
						manifest.addTruck(truck);
					} else {
						// Create the truck
						Truck truck = new RegTruck();
						
						// Add the items to the truck
					    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
					    while (stockIterator.hasNext()) {
					        Entry<Item, Integer> stockPair = stockIterator.next();
					        Item item = stockPair.getKey();
					        int stockQuantity = stockPair.getValue();
					        
					        truck.addCargo(item, stockQuantity);
					    }
					    
					    // Add the truck to the manifest
					    manifest.addTruck(truck);
					}					
					
					// Increase the iteration counter
					itr++;
					
					// Read the next line
					finished = true;
					line = buff.readLine();
				
					
				} else if (!"".equals(line)) {
					// Split the line into an array from the ,'s
					String[] values = line.split(",");
					
					// Initialise quantity integer
					int quantity;
					
					for (Item i : Store.getInstance().getItemList()) {
						// If the string from csv is equal to a string from the inventory's item name
						if (values[0].equals(i.getName())) {
							quantity = Integer.parseInt(values[1]);
							// Add the item and quantity to the cargo
							stock.addQuantity(i, quantity);
						} 
					}
					
					System.out.println("!null\n" + line);
					
					// Increase the iteration counter
					itr++;
					
					// Read the next line
					line = buff.readLine();
				} else {
					throw new CSVFormatException();
				}
			}
			
			// If the truck is cold
			if (willTruckBeCold) {
				// Find out the item with the lowest required temperature and set the variable
				Integer lowestTemp = null;
				Iterator<Entry<Item, Integer>> tempCheck = stock.getStock().entrySet().iterator();
				while (tempCheck.hasNext()) {
					Entry<Item, Integer> storePair = tempCheck.next();
					Item item = storePair.getKey();
					if (item.getTemp() != null) {
						if (lowestTemp == null) {
							lowestTemp = item.getTemp();
						} else if (item.getTemp() < lowestTemp) {
							lowestTemp = item.getTemp();
						}
					}
				}
				
				// Create the truck
				Truck truck = new ColdTruck(lowestTemp);
				
				// Add the items to the truck
			    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
			    while (stockIterator.hasNext()) {
			        Entry<Item, Integer> stockPair = stockIterator.next();
			        Item item = stockPair.getKey();
			        int stockQuantity = stockPair.getValue();
			        
			        truck.addCargo(item, stockQuantity);
			    }
			    
			    // Add the truck to the manifest
				manifest.addTruck(truck);
			} else {
				// Create the truck
				Truck truck = new RegTruck();
				
				// Add the items to the truck
			    Iterator<Entry<Item, Integer>> stockIterator = stock.getStock().entrySet().iterator();
			    while (stockIterator.hasNext()) {
			        Entry<Item, Integer> stockPair = stockIterator.next();
			        Item item = stockPair.getKey();
			        int stockQuantity = stockPair.getValue();
			        
			        truck.addCargo(item, stockQuantity);
			    }
			    
			    // Add the truck to the manifest
			    manifest.addTruck(truck);
			}					
			
		
		
		// Return the manifest
		return manifest;
	}
	

}
