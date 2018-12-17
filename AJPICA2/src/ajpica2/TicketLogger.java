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
public class TicketLogger extends Agent
{
    private ArrayList<String> ticketList = new ArrayList<>();
    private Object lock = new Object();
    
    public TicketLogger(String h, String ri, int rp)
    {
        super(h, ri, rp);
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
    
    @Override
    public void start() throws IOException
    {
        startReceiver();
        loggingThread.start();
    }
    
    Thread loggingThread = new Thread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    while(true)
                    {
                        synchronized(lock)
                        {
                            for(Connection connection : connectionMap.values())
                            {
                                try
                                {
                                    if(connection.hasMessage())
                                    {
                                        Message newMessage = connection.recieveMessage();
                                        addTicket(newMessage.getContent());
                                        System.out.println("New Ticket added: " + newMessage.getContent());
                                    }
                                }
                                catch (IOException e)
                                {
                                    Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
                                }
                            }
                        }
                    }
                }
            }
    );
}
