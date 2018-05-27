package stock;

/**
 * A class that represents an item to be stocked in a store.
 * An item has a name, cost, price, and storage temperature
 * (if applicable), and an amount where if the item drops
 * under it, a reorder is placed with an amount equal to
 * another figure specified here.
 * 
 * @author Ashley Husband
 *
 */
public class Item {

	String name = "";
	int cost = 0;
	int price = 0;
	int reorderPoint = 0; // When the item quantity reaches this level in an inventory, it calls a reorder
	int reorderQuantity = 0; // The amount of items to be ordered on a reorder
	Integer temp = null; // The storage temperature of a cold/fresh item
	
	/**
	 * Constructor for an item that needs to be kept under a certain temperature
	 * @param name The name of the item
	 * @param cost How must the item costs to order
	 * @param price The selling price of the item
	 * @param reorderPoint The amount at which a new delivery of the item is reordered
	 * @param reorderQuantity The amount of the item which is ordered in a reorder
	 * @param temp The temperature the item should be stored at/under
	 * @throws StockException If an item is initialised with a negative value
	 */
	public Item(String name, int cost, int price, int reorderPoint, int reorderQuantity, Integer temp) throws StockException {
		
		if (cost <= 0 || price <= 0) {
			throw new StockException("Item cannot be initialised: Cost/Price cannot be Zero");
		}
		
		if (reorderPoint < 0 || reorderQuantity < 0) {
			throw new StockException("Item cannot be initialised: Reorder Point/Quantity cannot be negative value");
		}
		this.name = name;
		this.cost = cost;
		this.price = price;
		this.reorderPoint = reorderPoint;
		this.reorderQuantity = reorderQuantity;
		this.temp = temp;
	}
	
	/**
	 * Returns the name of an Item
	 * @return the name of the Item
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the cost of an Item
	 * @return the cost of the Item
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Returns the price of an Item
	 * @return the price of the Item
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Returns the reorder point of an Item
	 * @return the reorder point of the Item
	 */
	public int getReorderPoint() {
		return reorderPoint;
	}

	/**
	 * Returns the reorder quantity of an Item
	 * @return the reorder quantity of the Item
	 */
	public int getReorderQuantity() {
		return reorderQuantity;
	}

	/**
	 * Returns the storage temperature of an Item
	 * @return the storage temperature of the Item
	 */
	public Integer getTemp() {
		return temp;
	}
	
}
