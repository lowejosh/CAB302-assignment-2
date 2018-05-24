package delivery;

/**
 * @author Joshua Lowe
 *
 */
public class RegTruck extends Truck {

	public RegTruck() {
		super(1000);
	}

	/* (non-Javadoc)
	 * @see delivery.Truck#getCost()
	 */
	@Override
	public double getCost() {
		double totalQuantity = super.getCargo();
		return 750 + (0.25 * totalQuantity);
	}

}
