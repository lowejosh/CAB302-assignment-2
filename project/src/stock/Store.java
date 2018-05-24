package stock;

public class Store {
	protected Store() {}
	
	private final double STARTING_CAPITAL = 1000000.0;
	private final String STORE_NAME = "SuperMart";
	
	private double capital = STARTING_CAPITAL;
	private Stock inventory = null;
	private String name = STORE_NAME;
	
	private static class StoreHolder() {
		private final static Store INSTANCE = new Store();
	}

}
