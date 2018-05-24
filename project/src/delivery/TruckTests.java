package delivery;
import stock.Item;
import stock.Stock;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Ashley Husband
 *
 */

public class TruckTests {
	
	private Truck coldTruck;
	private Truck regTruck;
	private Manifest manifest;
	
	private Item iceCream;
	private Item biscuit;
	
	//Setting up the items used in the tests
	@Before 
	public void setupItems() {
		biscuit = new Item("biscuit", 4, 6, 300, 700);
		iceCream = new Item("ice-cream", 6, 10, 200, 500, -20);
	}
	
	/*
	 * 	---------- START TRUCK TESTS ----------
	 */
	
	/*
	 * Test 1: Initialising the two truck types and checking their default cargo values
	 */
	@Before @Test 
	public void truckTest() {
		coldTruck = new ColdTruck(-10); //Cold trucks let you assign a temperature
		regTruck = new RegTruck();
		
		assertEquals(coldTruck.getCargo(), 0);
		assertEquals(regTruck.getCargo(), 0);
	}
	
	/*
	 * Test 2: Adding cargo to a truck
	 */
	@Test 
	public void addCargoTest(){
		coldTruck.addCargo(iceCream, 1);
		assertEquals(coldTruck.getCargo(), 1);
		coldTruck.addCargo(biscuit, 1);
		assertEquals(coldTruck.getCargo(), 2);
		
		regTruck.addCargo(biscuit, 1);
		assertEquals(regTruck.getCargo(), 1);
	}
	
	/*
	 * Test 3: Checking the costs of the trucks
	 */
	@Test
	public void getCostTest() {
		regTruck.addCargo(biscuit, 323);
		assertEquals(regTruck.getCargo(), 323);
		
		double exactColdTemp = 900 + (200 * Math.pow(0.7, -10 / 5));
		
		assertEquals(coldTruck.getCost(), exactColdTemp, 1e-15);
		assertEquals(regTruck.getCost(), 830.75, 1e-15);
		
		//float coldTruckCost = 900 + 200*0.7^coldTruck.getTemp()/5;
		//float regTruckCost = 750 + 0.25*regTruck.getCargo();
		//assertEquals(coldTruck.getCost(), coldTruckCost);
		//assertEquals(regTruck.getCost(), regTruckCost);
		
	}
	
	/*
	 * Test 4: Adding a cold item to a regular truck - Throws Exception
	 */
	@Test (expected = DeliveryException) 
	public void addColdCargoToRegTruck(){
		regTruck.addCargo(iceCream, 1);
	}
	
	/*
	 * Test 5: Adding too much cargo to a regular truck - Throws Exception
	 */
	@Test (expected = DeliveryException) 
	public void addTooMuchCargo(){
		regTruck.addCargo(biscuit, 1000); //Thassa lotta bickies, try dunkin that in ya bushells
		assertEquals(coldTruck.getCargo(), 1000);
		
		regTruck.addCargo(biscuit, 1);
	}
	
	/*
	 * Test 6: Adding too much cargo to a cold truck - Throws Exception
	 */
	@Test (expected = DeliveryException) 
	public void addTooMuchColdCargo(){
		coldTruck.addCargo(iceCream, 800);
		assertEquals(coldTruck.getCargo(), 800);
	
		coldTruck.addCargo(iceCream, 1);
		}
	
	/*
	 * Test 7: Setting the temperature of a cold truck too low - Throws Exception
	 */
	@Test (expected = DeliveryException) 
	public void tempTooLow(){
		ColdTruck brokenTruck = new ColdTruck(-50);
	}	

	/*
	 * Test 8: Setting the temperature of a cold truck too high - Throws Exception
	 */
	@Test (expected = DeliveryException) 
	public void tempTooHigh(){
		ColdTruck brokenTruck = new ColdTruck(50);
	}
	
	/*
	 * 	---------- END TRUCK TESTS ----------
	 */
	
	/*
	 * 	---------- START MANIFEST TESTS ----------
	 */
	
	@Test 
	public void testManifestConstruction() {
		manifest = new Manifest();
	}
	
}
