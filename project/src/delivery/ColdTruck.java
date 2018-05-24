package delivery;

import stock.Item;

/**
 * @author Joshua Lowe
 *
 */
public class ColdTruck extends Truck {
	private int temperature;
	
	public ColdTruck(int temperature) {
		super(800);
		if (temperature > 10 || temperature < -20) {
			//throw exception
		} else {
			this.temperature = temperature;
		}
	}
	
	@Override
	public void addCargo(Item item, int quantity) {
		// If there is not enough space for the cargo, throw exception
		if (this.getCargo() + quantity > this.cargoCapacity) {
			// throw exception
		} else {
			super.cargo.addQuantity(item, quantity);
		}
	}

	@Override
	public double getCost() {
		double result = 900 + (200 * Math.pow(0.7, ((double)temperature / 5)));
		System.out.println("the temp is" + temperature + "\n and the result is " + result);
		return result;
	}

}
