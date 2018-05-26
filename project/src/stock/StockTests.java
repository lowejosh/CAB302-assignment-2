package stock;

/**
 * @author Joshua Lowe
 *
 */

import static org.junit.Assert.*;

import csv.ReadCSV;

import java.util.HashMap;
import java.util.List;
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
	 * ---------- END HELPER METHODS ----------
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
	public void testItemConstruction() throws StockException {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a cooled Item object. 
	 */
	@Test 
	public void testCooledItemConstruction() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative cost. 
	 */
	@Test 
	(expected = StockException.class) public void testNegativeCostItemConstruction() throws StockException {
		item = new Item("Biscuit", -4, 6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative price. 
	 */
	@Test 
	(expected = StockException.class) public void testNegativePriceItemConstruction() throws StockException {
		item = new Item("Biscuit", 4, -6, 300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative reorder point. 
	 */
	@Test 
	(expected = StockException.class) public void testNegativeReorderPointItemConstruction() throws StockException {
		item = new Item("Biscuit", 4, 6, -300, 700, null);
	}
	
	
	/*
	 * Test : Constructing a basic Item object with a negative reorder quantity. 
	 */
	@Test 
	(expected = StockException.class) public void testNegativeReorderQuantityItemConstruction() throws StockException {
		item = new Item("Biscuit", 4, 6, 300, -700, null);
	}
	
	
	/*
	 * Test : Get the Item Name
	 */
	@Test 
	public void testItemName() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getName(), "Ice-Cream");
	}
	
	
	/*
	 * Test : Get the Item Cost
	 */
	@Test 
	public void testItemCost() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getCost(), 6);
	}
	
	
	/*
	 * Test 4: Get the Item Price
	 */
	@Test 
	public void testItemPrice() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getPrice(), 10);
	}
	
	
	/*
	 * Test : Get the Item Reorder Point
	 */
	@Test 
	public void testItemReorderPoint() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderPoint(), 200);
	}
	
	
	/*
	 * Test : Get the Item Reorder Quantity
	 */
	@Test 
	public void testItemReorderQuantity() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderQuantity(), 500);
	}
	
	
	/*
	 * Test : Get the cooled item required temperature
	 */
	@Test 
	public void testCooledItemRequiredTemp() throws StockException {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals((int) item.getTemp(), -20);
	}
	
	
	/*
	 * Test : Get the dry item required temperature
	 */
	@Test 
	public void testDryItemRequiredTemp() throws StockException {
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
	public void testAddQuantityToItem() throws StockException {
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
	public void testRemoveQuantityFromItem() throws StockException {
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
	(expected = StockException.class) public void testAddNegativeQuantityToItem() throws StockException {
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
	(expected = StockException.class) public void testRemoveNegativeQuantityFromItem() throws StockException {
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
	public void testItemStockNeedsReorder() throws StockException {
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
	public void testItemStockDoesntNeedReorder() throws StockException {
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
	 * Test : Grabbing the basic store instance 
	 */
	@Test 
	public void testStoreConstruction() {
		// Retrieve the store instance
		Store store = Store.getInstance();
	}
	
	
	/*
	 * Test : Get the Starting Store Capital
	 */
	@Test
	public void testStoreCapital() {
		
		double startingCapital = 100000.0;
		
		// Retrieve the store instance
		Store store = Store.getInstance();
		
		// Test the get method
		assertEquals(store.getCapital(), startingCapital, 0.01);
	}
	
	
	/*
	 * Test : Get the Store Name
	 * TODO - DELETE? MIGHT NOT BE NEEDED
	 * 
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
	*/
	
	
	/*
	 * Test : Get the Store Inventory
	 * TODO - i'm not sure if you've decided how to do it but
	 * we could initialize the starting inventory of the store
	 * from the store class by importing the item properties
	 * and setting every item to 0 quantity - example below
	 */
	@Test
	public void testStoreInventory() {
		
		Stock startingInventory;
		List<Item> itemList = ReadCSV.initialiseItems("item_properties.txt");
		for (Item i : itemList) {
			startingInventory.addQuantity(i, 0);
		}
		
		// Retrieve the store instance
		Store store = Store.getInstance();
		
		// Test the get method
		assertEquals(store.getInventory(), startingInventory);
	}
	
	
	/*
	 * Test : Modify Store Capital by adding
	 */
	@Test
	public void ModifyStoreCapitalByAdding() {
		
		double startingCapital = 100000.0;
		
		// Retrieve the store instance
		Store store = Store.getInstance();
		
		// Add to the capital
		double amountToAdd = randCapital();
		store.modifyCapital(amountToAdd);
		
		// Test the get method
		assertEquals(store.getCapital(), startingCapital + amountToAdd, 0.01);
	}
	
	
	/*
	 * Test : Modify Store Capital by removing
	 */
	@Test
	public void ModifyStoreCapitalByRemoving() {
		
		double startingCapital = 100000.0;
		
		// Retrieve the store instance
		Store store = Store.getInstance();
		
		// Remove from the capital
		double amountToRemove = randCapital();
		store.modifyCapital(-amountToRemove);
		
		// Test the get method
		assertEquals(store.getCapital(), startingCapital - amountToRemove, 0.01);
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
	public void getTotalPrice() throws StockException {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		// Find the proper output
		int totalPrice = item.getPrice() * quantity;
		
		assertEquals(stock.getTotalPrice(), totalPrice);
		
	}
	
	
	/*
	 * Test : Get the Total Cost
	 * TODO - test for more than one item
	 */
	@Test
	public void getTotalCost() throws StockException {
		// Create a sample stock object
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		stock = new Stock();
		
		// Create random quantity and add it
		int quantity = randQuantity();
		stock.addQuantity(item, quantity);
		
		// Find the proper output
		int totalCost = item.getCost() * quantity;
		
		assertEquals(stock.getTotalCost(), totalCost);
		
	}
	
	
	/*
	 *	----------- END SALES LOG TESTS ---------
	 */
	
}
