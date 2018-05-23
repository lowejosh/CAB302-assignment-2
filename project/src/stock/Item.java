package stock;

/**
 * @author Ashley Husband
 *
 */
public class Item {

	String name = "";
	int cost = 0;
	int price = 0;
	int reorderPoint = 0;
	int reorderQuantity = 0;
	Integer temp = null;
	
	/**
	 * Constructor for an item that doesn't need to be kept under a certain temperature
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderQuantity
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderQuantity) {
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderQuantity = reorderQuantity;
	}
	
	/**
	 * Constructor for an item that needs to be kept under a certain temperature
	 * @param name
	 * @param cost
	 * @param price
	 * @param reorderPoint
	 * @param reorderQuantity
	 * @param temp
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderQuantity, Integer temp) {
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderQuantity = reorderQuantity;
		this.temp = temp;
	}
	
	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getPrice() {
		return price;
	}

	public int getReorderPoint() {
		return reorderPoint;
	}

	public Object getReorderQuantity() {
		return reorderQuantity;
	}
	
	public Integer getTemp() {
//		if (temp==null) return null;
//		else { 
//			int intTemp = temp;
//			return intTemp;
//		}
		return temp; //fails test 7 somehow
	}
	
}
