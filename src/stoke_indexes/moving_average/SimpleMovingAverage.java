package stoke_indexes.moving_average;

import stoke_indexes.moving_average.MovingAverage;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SimpleMovingAverage extends MovingAverage {


    public SimpleMovingAverage(ArrayList<Double> prices, int period) {
        this.index = createSimpleMovingAverage(prices,period);
    }


    private ArrayList<Double> createSimpleMovingAverage(ArrayList<Double> prices,int period) {
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
