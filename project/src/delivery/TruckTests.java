package delivery;
import stock.Item;
import stock.Stock;
import stock.StockException;
import stock.Store;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import csv.CSVFormatException;
import csv.ReadCSV;
import csv.WriteCSV;

/**
 * 
 * @author Ashley Husband
 *
 */

public class TruckTests {
	
	private Truck coldTruck;
	private Truck regTruck;
	private Manifest manifest;
	private List<Truck> list;
	
	private Item iceCream;
	private Item biscuit;
	private Stock stock;
	private Store store;
	
	//Setting up the items used in the tests
	@Before 
	public void setupItems() throws StockException {
		biscuit = new Item("biscuit", 2, 5, 450, 575);
		iceCream = new Item("ice-cream", 6, 10, 200, 500, -20);
	}
	
	/*
	 * 	---------- START TRUCK TESTS ----------
	 */
	
	/*
	 * Test 1: Initialising the two truck types and checking their default cargo values
	 */
	@Before @Test 
	public void truckTest() throws DeliveryException{
		coldTruck = new ColdTruck(-20); //Cold trucks let you assign a temperature
		regTruck = new RegTruck();
		
		assertEquals(coldTruck.getCargo(), 0);
		assertEquals(regTruck.getCargo(), 0);
	}
	
	/*
	 * Test 2: Adding cargo to a truck
	 */
	@Test 
	public void addCargoTest() throws DeliveryException{
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
	public void getCostTest() throws DeliveryException {
		regTruck.addCargo(biscuit, 323);
		assertEquals(regTruck.getCargo(), 323);
		
		assertEquals(coldTruck.getCost(), 1732.99, 1e-15);
		assertEquals(regTruck.getCost(), 830.75, 1e-15);
		
		//float coldTruckCost = 900 + 200*0.7^coldTruck.getTemp()/5;
		//float regTruckCost = 750 + 0.25*regTruck.getCargo();
		//assertEquals(coldTruck.getCost(), coldTruckCost);
		//assertEquals(regTruck.getCost(), regTruckCost);
		
	}
	
	/*
	 * Test 4: Adding a cold item to a regular truck - Throws Exception
	 */
	@Test (expected = DeliveryException.class) 
	public void addColdCargoToRegTruck() throws DeliveryException{
		regTruck.addCargo(iceCream, 1);
	}
	
	/*
	 * Test 5: Adding too much cargo to a regular truck - Throws Exception
	 */
	@Test (expected = DeliveryException.class) 
	public void addTooMuchCargo() throws DeliveryException{
		regTruck.addCargo(biscuit, 1000); //Thassa lotta bickies, try dunkin that in ya bushells
		assertEquals(regTruck.getCargo(), 1000);
		
		regTruck.addCargo(biscuit, 1);
	}
	
	/*
	 * Test 6: Adding too much cargo to a cold truck - Throws Exception
	 */
	@Test (expected = DeliveryException.class) 
	public void addTooMuchColdCargo() throws DeliveryException{
		coldTruck.addCargo(iceCream, 800);
		assertEquals(coldTruck.getCargo(), 800);
	
		coldTruck.addCargo(iceCream, 1);
		}
	
	/*
	 * Test 7: Setting the temperature of a cold truck too low - Throws Exception
	 */
	@Test (expected = DeliveryException.class) 
	public void tempTooLow() throws DeliveryException{
		ColdTruck brokenTruck = new ColdTruck(-50);
	}	

	/*
	 * Test 8: Setting the temperature of a cold truck too high - Throws Exception
	 */
	@Test (expected = DeliveryException.class) 
	public void tempTooHigh() throws DeliveryException{
		ColdTruck brokenTruck = new ColdTruck(50);
	}
	
	/*
	 * 	---------- END TRUCK TESTS ----------
	 */
	
	/*
	 * 	---------- START MANIFEST TESTS ----------
	 */
	
	@Test 
	public void testManifestConstruction() throws StockException, IOException, DeliveryException, CSVFormatException {
		manifest = new Manifest();
	}
	
	@Test
	public void testLoadManifest() throws StockException, IOException, DeliveryException, CSVFormatException {
		stock = new Stock();
		store = Store.getInstance();
		
		List<Item> itemList = store.getItemList();
        for (Item i : itemList) {
            stock.addQuantity(i, 0);
        }
        // For getting the same instances of items initialised in the store
        for (Item i : itemList) {
        	if (i.getName().equals("biscuits")) {
        		biscuit = i;
        	}
        }
        
        //stock.addQuantity(biscuit, 700);
        
		Truck truck = new RegTruck();
		truck.addCargo(biscuit, 700);
		
		double testCompare = store.getCapital() - (truck.getStock().getTotalCost() + truck.getCost());
		Manifest.loadManifest("manifest_test.txt");
		
		assertEquals(store.getCapital(), testCompare, 0.001);
	}
	
	@Test
	public void testLoadSalesLog() throws StockException, IOException, CSVFormatException {
	
		store = Store.getInstance();
		stock = new Stock();
		
		List<Item> itemList = Store.getInstance().getItemList();
        for (Item i : itemList) {
            stock.addQuantity(i, 0);
        }
        // For getting the same instances of items initialised in the store
        for (Item i : itemList) {
        	if (i.getName().equals("biscuits")) {
        		biscuit = i;
        	}
        }
        stock.addQuantity(biscuit, 394);
        double expected = store.getCapital() + biscuit.getPrice() * stock.getQuantity(biscuit);
        Manifest.loadSalesLog("sales_log_test.txt");
        
        assertEquals(store.getCapital(), expected, 0.001);
	}
	
	@Test
	public void testAddTruck() {
		manifest = new Manifest();
		manifest.addTruck(regTruck); //Adds this Truck object
		manifest.addTruck(coldTruck); //Adds this Truck object
	}
	
	@Test
	public void testGetManifest() {
		manifest = new Manifest();
		list = new ArrayList<>();
		manifest.addTruck(regTruck);
		manifest.addTruck(coldTruck);
		list.add(regTruck);
		list.add(coldTruck);
		assertEquals(manifest.getManifest(), list);
	}
	

	
}
