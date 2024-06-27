package trade_engine;

public class TradeRunner {
    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }



    TradeStatus tradeStatus  = TradeStatus.FREE;

    private int lossCount = 0;
    private int benefitCount = 0;

    private double price = 0.0;


    private double initialStoke = 0.0;
    private double trade = 0.0;

    public TradeRunner(double initialStoke){
        this.initialStoke = initialStoke;
        this.trade = initialStoke;
    }

    public void buy(double price){
        if (this.tradeStatus == TradeStatus.HOLD) return;
        this.price = price;
        this.tradeStatus = TradeStatus.HOLD;
    }
    public void sell(double price){
        if (this.tradeStatus == TradeStatus.FREE) return;
        double diff = this.price - price;
        if (diff>0) benefitCount+=1;
        else if (diff<0) lossCount+=1;

        trade += (diff/price)*100*trade;
    }

    public String report(){
        return "loss count: " + lossCount + "  benefit count: " + benefitCount + "initial stoke: " + initialStoke + "  final stoke: " + trade + " percent: "+ (trade-initialStoke)/100;
    }
}
