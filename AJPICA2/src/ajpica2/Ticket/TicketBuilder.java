package ajpica2.Ticket;

//@author scott
import ajpica2.Agent;
import ajpica2.TicketLogger;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class TicketBuilder extends JFrame implements ActionListener {

    private Ticketing t;
    private Agent ticketAgent;
    protected JFrame ticketBuilder = new JFrame("Ticket Builder");

    /**
     * Contains the price of the ticket
     */
    protected JLabel priceLabel = new JLabel();
    
    JRadioButton distanceAButton = new JRadioButton("0 - 1.5 miles");
    JRadioButton distanceBButton = new JRadioButton("1.5 - 3 miles");
    JRadioButton distanceCButton = new JRadioButton("3 - 5 miles");
    JRadioButton distanceDButton = new JRadioButton("5 miles +");
    
    public TicketBuilder() {
        final int width = 400, height = 200;
        ticketBuilder.setSize(width, height);
        ticketBuilder.setDefaultCloseOperation(EXIT_ON_CLOSE);

        addRadioButtons();
        addUpgradesPanel();
        addPricePanel();

        t = new DistanceA(); //prevents NullPointers if someone clicks upgrades

        ticketBuilder.setVisible(true);
    }
    
    public TicketBuilder(String h, String ri, int rp) {
        ticketAgent = new Agent(h, ri, rp);
        final int width = 400, height = 200;
        ticketBuilder.setSize(width, height);
        ticketBuilder.setDefaultCloseOperation(EXIT_ON_CLOSE);

        addRadioButtons();
        addUpgradesPanel();
        addPricePanel();

        t = new DistanceA(); //prevents NullPointers if someone clicks upgrades

        ticketBuilder.setVisible(true);
    }

    final void addRadioButtons() {
        
        distanceAButton.setActionCommand("distanceA");
        distanceAButton.setSelected(true);

        
        distanceBButton.setActionCommand("distanceB");

        
        distanceCButton.setActionCommand("distanceC");

        
        distanceDButton.setActionCommand("distanceD");

        ButtonGroup distanceGroup = new ButtonGroup();
        distanceGroup.add(distanceAButton);
        distanceGroup.add(distanceBButton);
        distanceGroup.add(distanceCButton);
        distanceGroup.add(distanceDButton);

        JPanel radioPanel = new JPanel();
        radioPanel.add(distanceAButton);
        radioPanel.add(distanceBButton);
        radioPanel.add(distanceCButton);
        radioPanel.add(distanceDButton);
        ticketBuilder.add(radioPanel, BorderLayout.NORTH);

        distanceAButton.addActionListener(this);
        distanceBButton.addActionListener(this);
        distanceCButton.addActionListener(this);
        distanceDButton.addActionListener(this);
    }

    final void addUpgradesPanel() {

        JPanel upgradesPanel = new JPanel();
        JCheckBox allDayUpgradeButton = new JCheckBox("All-Day Upgrade");
        JCheckBox childUpgradeButton = new JCheckBox("Child Upgrade");
        JCheckBox offPeakUpgradeButton = new JCheckBox("Off-Peak Upgrade");
        JCheckBox peakUpgradeButton = new JCheckBox("Peak Upgrade");
        JCheckBox returnUpgradeButton = new JCheckBox("Return Upgrade");

        upgradesPanel.add(allDayUpgradeButton);
        upgradesPanel.add(childUpgradeButton);
        upgradesPanel.add(offPeakUpgradeButton);
        upgradesPanel.add(peakUpgradeButton);
        upgradesPanel.add(returnUpgradeButton);

        allDayUpgradeButton.addActionListener((ActionEvent e) -> {
            if (allDayUpgradeButton.isSelected()) {
                t = new AllDayUpgrade(t);
                priceLabel.setText(Double.toString(t.getCost()));
            } else {
            }
        });

        childUpgradeButton.addActionListener((ActionEvent e) -> {
            if (childUpgradeButton.isSelected()) {
                t = new ChildUpgrade(t);
                priceLabel.setText(Double.toString(t.getCost()));
            } else {
            }
        });

        offPeakUpgradeButton.addActionListener((ActionEvent e) -> {
            if (offPeakUpgradeButton.isSelected()) {
                t = new OffPeakUpgrade(t);
                priceLabel.setText(Double.toString(t.getCost()));
            } else {
            }
        });

        peakUpgradeButton.addActionListener((ActionEvent e) -> {
            if (peakUpgradeButton.isSelected()) {
                t = new PeakUpgrade(t);
                priceLabel.setText(Double.toString(t.getCost()));
            } else {
            }
        });

        returnUpgradeButton.addActionListener((ActionEvent e) -> {
            if (returnUpgradeButton.isSelected()) {
                t = new ReturnUpgrade(t);
                priceLabel.setText(Double.toString(t.getCost()));
            } else {
            }
        });

        allDayUpgradeButton.setActionCommand("allDay");
        childUpgradeButton.setActionCommand("child");
        offPeakUpgradeButton.setActionCommand("offPeak");
        peakUpgradeButton.setActionCommand("peak");
        returnUpgradeButton.setActionCommand("return");

        ticketBuilder.add(upgradesPanel, BorderLayout.CENTER);
    }

    final void addPricePanel() {
        JPanel pricePanel = new JPanel();
        pricePanel.add(priceLabel);
        priceLabel.setText("1.2");
        ticketBuilder.add(pricePanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "distanceA":
                t = new DistanceA();
                break;
            case "distanceB":
                t = new DistanceB();
                break;
            case "distanceC":
                t = new DistanceC();
                break;
            case "distanceD":
                t = new DistanceD();
        }
        priceLabel.setText(Double.toString(t.getCost()));
    }
    
    public void begin()
    {
        try{
            ticketAgent.start();
            System.out.println("Agent started");
        }
        catch(IOException e)
        {
            Logger.getLogger(TicketLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
