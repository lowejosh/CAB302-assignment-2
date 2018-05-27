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

import delivery.DeliveryException;
import delivery.Manifest;
import stock.Item;
import stock.Stock;
import stock.StockException;

public class ReadCSVTesting {

	@Test
	public void testInitialiseItems() throws IOException, StockException, CSVFormatException {
		List<Item> items = ReadCSV.initialiseItems("item_properties.txt");
	}
	
	@Test
	public void testReadSalesLog() throws IOException, StockException, NumberFormatException, CSVFormatException {
		Stock sales = ReadCSV.readSalesLog("sales_log_0.txt");
	}

	//temp
	@Test
	public void testReadManifest() throws NumberFormatException, IOException, StockException, DeliveryException, CSVFormatException {
		Manifest manifest = ReadCSV.readManifest("manifest.txt");
	}

}
