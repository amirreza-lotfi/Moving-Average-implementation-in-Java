import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class BollingerBands {

    private ArrayList<Double> prices;


    private ArrayList<Double> standardDeviation;
    private ArrayList<Double> upperBand;
    private ArrayList<Double> lowerBand;

    public BollingerBands(String pricePath) {
        prices = new ArrayList<>();
        setPrices(pricePath);
    }

    private void setPrices(String path) {
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

    void calculateBollingerBands(int period,int multiplier){
        ArrayList<Double> simpleMovingAverage = new SimpleMovingAverage(prices).createSimpleMovingAverage(period);
        initializeStandardDeviation(simpleMovingAverage,period);
        initializeUpperBand(simpleMovingAverage,standardDeviation,multiplier);
        initializeLowerBand(simpleMovingAverage,standardDeviation,multiplier);
    }
    public void initializeStandardDeviation(ArrayList<Double> sma, int period) {

        for (int i = 0; i < period - 1; ++i) {
            standardDeviation.add(null); // Add nulls for the first 'period - 1' elements
        }

        for (int i = period - 1; i < prices.size(); ++i) {
            double sum = 0.0;
            for (int j = 0; j < period; ++j) {
                double deviation = prices.get(i - j) - sma.get(i);
                sum += deviation * deviation;
            }
            standardDeviation.add(Math.sqrt(sum / period));
        }
    }

    public void initializeUpperBand(ArrayList<Double> sma, ArrayList<Double> stddev, int multiplier) {
        for (int i = 0; i < sma.size(); ++i) {
            if (sma.get(i) == null || stddev.get(i) == null) {
                upperBand.add(null);
            } else {
                upperBand.add(sma.get(i) + (stddev.get(i) * multiplier));
            }
        }
    }

    public void initializeLowerBand(ArrayList<Double> sma, ArrayList<Double> stddev, int multiplier) {

        for (int i = 0; i < sma.size(); ++i) {
            if (sma.get(i) == null || stddev.get(i) == null) {
                lowerBand.add(null);
            } else {
                lowerBand.add(sma.get(i) - (stddev.get(i) * multiplier));
            }
        }
    }

}
