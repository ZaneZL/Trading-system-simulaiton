package md;

public class Timer extends Thread{
    private final int tradeTimeperPerson=20;
    int i;
    public Timer(){}
    public Timer(int c){this.i=c;}
    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
    @Override
    public void run()
    {
        for(this.i=0;this.i<tradeTimeperPerson;this.i++)
        {
            try
            {
                Thread.sleep(1000);
            }catch(InterruptedException e)
            {
               // e.printStackTrace();
                return;
            }
            if(Thread.currentThread().isInterrupted())
            {
                return;
            }
        }
        
    }
    
}
