package stoke_indexes.moving_average;

import java.util.ArrayList;

public abstract class MovingAverage {
    protected ArrayList<Double> index = new ArrayList<>();



    public Double getValue(int index){
        if (index >= this.index.size() || index<0)
            return -1.0;
        return this.index.get(index);
    }

    public boolean isThisMovingAverageCrossUpSecondMovingAverage(int index, MovingAverage second){
        return second.getValue(index-1)>=this.getValue(index-1) && second.getValue(index)<this.getValue(index);
    }
    public boolean isThisMovingAverageCrossDownSecondMovingAverage(int index, MovingAverage second){
        return second.getValue(index-1)<=this.getValue(index-1) && second.getValue(index)>this.getValue(index);
    }
}
