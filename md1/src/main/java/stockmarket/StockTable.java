package stockmarket;
/*
 * this is a associate class for stock and account to hold the quantity of a stock
 */
public class StockTable {
    private String accountN;
    private Stock stock;
    private int accountQuantity;
    //private int purchaseValue;

    public StockTable(){stock=new Stock();}
    public StockTable(String an, Stock sn, int aq)
    {
        this.accountN=an;
        this.stock=sn;
        this.accountQuantity=aq;
        //this.purchaseValue=pv;
    }
    public StockTable(StockTable s)
    {
        this(s.getAccountN(),s.getStock(),s.getAccountQuantity());
    }

     public int getAccountQuantity() {
        return accountQuantity;
    }
    public void setAccountQuantity(int accountQuantity) {
        this.accountQuantity = accountQuantity;
    }
     public void setNewAccountQuantity(int accountQuantity) {
        this.accountQuantity += accountQuantity;
    }
    public String getAccountN() {
        return accountN;
    }
    public void setAccountN(String accountN) {
        this.accountN = accountN;
    }
    public Stock getStock() {
        return this.stock;
    }
    public void setStock(Stock s) {
        this.stock = s;
    }
     
}
