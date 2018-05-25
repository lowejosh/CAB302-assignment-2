package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import stock.Item;
import stock.StockException;

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
	 * Initialises a list of Item objects from the imported .csv file
	 * @param fileName The name of the file
	 * @return A list of items retrieved from the file
	 * @throws IOException If the file does not exist
	 */
	public static List<Item> initialiseItems(String fileName) throws IOException {
		// Initialise a list of Item objects and retrieve the File Path
		List<Item> items = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);
		
		// Try to create a BufferedReader from the file
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			// Read the first line
			String line = br.readLine();
			
			// While the whole document file hasn't been read
			while (line!=null) {
				// Split the line into an array from the ,'s
				String[] attributes = line.split(",");
				
				// Create the Item object and add it to the list
				Item item = createItem(attributes);
				items.add(item);
				
				// Read the next line
				line = br.readLine();
			}
		}
		// Catch exception
		catch (Exception e) {
			e.printStackTrace();
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
		try {
			name = data[0];
			cost = Integer.parseInt(data[1]);
			price = Integer.parseInt(data[2]);
			reorderPoint = Integer.parseInt(data[3]);
			reorderQuantity = Integer.parseInt(data[4]);
		} 
		// Throw a CSVFormatException if it's in the wrong format
		catch (ArrayIndexOutOfBoundsException e) {
			throw new CSVFormatException();
		}
		
		// Try to assign temperature if it exists, if not, assign null
		try {
			temp = Integer.parseInt(data[5]);
		} catch(ArrayIndexOutOfBoundsException e) {
			temp = null;
		}
		
		// Try to create the item object
		try {
			Item item = new Item(name, cost, price, reorderPoint, reorderQuantity, temp);
			return item;
		} 
		// Throw a StockException if the item object cannot be created
		catch (Exception e) {
			throw new StockException();
		}
	}

}
