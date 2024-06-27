package trade_engine.moving_average_strategy;

import stoke_indexes.moving_average.ExponentialMovingAverage;
import stoke_indexes.moving_average.MovingAverage;
import trade_engine.TradeRunner;

import java.util.ArrayList;
import java.util.Scanner;

public class TwoEmaStrategy {

    public static void main(String[] args) {
        ArrayList<Double> price = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter short ema period start: ");
        int shortEmaPeriodStart = scanner.nextInt();

        System.out.println("Enter short ema period end: ");
        int shortEmaPeriodEnd = scanner.nextInt();

        System.out.println("Enter long ema period start: ");
        int longEmaPeriodStart = scanner.nextInt();

        System.out.println("Enter long ema period end: ");
        int longEmaPeriodEnd = scanner.nextInt();


        System.out.println("Enter initial stoke: ");
        double initialStoke = scanner.nextDouble();




        for (int i = longEmaPeriodStart; i <= longEmaPeriodEnd; ++i) {
            for (int j = shortEmaPeriodStart; j <= shortEmaPeriodEnd; ++j) {
                if (j>i || i-j<10) break;

                TradeRunner tradeRunner = new TradeRunner(initialStoke);
                MovingAverage longEma = new ExponentialMovingAverage(price, i);
                MovingAverage shortEma = new ExponentialMovingAverage(price, j);

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
