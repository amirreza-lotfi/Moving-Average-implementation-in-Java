// (c) Amirreza Lotfi

import java.util.ArrayList;

public class SimpleMovingAverage {

    private ArrayList<Double> price;

    SimpleMovingAverage(String pricePath){
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

    public ArrayList<Double> creatSimpleMovingAverage(int period) {
        if (period <= price.size()) {
            System.out.println("The period value is not allowable");
            return null;
        } else {
            ArrayList<Double> simpleMovingAverage = new ArrayList<>(price.size());

            for (int i = 0; i < period; ++i)
                simpleMovingAverage.add(null);

            for (int i = 1; i <= price.size() - period; ++i) {

                Double sum = 0.0;
                for (int j = i; j < i + period; ++j) {
                    sum += price.get(j);
                }

                simpleMovingAverage.add(sum / period);
            }

            return simpleMovingAverage;
        }
    }

}
