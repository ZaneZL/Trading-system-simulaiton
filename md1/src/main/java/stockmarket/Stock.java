package stockmarket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


public class Stock {
    private ReentrantLock qlock;
    private ReentrantLock plock;
    private String name;
    private int availableQ;
    private int totalQuantity;
    private int price;
    private AtomicInteger tradeCount;
    public Stock(){this.tradeCount=new AtomicInteger(0);
        this.qlock=new ReentrantLock();
        this.plock=new ReentrantLock();}
    public Stock(String n,int q,int p,int tq)
    {
        this.name=n;
        this.availableQ=q;
        this.totalQuantity=tq;
        this.price=p;
        this.tradeCount=new AtomicInteger(0);
        this.qlock=new ReentrantLock();
        this.plock=new ReentrantLock();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAvailableQ() {
        return availableQ;
    }
    public void setAvailableQ(int quantity) {
        this.availableQ = quantity;
    }
       public int getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public AtomicInteger getTradeCount() {
        return tradeCount;
    }
    public void setTradeCount(AtomicInteger tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String toString()
    {
 
        StringBuffer str=new StringBuffer("");
        str.append("Name: "+this.name+"\n");
        str.append("Price: "+this.price+"\n");
        str.append("Total Quantity: "+this.totalQuantity+"\n");
        str.append("Available Quantity:  "+this.availableQ+"\n");
        str.append("totalTradeCount: "+this.tradeCount.get()+"\n");
        return str.toString();


    }
    public void setNewAvailableQ(int quant)
    {
        if(qlock.tryLock())
        {
            try
            {
                this.availableQ=this.availableQ-quant;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                qlock.unlock();;
            }
        }

    }
    /*
     * percentage to bypass all the nitty-gritty of the actual stock market
     * the totalQuantity will kept increasing till it hits the given totalMax which is 100
     * <10%+8/25
     * <15%+7/25
     * <20%+6/25
     * <25%+5/25
     * <30%+4/25
     * <35%+3/25
     * <40%+2/25
     * 
     * >60%-2/25
     * >65%-3/25
     * >70%-4/25
     * >75%-5/25
     * >80%-6/25
     * >85%-7/25
     * >90%-8/25
     */
    public void setNewPrice()
    {
        
        if(plock.tryLock())
        {
            try{
                this.tradeCount.incrementAndGet();
                if(this.availableQ<=this.totalQuantity*1/10)
                {
                    if(this.totalQuantity!=100)
                    {
                    this.availableQ+=10;
                    this.totalQuantity+=10;

                    this.price=this.price+this.price*8/25;
                    }
                }
                else if(this.availableQ<=this.totalQuantity*1.5/10 && this.availableQ>=this.totalQuantity/10)
                {
                    this.price=this.price+this.price*7/25;
                }
                else if(this.availableQ<=this.totalQuantity*2/10 && this.availableQ>=this.totalQuantity*1.5/10)
                {
                    this.price=this.price+this.price*6/25;
                }
                else if(this.availableQ<=this.totalQuantity*2.5/10 && this.availableQ>=this.totalQuantity*2/10)
                {
                    this.price=this.price+this.price*5/25;
                }
                else if(this.availableQ<=this.totalQuantity*3/10 && this.availableQ>=this.totalQuantity*2.5/10)
                {
                    this.price=this.price+this.price*4/25;
                }
                else if(this.availableQ<=this.totalQuantity*3.5/10 && this.availableQ>=this.totalQuantity*3/10)
                {
                    this.price=this.price+this.price*3/25;
                }
                else if(this.availableQ<=this.totalQuantity*4/10 && this.availableQ>=this.totalQuantity*3.5/10)
                {
                    this.price=this.price+this.price*2/25;
                }
                else if(this.availableQ>=this.totalQuantity*6/10 && this.availableQ<=this.totalQuantity*6.5/10)
                {
                    this.price=this.price-this.price*2/25;
                }
                else if(this.availableQ>=this.totalQuantity*6.5/10 && this.availableQ<=this.totalQuantity*7/10)
                {
                    this.price=this.price-this.price*3/25;
                }
                else if(this.availableQ>=this.totalQuantity*7/10 && this.availableQ<=this.totalQuantity*7.5/10)
                {
                    this.price=this.price-this.price*4/25;
                }
                else if(this.availableQ>=this.totalQuantity*7.5/10 && this.availableQ<=this.totalQuantity*8/10)
                {
                    this.price=this.price-this.price*5/25;
                }
                else if(this.availableQ>=this.totalQuantity*8/10 && this.availableQ<=this.totalQuantity*8.5/10)
                {
                    this.price=this.price-this.price*6/25;
                }
                else if(this.availableQ>=this.totalQuantity*8.5/10 && this.availableQ<=this.totalQuantity*9/10)
                {
                    this.price=this.price-this.price*7/25;
                }
                else if(this.availableQ>=this.totalQuantity*9/10)
                {
                    this.price=this.price-this.price*8/25;
                }
                else
                {
                    return;
                }
                        
            }
            finally
            {
                plock.unlock();
            }
        }
        
    }
}
