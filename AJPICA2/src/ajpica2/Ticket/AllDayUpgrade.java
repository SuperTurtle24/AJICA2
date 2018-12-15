package ajpica2.Ticket;

//@author scott
 class AllDayUpgrade extends TicketUpgrade{
    public AllDayUpgrade(Ticketing t) {
        super(t);
        cost = 5.30;
        upgradeString += "All-Day ";
    }
      
    @Override
    protected double getCost()//Overrides as AllDay ticket is standard across all tickets
    {
        return cost;
    }
}
