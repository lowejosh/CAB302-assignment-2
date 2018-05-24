package stock;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Ashley Husband
 *
 * @param <Item> The type of element contained in a stock
 */
public class Stock{
	
	private Map<Item, Integer> stock;
	private int totalPrice = 0;
	private int totalCost = 0;
	
	public Stock() {
		stock = new HashMap<Item, Integer>();
	}

	public Map<Item, Integer> getStock() {
		return stock;
	}

	public int getQuantity(Item item) {
		return stock.get(item);
	}

	public void addQuantity(Item item, int amountToAdd) {
		if (stock.containsKey(item)) {
			stock.replace(item, stock.get(item) + amountToAdd);
		} else {
			stock.put(item, amountToAdd);
		}
	}

	public void removeQuantity(Item item, int amountToRemove) { // TO-DO: Throws Exception if amount becomes negative or item doesn't exist
		if (stock.containsKey(item)) {
			stock.replace(item, stock.get(item) - amountToRemove);
		} else {
			// Throw exception
		}
	}

	public boolean reorderRequired(Item item) {
		return (stock.get(item) < item.getReorderPoint());
	}

	public int getTotalPrice() {
		stock.forEach((k,v) -> totalPrice = k.getPrice() * v);
		return totalPrice;
	}

	public int getTotalCost() {
		stock.forEach((k,v) -> totalCost = k.getCost() * v);
		return totalCost;
	}
	
}
