package md;

import stockmarket.*;
public class PriceUpdater extends Thread {
    private StockList sl;

    public PriceUpdater()
    {
        sl=new StockList();
    }
    public PriceUpdater(StockList sl)
    {
        this.sl=sl;
    }
    @Override
    public void run()
    {
        
        //things to consider:!! where to put lock at
        StringBuilder str= new StringBuilder();
        while(true)
        {
            if(sl.getRlock().tryLock())
            {
                try
                {
                    try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException e)
                    {
                       // e.printStackTrace();
                        return;
                    }

                    for(Stock s: this.sl.getStockLists())
                    {
                        str.append(s.toString());
                    }
                    System.out.println(str);
                    
                }catch(Exception e)
                {
                    //e.printStackTrace();
                    return;
                }
                finally{
                    sl.getRlock().unlock();
                }

                try
                    {
                        Thread.sleep(2000);
                    }
                    catch(InterruptedException e)
                    {
                       
                        //e.printStackTrace();
                        return;
                    }
            }

            if(Thread.currentThread().isInterrupted())
            {
               
                System.out.println("PriceUpdater Thread is interrupted:"+Thread.currentThread().getId()+" Closing the Thread");
                return;
            }
        }
    }
}   
