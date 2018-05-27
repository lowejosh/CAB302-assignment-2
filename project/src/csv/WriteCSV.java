package csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import delivery.Manifest;
import delivery.Truck;
import stock.Item;

public class WriteCSV {
	public static void writeManifest(Manifest manifest, String fileName) throws IOException
	{
		FileWriter writer = new FileWriter(fileName);

		for (Truck truck : manifest.getManifest()) {
			if (truck.cargoCapacity == 800) {
				writer.write(">Refrigerated\n");
			} else {
				writer.write(">Ordinary\n");
			}
			
		    Iterator<Entry<Item, Integer>> itr = truck.getStock().getStock().entrySet().iterator();
		    while (itr.hasNext()) {
		        Entry<Item, Integer> pair = itr.next();
		        Item item = pair.getKey();
		        int quantity = pair.getValue();
		        writer.write(item.getName() + "," + quantity + "\n");
		    }
		}
		writer.close();
	}
}
