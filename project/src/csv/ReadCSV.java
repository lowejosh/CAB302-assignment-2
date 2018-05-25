package csv;

/**
 * @author Joshua Lowe
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {

	public static void InitialiseItems() throws IOException {
		try {
			FileReader reader = new FileReader("item_properties.csv");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			if (line == null) {
				System.out.println("The file was empty");
			} else {
				System.out.println("The first line of the file was:");
				System.out.println(line);
				bufferedReader.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	
	
	}

}
