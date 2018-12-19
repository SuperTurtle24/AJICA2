package ajpica2;
import java.io.IOException;
import ajpica2.Ticket.Ticketing;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author Joe
 */
public class TicketLogger
{
    private ArrayList<String> ticketList = new ArrayList<>();
    private Agent loggerAgent;
    
    public TicketLogger(String h, String ri, int rp)
    {
        loggerAgent = new Agent(h, ri, rp);
    }
    
    public void begin()
    {
        try
        {
            start();
            System.out.println("Agent started");
        }
        catch(IOException e)
        {
            Logger.getLogger(TicketLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void addTicket(String t)
    {
        ticketList.add(t);
    }
    
    public int ticketCount()
    {
        return ticketList.size();
    }
    
    public ArrayList<String> getTicketList()
    {
        return ticketList;
    }
    
    public void start() throws IOException
    {
        loggerAgent.start();
    }
    
}
