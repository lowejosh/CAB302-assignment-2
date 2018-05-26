package csv;
/**
 * TEMPORARY
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import stock.Item;
import stock.Stock;
import stock.StockException;

public class ReadCSVTesting {

	@Test
	public void testInitialiseItems() throws IOException {
		List<Item> items = ReadCSV.initialiseItems("item_properties.txt");
		for (Item i : items) {
			System.out.println(i.getTemp());
		}
	}
	
	@Test
	public void testReadSalesLog() throws IOException, StockException {
		Stock sales = ReadCSV.readSalesLog("sales_log_0.txt");
		
		// print sales items
	    Iterator<Entry<Item, Integer>> itr = sales.getStock().entrySet().iterator();
	    while (itr.hasNext()) {
	        Entry<Item, Integer> pair = itr.next();
	        Item i = pair.getKey();
	        
			System.out.println(sales.getQuantity(i));
	       
	        itr.remove(); // avoids a ConcurrentModificationException
	    }
	}

}
