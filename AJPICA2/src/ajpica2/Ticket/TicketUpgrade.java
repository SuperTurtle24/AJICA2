package ajpica2.Ticket;

//@author scott
import java.text.DecimalFormat;

abstract class TicketUpgrade extends Ticketing {

    protected Ticketing t;
    protected static DecimalFormat decimalFormat2 = new DecimalFormat(".##");// converts to 2dp if more

    public TicketUpgrade(Ticketing t) {
        this.t = t;
    }
    
    @Override
    protected double getCost() {
        return Double.valueOf(decimalFormat2.format(t.getCost() + cost));
    }

    @Override
    protected String getUpgrades() {
        return t.upgradeString + upgradeString;
    }

}
