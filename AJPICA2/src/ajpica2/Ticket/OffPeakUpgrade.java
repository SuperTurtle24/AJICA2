package ajpica2.Ticket;

//@author scott
class OffPeakUpgrade extends TicketUpgrade {

    public OffPeakUpgrade(Ticketing t) {
        super(t);
        cost = t.getCost() * 0.2;
        upgradeString = "OffPeak ";
    }
}
