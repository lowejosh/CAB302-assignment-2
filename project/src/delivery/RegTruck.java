package delivery;

import stock.Item;

/**
 * @author Joshua Lowe
 *
 */
public class RegTruck extends Truck {

	public RegTruck() {
		super(1000);
	}

	@Override
	public void addCargo(Item item, int quantity) {
		// If there is not enough space for the cargo or the item is cooled, throw exception
		if (this.getCargo() + quantity > this.cargoCapacity || item.getTemp() != null) {
			// throw exception
		} else {
			super.cargo.addQuantity(item, quantity);
		}
	}
	
	@Override
	public double getCost() {
		double totalQuantity = super.getCargo();
		return 750 + (0.25 * totalQuantity);
	}

}
