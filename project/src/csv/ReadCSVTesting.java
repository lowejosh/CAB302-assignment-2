package csv;
/**
 * TEMPORARY
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import stock.Item;

public class ReadCSVTesting {

	@Test
	public void test() throws IOException {
		List<Item> items = ReadCSV.initialiseItems("item_properties.txt");
		for (Item i : items) {
			System.out.println(i);
		}
	}

}
