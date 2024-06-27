package stoke_indexes;

import stoke_indexes.moving_average.SimpleMovingAverage;

import java.util.ArrayList;

public class BollingerBands {


    private ArrayList<Double> standardDeviation;
    private ArrayList<Double> upperBand;
    private ArrayList<Double> lowerBand;

    public BollingerBands(ArrayList<Double> prices, int period, int multiplier) {
        calculateBollingerBands(prices, period, multiplier);
    }


    void calculateBollingerBands(ArrayList<Double> prices, int period, int multiplier) {
        SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage(prices, period);
        initializeStandardDeviation(prices, simpleMovingAverage, period);
        initializeUpperBand(simpleMovingAverage, standardDeviation, multiplier);
        initializeLowerBand(simpleMovingAverage, standardDeviation, multiplier);
    }

    public void initializeStandardDeviation(ArrayList<Double> prices, SimpleMovingAverage sma, int period) {

        for (int i = 0; i < period - 1; ++i) {
            standardDeviation.add(null); // Add nulls for the first 'period - 1' elements
        }

        for (int i = period - 1; i < prices.size(); ++i) {
            double sum = 0.0;
            for (int j = 0; j < period; ++j) {
                double deviation = prices.get(i - j) - sma.getValue(i);
                sum += deviation * deviation;
            }
            standardDeviation.add(Math.sqrt(sum / period));
        }
    }

    public void initializeUpperBand(SimpleMovingAverage sma, ArrayList<Double> stddev, int multiplier) {
        for (int i = 0; i < stddev.size(); ++i) {
            if (sma.getValue(i) == null || stddev.get(i) == null) {
                upperBand.add(null);
            } else {
                upperBand.add(sma.getValue(i) + (stddev.get(i) * multiplier));
            }
        }
    }

    public void initializeLowerBand(SimpleMovingAverage sma, ArrayList<Double> stddev, int multiplier) {

        for (int i = 0; i < stddev.size(); ++i) {
            if (sma.getValue(i) == null || stddev.get(i) == null) {
                lowerBand.add(null);
            } else {
                lowerBand.add(sma.getValue(i) - (stddev.get(i) * multiplier));
            }
        }
    }

}
