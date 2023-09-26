/* This is a practice for consolidating what i learn from Udemy java multithread programming course by Sir.Probenski
 * This program demonstrates the basic understanding of the implementation of parellel programming concept. 
 * tools used: 1.semaphore 2.reentrantlock 3.Non-blocking IO
 * Scenarios: in the early days of stock trading, there are only 4 counters at the stock market,
 * anyone who'd like to buy stock have to wait in line. There are in total 5 stocks for ppl to invest in. 
 * the program will terminate once the user enters q.
 */
package md;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import stockmarket.*;


public class Main{
    private static final int MAX_THread=100;
    private static final int ConcurrentDealer=15;
    public static void main(String[] args) {
        
    /*
    Create max thread pool to ensure the system load
    Create semaphore to set the number of dealers who could trade at the same time
    Create a stocklist that every trader would access 
    
    */    
    ExecutorService StockMarketPool= Executors.newFixedThreadPool(MAX_THread);
    //ExecutorService Userio= Executors.newFixedThreadPool(2);
    Semaphore StockMarketQueue= new Semaphore(ConcurrentDealer,true);
    
       try{

            System.out.println("The Extra-Simplified Stock Market Simulation opens now!!");

            //create stocklist for trading ->replaced this with database implementaiton in the future
            StockList stocklist= new StockList();
            stocklist.createStockList();
            //create traders ->replaced this with database implementaiton in the future
            Account a1=new Account("a1",130);
            Trader trader1=new Trader(a1,StockMarketQueue,stocklist);
            Account a2=new Account("a2",170);
            Trader trader2=new Trader(a2,StockMarketQueue,stocklist);
            Account a3=new Account("a3",140);
            Trader trader3=new Trader(a3,StockMarketQueue,stocklist);
            Account a4=new Account("a4",120);
            Trader trader4=new Trader(a4,StockMarketQueue,stocklist);
            Account a5=new Account("a5",160);
            Trader trader5=new Trader(a5,StockMarketQueue,stocklist);
            //priceUpdater thread 
            PriceUpdater pu=new PriceUpdater(stocklist);
            // IO thread

            //start running until exit

                //dealers trade in the background
                StockMarketPool.execute(trader1);
                StockMarketPool.execute(trader2);
                StockMarketPool.execute(trader3);
                StockMarketPool.execute(trader4);
                StockMarketPool.execute(trader5);
                //UI:price updater shows the change made in the background  
                pu.start(); 
            
           //userio decides when to stop
           UserIO  uio=new UserIO();
           FutureTask<Void> stop=new FutureTask<Void>(uio);
           Thread uiothread=new Thread(stop);
           uiothread.start();
           while(!stop.isDone())
           {
               try {
                   Thread.sleep(1000); 
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           
            pu.interrupt();
            StockMarketPool.shutdown();
            try{
                boolean terminated = StockMarketPool.awaitTermination(5, TimeUnit.SECONDS);
                if(terminated)
                {
                    System.out.println("The Executor thread is terminated");
                }
                else
                {
                    System.out.println("The thread is still running shutting down NOW!");
                    StockMarketPool.shutdownNow();
                    return;
                }
            }catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
           
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}