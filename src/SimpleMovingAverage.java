import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SimpleMovingAverage {

    private ArrayList<Double> prices;

    public SimpleMovingAverage(String pricePath) {
        prices = new ArrayList<>();
        setPrices(pricePath);
    }

    public SimpleMovingAverage(ArrayList<Double> prices) {
        this.prices = prices;
    }

    public void setPrices(String path) {
        try {
            Scanner sc = new Scanner(new File(path));
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
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + path);
        }
    }

    public ArrayList<Double> createSimpleMovingAverage(int period) {
        ArrayList<Double> simpleMovingAverage = new ArrayList<>();

        if (period <= 0 || period > prices.size()) {
            System.err.println("Invalid period value: " + period);
            return simpleMovingAverage;
        }

        for (int i = 0; i < prices.size(); ++i) {
            if (i < period - 1) {
                simpleMovingAverage.add(null);
            } else {
                double sum = 0.0;
                for (int j = i; j > i - period; --j) {
                    sum += prices.get(j);
                }
                simpleMovingAverage.add(sum / period);
            }
        }

        return simpleMovingAverage;
    }
}
