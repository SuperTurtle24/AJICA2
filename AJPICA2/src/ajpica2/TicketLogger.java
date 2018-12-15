/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.io.IOException;
import ajpica2.Ticket.Ticketing;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author super
 */
public class TicketLogger
{
    private ArrayList<Ticketing> ticketList = new ArrayList<>();
    private Agent loggingAgent;
    
    public TicketLogger(String h, String ri, int rp)
    {
        loggingAgent = new Agent(h, ri, rp);
    }
    
    public void begin()
    {
        try{
        loggingAgent.start();
        }
        catch(IOException e)
        {
            Logger.getLogger(TicketLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void addTicket(Ticketing t)
    {
        ticketList.add(t);
    }
    
    public int ticketCount()
    {
        return ticketList.size();
    }
}
