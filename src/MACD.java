import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MACD {
    private ArrayList<Double> prices;


    private ArrayList<Double> macdHistogramIndex;
    private ArrayList<Double> signalIndex;
    private ArrayList<Double> macdIndex;


    public MACD(String pricePath) {
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

    public void initializeMcdIndexes(int longPeriod, int shortPeriod, int signalPeriod){
        initializeMacdIndex(shortPeriod, longPeriod);
        initializeSignalLine(signalPeriod);
        initializeHistogramIndex(macdIndex,signalIndex);
    }


    private void initializeMacdIndex(int shortPeriod, int longPeriod) {
        ArrayList<Double> shortEMA = new ExponentialMovingAverage(prices).createExponentialMovingAverage(shortPeriod);
        ArrayList<Double> longEMA = new ExponentialMovingAverage(prices).createExponentialMovingAverage(longPeriod);
        ArrayList<Double> macd = new ArrayList<>();

        for (int i = 0; i < prices.size(); ++i) {
            if (shortEMA.get(i) == null || longEMA.get(i) == null) {
                macd.add(null);
            } else {
                macd.add(shortEMA.get(i) - longEMA.get(i));
            }
        }
        this.macdIndex = macd;
    }
    private void initializeSignalLine(int signalPeriod){
        this.signalIndex  = new ExponentialMovingAverage(prices).createExponentialMovingAverage(signalPeriod);
    }
    private void initializeHistogramIndex(ArrayList<Double> macdIndex, ArrayList<Double> signalLineIndex){
        ArrayList<Double> histogram = new ArrayList<>();

        for (int i = 0; i < macdIndex.size(); ++i) {
            if (macdIndex.get(i) == null || signalLineIndex.get(i) == null) {
                histogram.add(null);
            } else {
                histogram.add(macdIndex.get(i) - signalLineIndex.get(i));
            }
        }

        macdHistogramIndex = histogram;
    }


}
