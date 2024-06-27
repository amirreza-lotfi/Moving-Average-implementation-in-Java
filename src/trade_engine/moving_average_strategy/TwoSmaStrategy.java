package trade_engine.moving_average_strategy;

import stoke_indexes.moving_average.ExponentialMovingAverage;
import stoke_indexes.moving_average.MovingAverage;
import stoke_indexes.moving_average.SimpleMovingAverage;
import trade_engine.TradeRunner;

import java.util.ArrayList;
import java.util.Scanner;

public class TwoSmaStrategy {
    public static void main(String[] args) {
        ArrayList<Double> price = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter short sma period start: ");
        int shortSmaPeriodStart = scanner.nextInt();

        System.out.println("Enter short sma period end: ");
        int shortSmaPeriodEnd = scanner.nextInt();

        System.out.println("Enter long sma period start: ");
        int longSmaPeriodStart = scanner.nextInt();

        System.out.println("Enter long sma period end: ");
        int longSmaPeriodEnd = scanner.nextInt();


        System.out.println("Enter initial stoke: ");
        double initialStoke = scanner.nextDouble();




        for (int i = longSmaPeriodStart; i <= longSmaPeriodEnd; ++i) {
            for (int j = shortSmaPeriodStart; j <= shortSmaPeriodEnd; ++j) {
                if (j>i || i-j<10) break;

                TradeRunner tradeRunner = new TradeRunner(initialStoke);
                MovingAverage longEma = new SimpleMovingAverage(price, i);
                MovingAverage shortEma = new SimpleMovingAverage(price, j);

                for (int iterate = i+1; iterate < price.size(); ++iterate) {
                    switch (tradeRunner.getTradeStatus()) {
                        case FREE -> {
                            if (shortEma.isThisMovingAverageCrossUpSecondMovingAverage(iterate, longEma)) {
                                tradeRunner.buy(price.get(i));
                            }
                        }
                        case HOLD -> {
                            if (shortEma.isThisMovingAverageCrossDownSecondMovingAverage(iterate, longEma)) {
                                tradeRunner.sell(price.get(i));
                            }
                        }
                    }
                }
                tradeRunner.report();
            }
        }

    }

}
