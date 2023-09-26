package md;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class UserIO implements Callable<Void> {
    String stop;
    public UserIO(){}
    public String getStop(){return this.stop;}
    public void setStop(String s){this.stop=s;}
    @Override
    public Void call(){
        //enter q to close the main thread
        System.out.println("Enter q to quit");
        stop="x";
        Scanner scanner=new Scanner(System.in);

        while(!this.stop.equals("q"))
        {
            this.stop=scanner.nextLine();
            System.out.println("u entered: "+this.stop);
        }
        scanner.close();
        
       return null;
       
    }
}   
