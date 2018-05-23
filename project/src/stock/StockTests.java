package stock;

/**
 * @author Joshua Lowe
 *
 */

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class StockTests {
	
	/*
	 * ---------- START HELPER METHODS ----------
	 */

	// PLACEHOLDER
	
	/*
	 * ---------- START HELPER METHODS ----------
	 */
	
	
	
	
	/*
	 * Test 0: Declaring test objects.
	 */
	Item item;
	Stock stock;
	Store store;

	// Clear the test objects before every test.
	@Before
	public void setUpItem() {
		item = null;
		store = null;
	}
	
	
	
	
	/*
	 * 	---------- START ITEM TESTS ----------
	 */
	
	/*
	 * Test : Constructing a dry Item object. 
	 */
	@Test 
	public void testItemConstruction() {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a cooled Item object. 
	 */
	@Test 
	public void testCooledItemConstruction() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative cost. 
	 */
	@Test 
	(expected = StockException) public void testNegativeCostItemConstruction() {
		item = new Item("Biscuit", -4, 6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative price. 
	 */
	@Test 
	(expected = StockException) public void testNegativePriceItemConstruction() {
		item = new Item("Biscuit", 4, -6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative reorder point. 
	 */
	@Test 
	(expected = StockException) public void testNegativeReorderPointItemConstruction() {
		item = new Item("Biscuit", 4, 6, -300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative reorder quantity. 
	 */
	@Test 
	(expected = StockException) public void testNegativeReorderQuantityItemConstruction() {
		item = new Item("Biscuit", 4, 6, 300, -700, null);
	}
	
	
	/*
	 * Test : Get the Item Name
	 */
	@Test 
	public void testItemName() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getName(), "Ice-Cream");
	}
	
	
	/*
	 * Test : Get the Item Cost
	 */
	@Test 
	public void testItemCost() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getCost(), 6);
	}
	
	
	/*
	 * Test 4: Get the Item Price
	 */
	@Test 
	public void testItemPrice() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getPrice(), 10);
	}
	
	
	/*
	 * Test : Get the Item Reorder Point
	 */
	@Test 
	public void testItemReorderPoint() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderPoint(), 200);
	}
	
	
	/*
	 * Test : Get the Item Reorder Quantity
	 */
	@Test 
	public void testItemReorderQuantity() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderQuantity(), 500);
	}
	
	
	/*
	 * Test : Get the cooled item required temperature
	 */
	@Test 
	public void testCooledItemRequiredTemp() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals((int) item.getTemp(), -20);
	}
	
	
	/*
	 * Test : Get the dry item required temperature
	 * TODO - IMPLEMENT CSV READING AND TEST FROM THAT
	 */
	@Test 
	public void testDryItemRequiredTemp() {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
		assertNull(item.getTemp());
	}
	
	/*
	 *	---------- END ITEM TESTS ----------
	 */
	
	
	
	
	/*
	 *	----------- START STOCK TESTS ---------
	 */
	
	/*
	 * Test : Constructing a basic Stock object. 
	 */
	@Test 
	public void testStockConstruction() {
		// Create the hashmap for <item, stock>
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, 50);
		
		// Construct the Stock object
		stock = new Stock(map);
	}
	
	
	/*
	 * Test : Creating a stock with negative quantity
	 */
	@Test
	(expected = StockException) public void testStockNegativeQuantity() {
		// Create a sample stock HashMap
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, -50);
		
		// Construct the Stock object
		stock = new Stock(map);
	}
	
	
	/*
	 * Test : Get the Stock HashMap
	 */
	@Test
	public void testStock() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, 50);
		stock = new Stock(map);
		
		// Test the get method
		assertArrayEquals(stock.getStock(), map);
	}
	
	/*
	 *	----------- END STOCK TESTS ---------
	 */
	
	
	
	
	/*
	 *	----------- START STORE TESTS ---------
	 */
	
	/*
	 * Test : Constructing a basic Store object. 
	 */
	@Test 
	public void testStoreConstruction() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, 50);
		stock = new Stock(map);
		
		// Construct the Store object
		double capital = 100000.0; // TODO - ADD RANDOM
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
	}
	
	
	/*
	 * Test : Get the Store Capital
	 */
	@Test
	public void testStoreCapital() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, 50);
		stock = new Stock(map);
		
		// Construct the Store object
		double capital = 100000.0; // TODO - ADD RANDOM
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
		
		// Test the get method
		assertEquals(store.getCapital(), capital);
	}
	
	
	/*
	 * Test : Get the Store Name
	 */
	@Test
	public void testStoreName() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		map.put(item, 50);
		stock = new Stock(map);
		
		// Construct the Store object
		double capital = 100000.0; // TODO - ADD RANDOM
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
		
		// Test the get method
		assertEquals(store.getName(), storeName);
	}
	
	
	/*
	 *	----------- END STORE TESTS ---------
	 */
	
}
