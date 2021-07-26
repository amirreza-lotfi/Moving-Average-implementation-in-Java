import java.util.ArrayList;

public class ExponentialMovingAverage {

    private ArrayList<Double> price;

    ExponentialMovingAverage(String pricePath){
        price = new ArrayList<>();
        setPrice(pricePath);
    }

    public void setPrice(String path){
        // TODO-> Write code that reads prices from a file and puts it in price ArrayList

        /* for example:
        Scanner sc = new Scanner(new File(file-price.csv));
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext()) {  //returns a boolean value{
            String line = sc.nextLine();
            String []x = line.split(",");
            Integer x2 = Integer.valueOf(x[2]);
        }
         */
    }

    public ArrayList<Double> creatExponentialMovingAverage(int period){
        if(period <= price.size()){
            System.out.println("The period value is not allowable");
            return null;
        }
        else {
            ArrayList<Double> ema = new ArrayList<>(price.size());

            double weighedMultiplier = 2.0 / (period + 1);
            double preEMA = 0;

            for (int i = 1; i <= period; ++i) {
                preEMA += price.get(i);
                ema.add(0.0);
            }

            ema.add(0.0);
            preEMA = preEMA / period;

            for (int i = period + 1; i < price.size(); ++i) {
                double emaCurrentValue = (price.get(i) - preEMA) * weighedMultiplier + preEMA;
                ema.add(emaCurrentValue);
                preEMA = (price.get(i) - preEMA) * weighedMultiplier + preEMA;
            }

            return ema;
        }
    }
}
