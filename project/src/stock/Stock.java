package stock;

import java.util.HashMap;
import java.util.Map;

/**
 * A Collection of Items, used for tracking inventories,
 * cargo etc.
 * @author Ashley Husband
 * @param <Item> The type of element contained in a stock
 */
public class Stock{
	
	private Map<Item, Integer> stock;
	private int totalPrice = 0;
	private int totalCost = 0;
	
	/**
	 * Constructs a new Stock item and initialises a HashMap
	 * which stores the items and their quantities
	 */
	public Stock() {
		stock = new HashMap<Item, Integer>();
	}

	/**
	 * Returns the stock as a Map
	 * @return the stock map
	 */
	public Map<Item, Integer> getStock() {
		return stock;
	}
	
	/**
	 * Returns the quantity of a stored item
	 * @param item the item that's quantity is being checked
	 * @return the quantity of the item
	 * @throws StockException if the item doesn't exist
	 */
	public int getQuantity(Item item) throws StockException{
		if (stock.containsKey(item)) return stock.get(item);
		else throw new StockException("Error: Item doesn't exist");
	}

	/**
	 * Adds an amount of items to the stock map
	 * @param item the item being added
	 * @param amountToAdd the amount being added
	 * @throws StockException if the amount being added is negative or zero
	 */
	public void addQuantity(Item item, int amountToAdd) throws StockException {
		if (amountToAdd <= 0) {
			throw new StockException("Amount cannot be added: Negative or empty value");
		} else if (stock.containsKey(item)) {
			stock.replace(item, stock.get(item) + amountToAdd);
		} else {
			stock.put(item, amountToAdd);
		}
	}

	/**
	 * Removes an amount of items from the stock map
	 * @param item the item being added
	 * @param amountToRemove the amount being removed
	 * @throws StockException if the amount being removed is negative or zero or if the item doesn't exist
	 */
	public void removeQuantity(Item item, int amountToRemove) throws StockException { // TO-DO: Throws Exception if amount becomes negative or item doesn't exist
		if (amountToRemove <= 0) {
			throw new StockException("Amount cannot be removed: Negative or empty value");
		} else if (stock.containsKey(item)) {
			stock.replace(item, stock.get(item) - amountToRemove);
		} else {
			throw new StockException("Amount cannot be removed: Item does not exist in stock");
		}
	}
	
	/**
	 * Checks if an item's quantity is below the reorder point
	 * and needs a reorder
	 * @param item the item whose quantity is being checked
	 * @return true if required, false if not
	 * @throws StockException if the item doesn't exist
	 */
	public boolean reorderRequired(Item item) throws StockException{
		if (stock.containsKey(item)) return (stock.get(item) < item.getReorderPoint());
		else throw new StockException("Error: Item doesn't exist");
	}

	/**
	 * Returns the total price of all items in the stock map
	 * @return the total price
	 */
	public int getTotalPrice() {
		int temp = 0;
		stock.forEach((k,v) -> totalPrice = k.getPrice() * v);
		temp = totalPrice;
		totalPrice = 0;
		return temp;
	}

	/**
	 * Returns the total cost of all items in the stock map
	 * @return the total cost
	 */
	public int getTotalCost() {
		int temp = 0;
		stock.forEach((k,v) -> totalCost = k.getCost() * v);
		temp = totalCost;
		totalCost = 0;
		return temp;
	}
	
}
