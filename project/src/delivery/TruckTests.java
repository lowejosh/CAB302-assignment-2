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
	
	private Item iceCream;
	private Item biscuit;
	
	//Setting up the items used in the tests
	@Before public void setupItems() {
		biscuit = new Item("biscuit", 4, 6, 300, 700);
		iceCream = new Item("ice-cream", 6, 10, 200, 500, -20);
	}
	
	//Testing the initialisation of the two truck types by checking its default cargo value
	@Before @Test public void truckTest() {
		coldTruck = new ColdTruck(-10); //Cold trucks let you assign a temperature
		regTruck = new RegTruck();
		
		assertEquals(coldTruck.getCargo(), 0);
		assertEquals(regTruck.getCargo(), 0);
	}
	
	//Testing adding cargo to a truck
	@Test public void addCargoTest(){
		coldTruck.addCargo(iceCream, 1);
		assertEquals(coldTruck.getCargo(), 1);
		coldTruck.addCargo(biscuit, 1);
		assertEquals(coldTruck.getCargo(), 2);
		
		regTruck.addCargo(biscuit, 1);
		assertEquals(regTruck.getCargo(), 1);
	}
	
	//Testing that the cost of the Truck matches the formula
	@Test
	public void getCostTest() {
		regTruck.addCargo(biscuit, 323);
		assertEquals(regTruck.getCargo(), 323);
			
		assertEquals(coldTruck.getCost(), 1308.16);
		assertEquals(regTruck.getCost(), 830.75);
		
		//float coldTruckCost = 900 + 200*0.7^coldTruck.getTemp()/5;
		//float regTruckCost = 750 + 0.25*regTruck.getCargo();
		//assertEquals(coldTruck.getCost(), coldTruckCost);
		//assertEquals(regTruck.getCost(), regTruckCost);
		
	}
	
	//Testing that an exception is thrown when a user tries to add a cold item to a regular truck
	@Test (expected = DeliveryException) public void addColdCargoToRegTruck(){
		regTruck.addCargo(iceCream, 1);
	}
	
	//Testing that an exception is thrown when a user tries to add too much cargo to a regular truck
	@Test (expected = DeliveryException) public void addTooMuchCargo(){
		regTruck.addCargo(biscuit, 1000); //Thassa lotta bickies, try dunkin that in ya bushells
		assertEquals(coldTruck.getCargo(), 1000);
		
		regTruck.addCargo(biscuit, 1);
	}
	
	//Testing that an exception is thrown when a user tries to add too much cargo to a cold truck
		@Test (expected = DeliveryException) public void addTooMuchColdCargo(){
			coldTruck.addCargo(iceCream, 800);
			assertEquals(coldTruck.getCargo(), 800);
			
			coldTruck.addCargo(iceCream, 1);
		}
	
	//Testing that an exception is thrown when a user tries to make a truck with a temperature that's too cold
	@Test (expected = DeliveryException) public void tempTooLow(){
		ColdTruck brokenTruck = new ColdTruck(-50);
	}	

	//Testing that an exception is thrown when a user tries to make a truck with a temperature that's too hoot
	@Test (expected = DeliveryException) public void tempTooHigh(){
		ColdTruck brokenTruck = new ColdTruck(50);
	}
}
