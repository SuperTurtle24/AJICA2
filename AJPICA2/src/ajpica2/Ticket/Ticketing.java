package ajpica2.Ticket;

//@author scott
import java.text.DecimalFormat;

public abstract class Ticketing {

    protected double cost;
    protected String upgradeString = "";
    protected static DecimalFormat df2 = new DecimalFormat(".##");// converts to 2dp if more

    /**
     * Returns cost of a ticket
     *
     * @return cost
     */
    protected double getCost() {
        return Double.valueOf(df2.format(cost));
    }

    /**
     * Returns the upgrades a ticket has
     *
     * @return upgradeString
     */
    protected String getUpgrades() {
        return upgradeString;
    }
}
