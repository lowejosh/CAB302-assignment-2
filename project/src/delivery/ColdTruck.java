package delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import stock.Item;
import stock.StockException;

/**
 * @author Joshua Lowe
 *
 */
public class ColdTruck extends Truck {
	private int temperature;
	
	public ColdTruck(int temperature) throws DeliveryException {
		super(800);
		if (temperature > 10 || temperature < -20) {
			throw new DeliveryException();
		} else {
			this.temperature = temperature;
		}
	}
	
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

	@Override
	public double getCost() {
		return round(900 + (200 * Math.pow(0.7, ((double)temperature / 5))), 2);
	}

	
	// Helper method
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
