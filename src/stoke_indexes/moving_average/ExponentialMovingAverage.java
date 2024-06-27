package stoke_indexes.moving_average;

import stoke_indexes.moving_average.MovingAverage;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ExponentialMovingAverage extends MovingAverage {


    public ExponentialMovingAverage(ArrayList<Double> prices, int period) {
        this.index = createExponentialMovingAverage(prices,period);
    }


    private ArrayList<Double> createExponentialMovingAverage(ArrayList<Double> prices, int period) {
        ArrayList<Double> ema = new ArrayList<>();

        if (period <= 0 || period > prices.size()) {
            System.err.println("Invalid period value: " + period);
            return ema;
        }

        double multiplier = 2.0 / (period + 1);

        // Initialize the first EMA value to be the average of the first 'period' prices
        double initialEMA = 0.0;
        for (int i = 0; i < period; ++i) {
            initialEMA += prices.get(i);
            ema.add(null); // add nulls for the first 'period - 1' elements
        }
        initialEMA /= period;
        ema.add(initialEMA); // the first EMA value

        // Calculate the rest of the EMA values
        for (int i = period; i < prices.size(); ++i) {
            double emaCurrentValue = (prices.get(i) - initialEMA) * multiplier + initialEMA;
            ema.add(emaCurrentValue);
            initialEMA = emaCurrentValue;
        }

        return ema;
    }



}
