import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class RsiIndex {

    private ArrayList<Double> prices;

    public RsiIndex(String pricePath) {
        prices = new ArrayList<>();
        setPrices(pricePath);
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

    public ArrayList<Double> createRSI(int period) {
        ArrayList<Double> rsi = new ArrayList<>();

        if (period <= 0 || period >= prices.size()) {
            System.err.println("Invalid period value: " + period);
            return rsi;
        }

        ArrayList<Double> gains = new ArrayList<>();
        ArrayList<Double> losses = new ArrayList<>();

        for (int i = 1; i < prices.size(); ++i) {
            double change = prices.get(i) - prices.get(i - 1);
            if (change > 0) {
                gains.add(change);
                losses.add(0.0);
            } else {
                gains.add(0.0);
                losses.add(-change);
            }
        }

        double avgGain = 0.0;
        double avgLoss = 0.0;

        for (int i = 0; i < period; ++i) {
            avgGain += gains.get(i);
            avgLoss += losses.get(i);
            rsi.add(null); // add nulls for the first 'period - 1' elements
        }

        avgGain /= period;
        avgLoss /= period;

        double rs = avgGain / avgLoss;
        rsi.add(100 - (100 / (1 + rs))); // the first RSI value

        for (int i = period; i < gains.size(); ++i) {
            avgGain = ((avgGain * (period - 1)) + gains.get(i)) / period;
            avgLoss = ((avgLoss * (period - 1)) + losses.get(i)) / period;

            rs = avgGain / avgLoss;
            rsi.add(100 - (100 / (1 + rs)));
        }

        return rsi;
    }

}
