package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PriceProvider {
    public static ArrayList<Double> getPrice(String priceFilePath) throws Exception {
        ArrayList<Double> prices = new ArrayList<>();
        Scanner sc = new Scanner(new File(priceFilePath));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            try {
                Double price = Double.valueOf(line.trim());
                prices.add(price);
            } catch (NumberFormatException e) {
                System.err.println("Skipping invalid price entry: " + line);
            }
        }
        sc.close();
        System.err.println("File not found: " + priceFilePath);
        return prices;
    }
}
