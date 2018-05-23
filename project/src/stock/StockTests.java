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
	 * Test 2: Get the cooled item temperature
	 */
	@Test 
	public void testTemp() {
		coldItem = new ColdItem("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(item.getTemp(), -20);
	}
	
	
	/*
	 * Test 3: Get the dry item temperature
	 * TODO - IMPLEMENT CSV READING AND TEST FROM THAT
	 */
	@Test 
	public void testTemp() {
		item = new Item("Biscuit", 4, 6, 300, 700, null);
		assertNull(item.getTemp());
	}
	
}
