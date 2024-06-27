package stoke_indexes;

import stoke_indexes.moving_average.ExponentialMovingAverage;

import java.util.ArrayList;

public class MACD {


    private ArrayList<Double> macdHistogramIndex;
    private ExponentialMovingAverage signalIndex;
    private ArrayList<Double> macdIndex;



    public void initializeMcdIndexes(ArrayList<Double> prices, int longPeriod, int shortPeriod, int signalPeriod){
        initializeMacdIndex(prices, shortPeriod, longPeriod);
        initializeSignalLine(prices, signalPeriod);
        initializeHistogramIndex(prices, macdIndex,signalIndex);
    }


    private void initializeMacdIndex(ArrayList<Double> prices, int shortPeriod, int longPeriod) {
        ExponentialMovingAverage shortEMA = new ExponentialMovingAverage(prices,shortPeriod);
        ExponentialMovingAverage longEMA = new ExponentialMovingAverage(prices,longPeriod);
        ArrayList<Double> macd = new ArrayList<>();

        for (int i = 0; i < prices.size(); ++i) {
            if (shortEMA.getValue(i) == null || longEMA.getValue(i) == null) {
                macd.add(null);
            } else {
                macd.add(shortEMA.getValue(i) - longEMA.getValue(i));
            }
        }
        this.macdIndex = macd;
    }
    private void initializeSignalLine(ArrayList<Double> prices, int signalPeriod){
        this.signalIndex  = new ExponentialMovingAverage(prices,signalPeriod);
    }
    private void initializeHistogramIndex(ArrayList<Double> prices, ArrayList<Double> macdIndex, ExponentialMovingAverage signalIndex){
        ArrayList<Double> histogram = new ArrayList<>();

        for (int i = 0; i < macdIndex.size(); ++i) {
            if (macdIndex.get(i) == null || signalIndex.getValue(i) == null) {
                histogram.add(null);
            } else {
                histogram.add(macdIndex.get(i) - signalIndex.getValue(i));
            }
        }

        macdHistogramIndex = histogram;
    }


}
