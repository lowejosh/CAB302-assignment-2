package delivery;

import stock.Item;
import stock.Stock;

/**
 * @author Joshua Lowe
 * 
 */

// TODO - ADD JAVADOC COMMENTS

public abstract class Truck {
	public int cargoCapacity;
	public Stock cargo;
	
	public Truck(int cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
		this.cargo = new Stock();
	}
	
	public void addCargo(Item item, int quantity) {
		cargo.addQuantity(item, quantity);
	}
	
	public int getCargo() {
		int sum = 0; 
		for (int i : cargo.getStock().values()) {
			sum+=i;
		}
		return sum;
	}
	
	public abstract double getCost();
}
