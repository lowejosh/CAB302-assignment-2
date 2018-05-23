package stock;

/**
 * @author Joshua Lowe
 *
 */

import static org.junit.Assert.assertEquals;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import junit.Uber.Uber;

public class StockTests {
	
	
	/*
	 * Test 0: Declaring Item objects.
	 */
	Item item;
	ColdItem coldItem;
	
	// Clear the item objects before every test.
	@Before
	public void setUpItem() {
		item = null;
		coldItem = null;
	}
	
	
	/*
	 * Test 1: Constructing a basic Item object. 
	 */
	@Test 
	public void testItemConstruction() {
		item = new Item("Biscuit", 4, 6, 300, 700);
	}
	
	
	/*
	 * Test 2: Constructing a basic ColdItem object. 
	 */
	@Test 
	public void testColdItemConstruction() {
		coldItem = new ColdItem("Ice-Cream", 6, 10, 200, 500, -20);
	}
	
	
	/*
	 * Test 3: Get the item temperature
	 */
	@Test 
	public void testTemp() {
		coldItem = new ColdItem("Ice-Cream", 6, 10, 200, 500, -20);
		assertEquals(coldItem.getTemp(), -20);
	}
}
