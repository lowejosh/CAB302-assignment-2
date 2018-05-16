package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * We got ourselves the main class right here boyos and girlos (and everything in-between, we don't want to be exclusionary).
 * @author Ashley
 *
 */
public class Main {
/**
 * Here's where it all comes together.
 * @param args Command line arguments
 * @throws IOException 
 */
	public static void main(String args[]) throws IOException {
		reader();
	}
	
	public static void reader() throws IOException {
		FileReader fr = new FileReader("item_properties.csv");
		BufferedReader br = new BufferedReader(fr);
		System.out.println(br.readLine());	
	}

	public static void writer() throws IOException {
		FileWriter fw = new FileWriter("newfile.csv");
		fw.write("rice,100\n");
		fw.write("pasta,150\n");
		fw.write("eggs,200\n");
		System.out.println(br.readLine());
	}
	
}
