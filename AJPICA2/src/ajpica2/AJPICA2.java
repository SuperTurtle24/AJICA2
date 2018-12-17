
package ajpica2;
import java.util.Scanner;
/**
 * @author Joe
 */
public class AJPICA2 {
    public static void main(String[] args) 
    {
        String ticketingIP = "127.0.0.1";
        int ticketingPort = 1000;
        
        String loggerHandle = "Logger";
        String loggerIp = "127.0.0.1";
        int loggerPort = 5000;
        
        TicketLogger tl = new TicketLogger(loggerHandle, loggerIp, loggerPort);
        tl.begin();
        
        while(true)
        {
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            System.out.println("Choose an option \n1. Show All Tickets  \n2. Show Customers");
            switch(input)
            {
                case "1":
                showAllTickets(tl);
                break;
                case "2":
                System.out.println("Not implemented yet");
                break;
                default:
                System.err.println("Not a valid option");
                break;
            }
        } 
    }    
    
    private static void showAllTickets(TicketLogger t)
    {
        for(String s : t.getTicketList())
            System.out.println(s);
    }
}
    

