package delivery;

import stock.Item;
import stock.Stock;

/**
 * @author Joshua Lowe
 * 
 * 	This abstract class represents a Truck object, which holds a Stock object as its cargo
 * 	and initialises its cargo capacity through a parameter. The addCargo and getCost methods are abstract
 *	and will be overridden by subclasses.
 * 
 */

/**
 */
public abstract class Truck {
	public int cargoCapacity;
	public Stock cargo;
	
	/**
	 * The constructor for the Truck object
	 * @param cargoCapacity The cargo capacity for the truck
	 */
	public Truck(int cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
		this.cargo = new Stock();
	}
	
	/**
	 * Abstract method for adding cargo to the truck
	 * @param item Item to add
	 * @param quantity Quantity of item to add
	 * @throws DeliveryException If the cargo is not a valid item for the truck
	 */
	public abstract void addCargo(Item item, int quantity) throws DeliveryException;
	
	/**
	 * Retrieves the total quantity of cargo in the truck
	 * @return The sum of the quantity of every item in the truck
	 */
	public int getCargo() {
		int sum = 0; 
		for (int i : cargo.getStock().values()) {
			sum+=i;
		}
		return sum;
	}
	
	/**
	 * Gets the cargo Stock object of the truck
	 * @return cargo Stock object of the truck
	 */
	public Stock getStock() {
		return cargo;
	}
	 
	/**
	 * Abstract method for getting the cost of the truck
	 * @return The cost of the truck
	 */
	public abstract double getCost();
}
