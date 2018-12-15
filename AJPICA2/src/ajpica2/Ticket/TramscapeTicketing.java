package ajpica2.Ticket;

//@author scott

public class TramscapeTicketing {

    private static Ticketing t;

    public static void main(String args[]) {
        String ticketHandle = "";
        String ticketRecievedIp = "";
        int ticketPort = 0;
        
        TicketBuilder simulateTicket = new TicketBuilder(ticketHandle, ticketRecievedIp, ticketPort);
        simulateTicket.begin();
    }
}
