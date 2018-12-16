package ajpica2.Ticket;


//@author scott

public class TramscapeTicketing {

    private static Ticketing t;

    public static void main(String args[]) {
        String ticketHandle = "TicketBuilder";
        String ticketRecievedIp = "127.0.0.1";
        int ticketPort = 1000;
        
        String loggerIp = "127.0.0.1";
        int loggerPort = 5000;
        
        TicketBuilder simulateTicket = new TicketBuilder(ticketHandle, ticketRecievedIp, ticketPort);
        simulateTicket.begin();
        simulateTicket.connectTo(loggerIp, loggerPort);
        
        
    }
}
