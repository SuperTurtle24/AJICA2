/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;

/**
 *
 * @author super
 */
public class AJPICA2 {
    public static void main(String[] args) 
    {
        String loggerHandle = "Logger";
        String loggerIp = "127.0.0.1";
        int loggerPort = 5000;
        
        TicketLogger tl = new TicketLogger(loggerHandle, loggerIp, loggerPort);
        tl.begin();
        
        String ticketingIP = "127.0.0.1";
        int ticketingPort = 1000;
    }    
}
    

