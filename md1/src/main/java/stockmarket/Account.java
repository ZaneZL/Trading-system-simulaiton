package stockmarket;

import java.util.ArrayList;

public class Account {
    private String name;
    private ArrayList<StockTable> listOfStocks;
    private int capital;
    
    
    public Account(){}
    public Account(String n, int c)
    {
        this.name=n;
        this.capital=c;
        this.listOfStocks= new ArrayList<StockTable>();
    }
    public Account(String n, int c,ArrayList<StockTable> sl)
    {
        this.name=n;
        this.capital=c;
        this.listOfStocks=sl;
    }
    public Account(Account A)
    {
        this.name=A.getName();
        this.capital=A.getCapital();
        this.listOfStocks=A.getListOfStocks();
    }

     public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCapital() {
        return capital;
    }
    public void setCapital(int capital) {
        this.capital = capital;
    }
    public ArrayList<StockTable> getListOfStocks() {
        return listOfStocks;
    }
    public void setListOfStocks(ArrayList<StockTable> listOfStocks) {
        this.listOfStocks = listOfStocks;
    }
    public ArrayList<String> getStocksNameInPortfolio()
    {
        if(this.listOfStocks==null)
            return null;
        else
        {
             ArrayList<String> strl=new ArrayList<String>();
            for(StockTable s: listOfStocks)
            {
                strl.add(s.getStock().getName());
            }
            return strl;
        }

    }
    public StockTable hasStock(String a)
    {
        if(this.listOfStocks!=null)
        {
            for(StockTable s:this.listOfStocks)
            {
                if(s.getStock().getName().equals(a))
                {
                    return s;
                }
            }
        }
        return null;
    }

    public void getAllStockInfo(StockList sl)
    {
        if(listOfStocks==null)
            return;
        else
        {
            System.out.println("Account Summary:");
            for(StockTable s: this.listOfStocks)
            {
                System.out.println("Name of the Stock: "+s.getStock().getName());
                System.out.println("The current value of the Stock: "+s.getStock().getPrice());
               // System.out.println("The value purchased of the Stock: "+s.getPurchaseValue());
                System.out.println("The quantity of the Stock bought: "+s.getAccountQuantity());
                System.out.println("The total value of the Stock: "+s.getAccountQuantity()*s.getStock().getPrice());
                System.out.println("========================================");
            }
        }

    }
    public void getStockInfoByName(String n, StockList sl)
    {
        StockTable s=hasStock(n);
        if(s!=null)
        {
            System.out.println("Name of the Stock: "+s.getStock().getName());
            System.out.println("The current value of the Stock: "+s.getStock().getPrice());
            //System.out.println("The value purchased of the Stock: "+s.getPurchaseValue());
            System.out.println("The quantity of the Stock bought: "+s.getAccountQuantity());
            System.out.println("The total value of the Stock: "+s.getAccountQuantity()*s.getStock().getPrice());
            System.out.println("========================================");
        }
        else
            System.out.println("there is no stock by the name "+n+" in your porfolio");
    }

    public void buyStocks(int squant, String sname, StockList sl)
    {
        /*
        1.grab the stock given the stock name
        2.check if the stock quantity is available 
        3.if the capital is enoguh to pay the price
        4.create a new stocktable to record the transaction if its a new stock.
        or modify the stocklist 
        */
        ArrayList<Stock> ls=sl.getStockLists();
        for(Stock s: ls )
        {
            if(s.getName().equals(sname))
            {
                if(s.getAvailableQ()>=squant)
                {
                    /*reentrantlock here*/
                        if(capital>=s.getPrice()*squant)
                        {
                            StockTable ast=this.hasStock(sname);
                            this.capital=this.capital-s.getPrice()*squant;
                            if(ast==null)
                            {
                                ast= new StockTable(this.name,s,squant);
                                this.listOfStocks.add(ast);
                            }
                            else
                            {
                                ast.setNewAccountQuantity(squant);
                            }
                            s.setNewAvailableQ(squant);
                            s.setNewPrice();
                            System.out.println("Successful Transaction");
                            return;
                        }
                }
                System.out.println("There are no enough quantities");
                return;
            }
        }
        System.out.println("There are no stock affiliated with the given name");
    }

    public void sellStocks(int squant, String sname, StockList sl)
    {
        /*
        1.grab the stock given the stock name
        2.check if the account has enough quantity  
        4.create a new stocktable to record the transaction.
        5.modify the stocklist 
        */
        ArrayList<Stock> ls=sl.getStockLists();
        for(Stock s: ls )
        {
            if(s.getName().equals(sname))
            {
                    /*lock here */
                StockTable ast=this.hasStock(sname);
                if(ast!=null)
                {
                    this.capital=this.capital+s.getPrice()*squant;
                    this.listOfStocks.add(ast);
                    s.setNewAvailableQ(-squant);
                    s.setNewPrice();
                    System.out.println("Successful Transaction");
                }
                else
                    System.out.println("The account owner doesn't have a stock by the name "+ s.getName());
                return;
            }
        }
        System.out.println("There are no stock affiliated with the given name");
    }

}
