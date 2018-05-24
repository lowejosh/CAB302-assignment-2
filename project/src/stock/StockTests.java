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
	
	// Helper vars
	private static Random random = new Random();
	
	// Returns a random int from 1 to 200 for quantity tests
	private static int randQuantity() {
		return (1 + 199 * random.nextInt());
	}
	
	// Returns a random double from 1000 to 500000 for capital tests
	private static double randCapital() {
		return (1000 + 499000 * random.nextDouble());
	}
	
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
		stock = new Stock();
	}
	

	
	/*
	 * Test : Add quantity to an Item's existing stock
	 * Test : Get quantity for an Item
	 */
	@Test
	public void testAddQuantityToItem() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		// Test the get method
		assertEquals(stock.getQuantity(item), quantity);
	}
	
	
	/*
	 * Test : Remove quantity from an Item's existing stock
	 */
	@Test
	public void testRemoveQuantityFromItem() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Add 200 to the stock
		int quantity = 200;
		stock.addQuantity(item, quantity);
		
		// Create random quantity and remove it
		int amountToRemove = randQuantity();
		stock.removeQuantity(item, amountToRemove);
		
		// Test the get method
		assertEquals(stock.getQuantity(item), quantity - amountToRemove);
	}
	
	
	/*
	 * Test : Add a negative quantity to an Item's existing stock
	 */
	@Test
	(expected = StockException) public void testAddNegativeQuantityToItem() {
		// Create a sample stock and item object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random negative quantity and add it
		int amountToAdd = randQuantity();
		amountToAdd = -amountToAdd; 
		stock.addQuantity(item, amountToAdd);
	}
	
	
	/*
	 * Test : Remove a negative quantity from an Item's existing stock
	 */
	@Test
	(expected = StockException) public void testRemoveNegativeQuantityFromItem() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Add 200 to the stock
		int quantity = 200;
		stock.addQuantity(item, quantity);
		
		// Create random negative quantity and remove it
		int amountToRemove = randQuantity();
		amountToRemove = -amountToRemove; 
		stock.removeQuantity(item, amountToRemove);
	}
	
	
	/*
	 * Test : Necessary reorder for item stock
	 */
	@Test
	public void testItemStockNeedsReorder() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		int quantity = 1; // This is below the reorder point - requiring true to be returned
		// Create a sample stock object
		stock = new Stock();
		// Add the quantity
		stock.addQuantity(item, quantity);
		
		// Test the method returns true
		assertTrue(stock.reorderRequired(item));
	}
	
	
	/*
	 * Test : Unnecessary reorder for item stock
	 */
	@Test
	public void testItemStockDoesntNeedReorder() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		int quantity = 1000; // This is above the reorder point - requiring false to be returned
		// Create a sample stock object
		stock = new Stock();
		// Add the quantity
		stock.addQuantity(item, quantity);
		
		// Test the method returns false
		assertFalse(stock.reorderRequired(item));
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
		// Create the stock
		stock = new Stock();
		
		// Construct the Store object
		double capital = randCapital();
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
	}
	
	
	/*
	 * Test : Get the Store Capital
	 */
	@Test
	public void testStoreCapital() {
		// Create the stock
		stock = new Stock();
		
		// Construct the Store object
		double capital = randCapital();
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
		// Create the stock
		stock = new Stock();
		
		// Construct the Store object
		double capital = randCapital();
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
		
		// Test the get method
		assertEquals(store.getName(), storeName);
	}
	
	
	/*
	 * Test : Modify Store Capital by adding
	 */
	@Test
	public void ModifyStoreCapitalByAdding() {
		// Create the stock
		stock = new Stock();
		
		// Construct the Store object
		double capital = randCapital();
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
		
		// Add to the capital
		double amountToAdd = randCapital();
		store.modifyCapital(amountToAdd);
		
		// Test the get method
		assertEquals(store.getCapital(), capital + amountToAdd);
	}
	
	
	/*
	 * Test : Modify Store Capital by removing
	 */
	@Test
	public void ModifyStoreCapitalByRemoving() {
		// Create the stock
		stock = new Stock();
		
		// Construct the Store object
		double capital = randCapital();
		String storeName = "Capalaba SuperMart"; // TODO - ADD RANDOM
		store = new Store(capital, stock, storeName);
		
		// Remove from the capital
		double amountToRemove = randCapital();
		store.modifyCapital(-amountToRemove);
		
		// Test the get method
		assertEquals(store.getCapital(), capital - amountToRemove);
	}
	
	
	/*
	 *	----------- END STORE TESTS ---------
	 */
	
	
	
	
	/*
	 *	----------- START SALES LOG TESTS ---------
	 */
	
	/*
	 * Test : Get the Total Price
	 * TODO - test for more than one item
	 */
	@Test
	public void getTotalPrice() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		// Find the proper output
		int totalPrice = item.getPrice() * quantity;
		
		AssertEquals(stock.getTotalPrice(), totalPrice);
		
	}
	
	
	/*
	 * Test : Get the Total Cost
	 * TODO - test for more than one item
	 */
	@Test
	public void getTotalCost() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		// Find the proper output
		int totalCost = item.getCost() * quantity;
		
		AssertEquals(stock.getTotalCost(), totalCost);
		
	}
	
	/* 	TODO REMOVE ---- MIGHT BE UNNECESSARY, IMPLEMENTING IN TRUCK'S GETCARGO	
	 *
	 * Test : Get the Total Quantity
	 * TODO - test for more than one item
	 *
	@Test
	public void getTotalQuantity() {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		AssertEquals(stock.getTotalQuantity(), quantity);
		
	}
	*/
	
	/*
	 *	----------- END SALES LOG TESTS ---------
	 */
	
}
