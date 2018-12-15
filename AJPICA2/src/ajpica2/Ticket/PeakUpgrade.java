package ajpica2.Ticket;

//@author scott
class PeakUpgrade extends TicketUpgrade {

    public PeakUpgrade(Ticketing t) {
        super(t);
        cost = t.getCost() * 0.5;
        upgradeString += "Peak ";
    }
}
