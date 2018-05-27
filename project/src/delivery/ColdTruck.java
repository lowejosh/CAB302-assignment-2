package delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map.Entry;

import stock.Item;
import stock.Stock;
import stock.StockException;
import stock.Store;

/**
 * @author Joshua Lowe
 *
 *	This class represents a ColdTruck object which
 *	extends the Truck abstract class. It holds a temperature
 *	and a different cargo capacity. It also has a different
 *	way of calculating the Cost.
 *
 */
public class ColdTruck extends Truck {
	// Truck temperature
	private int temperature;
	
	/**
	 * This method constructs the ColdTruck object and initalises the temperature
	 * @param temperature The temperature of the truck
	 * @throws DeliveryException If the temperature is not valid
	 */
	public ColdTruck(int temperature) throws DeliveryException {
		// Construct the object with 800 cargo capacity
		super(800);
		
		// Throw an exception if the temperature is not between -20 and 10
		if (temperature > 10 || temperature < -20) {
			throw new DeliveryException();
		} else {
			this.temperature = temperature;
		}
	}
	
	
	/**
	 * This method adds a given quantity of an item to the Truck's stock
	 * @param item The given item
	 * @param quantity The quantity of said item
	 * @throws DeliveryException If the given quantity exceeds the cargo capacity
	 */
	@Override
	public void addCargo(Item item, int quantity) throws DeliveryException {
		// If there is not enough space for the cargo, throw exception
		if (this.getCargo() + quantity > this.cargoCapacity) {
			throw new DeliveryException();
		} else {
			try {
				super.cargo.addQuantity(item, quantity);
			} catch (StockException e) {
				e.printStackTrace();
			}
		}
	}	
	
	/**
	 *  This method retrieves the cost of the ColdTruck according to its specified formula
	 *  @return The cost of the truck
	 */
	@Override
	public double getCost() {
		return round(900 + (200 * Math.pow(0.7, ((double)temperature / 5))), 2);
	}

	
	/**
	 * This helper method rounds a double to a certain amount of places - used in rounding the cost
	 * @param value The given value to round
	 * @param places Decimal places to round to
	 * @return The rounded value
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
