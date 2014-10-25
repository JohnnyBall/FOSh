import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.awt.*;
import java.lang.*;

public class PHFrame extends JFrame
                          implements ActionListener
{
    private DCRSimpleTextConsole console;
    private ImageIcon            iconGreen;
    private ImageIcon            iconRed;
    private JLabel               conditionLabelPH;
    private JLabel               phLabel;
    private Timer                pollTimer;
    private java.util.Random     rng;
    public double                minpH;
    public double                maxpH;
    public double                currentpH;

    PHFrame()
    {
        console                       = new DCRSimpleTextConsole(DCRSimpleTextConsole.INS_BEG);

        iconGreen                     = new ImageIcon("icon-green.png");
        iconRed                       = new ImageIcon("icon-red.png");

        minpH                         = 6.5;
        maxpH                         = 7.5;
        currentpH                     = 7.00;

        conditionLabelPH              = new JLabel(iconGreen);
        phLabel                       = new JLabel(Double.toString(currentpH));
        console.addLine("pH range auto-set to 6.50 - 7.50");

        rng                           = new java.util.Random(System.currentTimeMillis());

        pollTimer                     = new Timer(2000, this);
        pollTimer.setActionCommand("CMD_POLL");
        pollTimer.setRepeats(true);

        JButton incButton             = new JButton("pH + 0.5");
        JButton decButton             = new JButton("pH - 0.5");        
        JButton adjustButton          = new JButton("Manually Set Range");
        JButton exitButton            = new JButton("Close");

        JPanel  topSubPanel1          = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  topSubPanel2          = new JPanel(new FlowLayout());
        JPanel  topPanel              = new JPanel(new GridLayout(1, 2));

        JPanel  centerSubPanel1       = new JPanel();
        JPanel  centerSubPanel1a     = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel  centerSubPanel2       = new JPanel(new GridLayout(1, 1));
        JPanel  centerPanel           = new JPanel(new GridLayout(2, 1));

        JPanel  bottomSubPanel1       = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  bottomSubPanel2       = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel  bottomPanel           = new JPanel(new GridLayout(1, 2));

        BoxLayout boxLay              = new BoxLayout(centerSubPanel1, BoxLayout.PAGE_AXIS);

        JLabel phLabelHeader          = new JLabel("pH:");

        phLabelHeader.setFont(new Font("Arial", Font.BOLD, 72));
        phLabel.setFont(new Font("Arial", Font.BOLD, 72));

        incButton.setActionCommand("CMD_INC");
        incButton.addActionListener(this);

        decButton.setActionCommand("CMD_DEC");
        decButton.addActionListener(this);

        adjustButton.setActionCommand("CMD_ADJUST");
        adjustButton.addActionListener(this);

        topSubPanel2.add(incButton);
        topSubPanel2.add(decButton);
        topSubPanel2.add(adjustButton);
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Adjustments"));
        topPanel.add(topSubPanel2);

        this.add(topPanel, BorderLayout.NORTH);

        centerSubPanel1a.add(phLabelHeader);
        centerSubPanel1a.add(phLabel);

        centerSubPanel1.setLayout(boxLay);
        centerSubPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Probe Data"));
        centerSubPanel1.add(centerSubPanel1a);


        centerSubPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Console"));
        centerSubPanel2.add(new JScrollPane(console));

        centerPanel.add(centerSubPanel1);
        centerPanel.add(centerSubPanel2);

        this.add(centerPanel, BorderLayout.CENTER);

        exitButton.setActionCommand("CMD_EXIT");
        exitButton.addActionListener(this);

        bottomSubPanel1.add(new JLabel("PH Status:"));
        bottomSubPanel1.add(conditionLabelPH);

        bottomSubPanel2.add(exitButton);

        bottomPanel.add(bottomSubPanel1);
        bottomPanel.add(bottomSubPanel2);
        this.add(bottomPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(exitButton);
        exitButton.requestFocus();
        this.setupFrame();
        pollTimer.start();

    }//-[END CONSTRUCTOR(S)]----------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmdBuffer = e.getActionCommand();
        
        
        if(cmdBuffer.equals("CMD_EXIT"))
            System.exit(1);

        else if(cmdBuffer.equals("CMD_ADJUST"))
            console.addLine("Adjust...");

        else if(cmdBuffer.equals("CMD_INC"))
        {
            console.addLine("pH increased by 0.5 ...");
            currentpH += 0.5;
            if(currentpH > 14)
                currentpH = 14.00;

            currentpH = Math.round(currentpH * 100);
            currentpH = currentpH/100;

            phLabel.setText(Double.toString(currentpH));
        }

        else if(cmdBuffer.equals("CMD_DEC"))
        {
            console.addLine("pH decreased by 0.5 ...");
            currentpH -= 0.5;
            if(currentpH < 0)
                currentpH = 0.00;

            currentpH = Math.round(currentpH * 100);
            currentpH = currentpH/100;

            phLabel.setText(Double.toString(currentpH));
        }

        else if(cmdBuffer.equals("CMD_POLL"))
        {
            console.addLine("Polling the pH probe...");
            pollPH();
        }
    }//-[END METHOD actionPerformed]---------------------------------------------

    private void setupFrame()
    {
        this.setSize(new Dimension(450, 450));
        this.setLocationRelativeTo(null);
        this.setTitle("FOSh pH Module");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }//-[END METHOD setupFrame]--------------------------------------------------


    private void pollPH()
    {
        if(rng.nextInt(2) == 1)
        {
            currentpH += .01;

            if(currentpH > 14)
                currentpH = 14.00;
        }

        else
        {
            currentpH -= .01;

            if(currentpH < 0)
                currentpH = 0.00;
        }

        currentpH = Math.round(currentpH * 100);
        currentpH = currentpH/100;

        console.addLine("pH is " + currentpH);
        phLabel.setText(Double.toString(currentpH));

        if(currentpH < minpH || currentpH > maxpH)
        {
            conditionLabelPH.setIcon(iconRed);
            console.addLine("pH is no longer within limits.  Begin auto-adjust...");
            pollTimer.stop();

            try
            {
                autoAdjust();
                conditionLabelPH.setIcon(iconGreen);
                console.addLine("Auto-adjust complete. pH is " + currentpH);
                pollTimer.start();
            }

            catch(InterruptedException ie)
            {
                console.addLine("Error encountered during auto-adjust. pH Module offline. Please Reset...");
            }


        }
        return;

    }//-[END of pollPH()]----------------------------------------------------------------

    private void autoAdjust() throws InterruptedException
    {
        double prefPH = (minpH + maxpH)/2.00;

        if(currentpH > prefPH)
        {
            currentpH -= 0.2;

            currentpH = Math.round(currentpH * 100);
            currentpH = currentpH/100;

            console.addLine("pH is " + currentpH);
            phLabel.setText(Double.toString(currentpH));

            if(currentpH <= prefPH + 0.1)
                return;

            else
            {

                Thread.sleep(2000);
                autoAdjust();
                return;
            }
        }

        else
        {
            currentpH += 0.2;

            currentpH = Math.round(currentpH * 100);
            currentpH = currentpH/100;

            console.addLine("pH is " + currentpH);
            phLabel.setText(Double.toString(currentpH));

            if(currentpH >= prefPH - 0.1)
                return;

            else
            {
                Thread.sleep(2000);
                autoAdjust();
                return;
            }
    
        }

    }    
}//end of class
