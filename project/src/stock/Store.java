package stock;

public class Store {
	
	private final double STARTING_CAPITAL = 100000.001;
	private final String STORE_NAME = "SuperMart";
	
	private double capital = STARTING_CAPITAL;
	private Stock inventory = null;
	private String name = STORE_NAME;
	private static Store instance;
	
	private Store() {
	}

	public static Store getInstance() {
		if (instance == null) instance = new Store();
		return instance;
	}

	public double getCapital() {
		return capital;
	}

	public void modifyCapital(double amountToAdd) {
		capital += amountToAdd;
	}

}
