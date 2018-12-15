/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import ajpica2.Ticket.Ticketing;
import java.util.ArrayList;
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

    
    public void addTicket(Ticketing t)
    {
        ticketList.add(t);
    }
    
    public int ticketCount()
    {
        return ticketList.size();
    }
}
