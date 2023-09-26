package md;
import stockmarket.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

enum StocksName{
    StockA,StockB,StockC,StockD,StockE
}
public class Trader extends Thread{

    private Account traderaccount;
    private Timer t;
    private Semaphore inQueue;
    private StockList st;

    public Trader(){}
    public Trader(Account a,Semaphore iq,StockList s)
    {
        this.traderaccount=a;
        this.inQueue=iq;
        this.st=s;
        t=new Timer(0);
    }
    @Override
    public void run()
    {
        try{
            inQueue.acquire();
            //20 secs to trade 
            t.setI(0);
            t.start();
            Random rand = new Random();
            StocksName[] sname= StocksName.values();
            //select random stock
            int rand_Stock;
            //select random quantity
            int rand_Quantity;
            //buy ro sell
            int rand_buyorsell;
            //boolean first can't sell
            boolean first=true;
            while(t.getI()!=20)
            {   
        
                rand_buyorsell=rand.nextInt(2);
                //buy
                if(rand_buyorsell==0)
                {
                    rand_Stock=rand.nextInt(sname.length);
                    rand_Quantity=rand.nextInt(12);

                    this.traderaccount.buyStocks(rand_Quantity,sname[rand_Stock].toString() , this.st);
                    try
                    {
                        Thread.sleep(1500);
                    }
                    catch(InterruptedException e)
                    {
                        //e.printStackTrace();
                        return;
                    }
                    if(Thread.currentThread().isInterrupted())
                    {
                    
                        System.out.println("PriceUpdater Thread is interrupted:"+Thread.currentThread().getId()+" Closing the Thread");
                        return;
                    }
                }
                else
                {   //sell
                    
                    if(!first)
                        {
                        //choose the stock in the trader account
                        rand_Stock=rand.nextInt(this.traderaccount.getListOfStocks().size());
                        
                        //choose the quantity in the trader accunt
                        rand_Quantity=rand.nextInt(this.traderaccount.getListOfStocks().get(rand_Stock).getAccountQuantity());

                        this.traderaccount.sellStocks(rand_Quantity,this.traderaccount.getListOfStocks().get(rand_Stock).getStock().getName() , this.st);
                        try
                        {
                            Thread.sleep(1500);
                        }
                        catch(InterruptedException e)
                        {
                            //e.printStackTrace();
                            return;
                        }
                    }
                }
                if(Thread.currentThread().isInterrupted())
                {
                    System.out.println("Trader Thread "+Thread.currentThread().getId()+" is interrupted. Closing the Thread");
                    return;
                }
            }
    
            inQueue.release();
        }catch(InterruptedException e)
        {
            System.out.println("Trader Thread "+Thread.currentThread().getId()+" is interrupted. Closing the Thread");
            e.printStackTrace();
            return;
        }
        
    }
}
