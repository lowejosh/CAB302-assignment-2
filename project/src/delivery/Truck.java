package delivery;

import stock.Stock;

/**
 * @author Joshua Lowe
 * 
 */


public abstract class Truck {
	public int cargoCapacity;
	public Stock cargo;
	
	public Truck(int cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
		this.cargo = new Stock();
	}
	
	public abstract double getCost();
}
