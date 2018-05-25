package csv;

/**
 * @author Joshua Lowe
 * 
 */

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

public class ReadCSV {

	public static List<Item> initialiseItems(String fileName) throws IOException {
		List<Item> items = new ArrayList<>();
		Path pathToFile = Paths.get(fileName);
		
		try (BufferedReader br = Files.newBufferedReader(
				pathToFile, StandardCharsets.US_ASCII)) {
			
			String line = br.readLine();
			
			while (line!=null) {
				String[] attributes = line.split(",");
				
				Item item = createItem(attributes);
				
				items.add(item);
				line = br.readLine();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	private static Item createItem(String[] data) throws StockException {
		String name = data[0];
		int cost = Integer.parseInt(data[1]);
		int price = Integer.parseInt(data[2]);
		int reorderPoint = Integer.parseInt(data[3]);
		int reorderQuantity = Integer.parseInt(data[4]);
		
		try {
			Item item = new Item(name, cost, price, reorderPoint, reorderQuantity);
			return item;
		} catch (Exception e) {
			throw new StockException();
		}
	}

}
