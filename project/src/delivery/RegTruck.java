package delivery;

import java.util.Iterator;
import java.util.Map.Entry;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * @author Joshua Lowe
 *
 *	This class represents the RegTruck object which
 *	extends the abstract Truck class. It has a different
 *	cargo capacity and override method for cost when compared
 *	to the ColdTruck object.	
 *
 */
public class RegTruck extends Truck {

	/**
	 * Constructor for the RegTruck object with a cargo capacity of 1000
	 */
	public RegTruck() {
		super(1000);
	}

	/**
	 * This override method adds an item of given quantity to the truck's cargo
	 * @param item The given item
	 * @param quantity The quantity of said item
	 * @throws DeliveryException When the quantity exceeds the capacity, or it is a cold item
	 */
	@Override
	public void addCargo(Item item, int quantity) throws DeliveryException {
		// If there is not enough space for the cargo or the item is cooled, throw exception
		if (this.getCargo() + quantity > this.cargoCapacity || item.getTemp() != null) {
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
	 * This method retrieves the cost of the truck as specified by the given formula
	 * @return The cost of the truck
	 */
	@Override
	public double getCost() {
		double totalQuantity = super.getCargo();
		return (750 + (0.25 * totalQuantity));
	}

}
