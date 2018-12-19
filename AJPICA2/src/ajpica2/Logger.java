
package ajpica2;
import ajpica2.Ticket.TicketBuilder;
import java.util.Scanner;
/**
 * @author Joe
 */
public class Logger {
    public static void main(String[] args) 
    {
        String ticketingIP = "127.0.0.1";
        int ticketingPort = 10000;
        
        String loggerHandle = "Logger";
        String loggerIp = "127.0.0.1";
        int loggerPort = 50000;
        
        TicketLogger tl = new TicketLogger(loggerHandle, loggerIp, loggerPort);
        tl.begin();
        
        String ticketHandle = "TicketBuilder";
        String ticketRecievedIp = "127.0.0.1";
        int ticketPort = 1000;
        
        TicketBuilder simulateTicket = new TicketBuilder(ticketHandle, ticketRecievedIp, ticketPort);
        simulateTicket.begin();
        simulateTicket.connectTo(loggerIp, loggerPort);
        
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
    

