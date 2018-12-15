package ajpica2.Ticket;

//@author scott
 class ChildUpgrade extends TicketUpgrade{
    public ChildUpgrade(Ticketing t) {
        super(t);
        cost = t.getCost() * 0.4;
        upgradeString += "Child ";
    }
     
    @Override // reduced price rather than added 
    protected double getCost()
    {
        return Double.valueOf(decimalFormat2.format(t.getCost() - cost));
    }
 }
