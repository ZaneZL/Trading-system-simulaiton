package stockmarket;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class StockList {
    ReentrantLock rlock;
    private ArrayList<Stock> stockLists;
    
    public StockList(){ stockLists=new ArrayList<>(); rlock=new ReentrantLock();}
    public StockList(ArrayList<Stock> sl){this.stockLists=sl;rlock=new ReentrantLock();}
    public ReentrantLock getRlock() {
        return rlock;
    }
    public ArrayList<Stock> getStockLists() {
        return stockLists;
    }
    public void setStockLists(ArrayList<Stock> stockLists) {
        this.stockLists = stockLists;
    }
    public void addStocktoList(Stock s)
    {
        this.stockLists.add(s);
    }
    /*this will be replaced by database element in the future
     * for now i am just making a local copy of all the stock that's gona be traded on the market
     */
    public void createStockList()
    {
        Stock stockA= new Stock("StockA",60,4,60);
        Stock stockB= new Stock("StockB",40,8,40);
        Stock stockC= new Stock("StockC",50,10,50);
        Stock stockD= new Stock("StockD",60,6,60);
        Stock stockE= new Stock("StockE",45,12,45);
        this.stockLists.add(stockA);
        this.stockLists.add(stockB);
        this.stockLists.add(stockC);
        this.stockLists.add(stockD);
        this.stockLists.add(stockE);
    }
}
