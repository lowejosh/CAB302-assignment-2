package stock;

/**
 * @author Joshua Lowe
 *
 */

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class StockTests {
	
	/*
	 * Test 0: Declaring Item object.
	 */
	Item item;

	// Clear the item object before every test.
	@Before
	public void setUpItem() {
		item = null;
	}
	
	
	/*
	 * Test 1: Constructing a basic Item object. 
	 */
	@Test 
	public void testItemConstruction() {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
	}
	
	
	/*
	 * Test 2: Get the Item Name
	 */
	@Test 
	public void testItemName() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getName(), "Ice-Cream");
	}
	
	
	/*
	 * Test 3: Get the Item Cost
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
	 * Test 5: Get the Item Reorder Point
	 */
	@Test 
	public void testItemReorderPoint() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderPoint(), 200);
	}
	
	
	/*
	 * Test 6: Get the Item Reorder Quantity
	 */
	@Test 
	public void testItemReorderQuantity() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getReorderQuantity(), 500);
	}
	
	
	/*
	 * Test 7: Get the cooled item temperature
	 */
	@Test 
	public void testCooledItemTemp() {
		item = new Item("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getTemp(), -20);
	}
	
	
	/*
	 * Test 8: Get the dry item temperature
	 * TODO - IMPLEMENT CSV READING AND TEST FROM THAT
	 */
	@Test 
	public void testDryItemTemp() {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
		assertNull(item.getTemp());
	}
}
