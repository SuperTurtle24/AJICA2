package ajpica2.Ticket;

//@author scott
class ReturnUpgrade extends TicketUpgrade {

    public ReturnUpgrade(Ticketing t) {
        super(t);
        cost = t.cost * 0.8;
        upgradeString += "Return ";
    }
}
