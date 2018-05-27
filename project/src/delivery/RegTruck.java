package delivery;

import java.util.Iterator;
import java.util.Map.Entry;

import stock.Item;
import stock.Stock;
import stock.StockException;

/**
 * @author Joshua Lowe
 *
 */
public class RegTruck extends Truck {

	public RegTruck() {
		super(1000);
	}

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
	
	@Override
	public double getCost() {
		double totalQuantity = super.getCargo();
		return (750 + (0.25 * totalQuantity));
	}

}
