/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   FOSh-Salinity-Module
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2014-09-24
   FILENAME:                  PrimaryFrame.java
   PURPOSE:                   Main GUI Frame
   VERSION:                   20141006-1140
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
package FOShSalinityModule;

//=[BEGIN IMPORTS]==============================================================
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
//=[END IMPORTS]================================================================

//=[BEGIN CLASS PrimaryFrame]===================================================
public class PrimaryFrame extends JFrame
    implements ActionListener
{
    //-[BEGIN MEMBER DATA]------------------------------------------------------
    private static final int RANDOM_LIMIT = 1000;
    
    private FOShSalinityModule.DCRSimpleTextConsole     console;
    private FOShSalinityModule.AdjustmentDialog         dialog;
    
    private double                      curSalinity;
    private ImageIcon                   iconGreen;
    private ImageIcon                   iconRed;
    private int                         curConductivity;
    private int                         minConductivity;
    private int                         maxConductivity;
    private int                         curTemperature;
    private JLabel                      conditionLabel;
    private JLabel                      conductivityLabelT;
    private JLabel                      conductivityLabelC;
    private JLabel                      temperatureLabel;
    private JLabel                      salinityLabel;
    private JLabel                      timerLabel;
    private long                        timeElapsed;
    private Timer                       timer;
    //-[END MEMBER DATA]--------------------------------------------------------
    
    //-[BEGIN CONSTRUCTOR(S)]---------------------------------------------------
    PrimaryFrame()
    {
        console = new FOShSalinityModule.DCRSimpleTextConsole(
            FOShSalinityModule.DCRSimpleTextConsole.INS_BEG);
        
        setupGUI();
        setupFrame();
        requestConductivityLimits();
        requestTemperature();
        readConductivityProbe();
        updateLabels();
        setupTimer();
    }
    //-[END CONSTRUCTOR(S)]-----------------------------------------------------
    
    //-[BEGIN METHOD actionPerformed]-------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        
        switch(cmd)
        {
            case "CMD_NULL":
                break;
            case "CMD_POLL":
                readConductivityProbe();
                break;
            case "CMD_ADJCON":
                handleAdjustConductivityClicked();
                break;
            case "CMD_EXIT":
                System.exit(1);
            default:
                throw new UnsupportedOperationException("actionPerformed() in "
                    + "PrimaryFrame encountered an unrecognized Action "
                    + "Command.");
        }
    }
    //-[END METHOD actionPerformed]---------------------------------------------
    
    //-[BEGIN METHOD calculatePracticalSalinity]--------------------------------
    private void calculatePracticalSalinity()
    {
        // Candy
        console.addLine("Calculating practical salinity...");
        
        /*  Oh boy, here we go...  */
        
        double ckcl;    // Conductivity of KCl solution @ curTemperature
        double rt;      // Conductivity ratio between sample and KCl
        double deltaS;
        
        // CONSTANTS:
        double a0 = 0.0080;
        double a1 = -0.1692;
        double a2 = 25.3851;
        double a3 = 14.0941;
        double a4 = -7.0261;
        double a5 = 2.7081;
        double b0 = 0.0005;
        double b1 = -0.0056;
        double b2 = -0.0066;
        double b3 = -0.0375;
        double b4 = 0.0636;
        double b5 = -0.0144;
        double c0 = -0.0267243;
        double c1 = 4.6636947;
        double c2 = 861.3027640;
        double c3 = 29035.1640851;
        
        // Calculate conductivity C(KCL soln. @ curTemperature)
        ckcl = c0 * Math.pow(curTemperature, 3) 
            + c1 * Math.pow(curTemperature, 2) + c2 * curTemperature + c3;
        
        // Calculate conductivity ratio
        rt = curConductivity / ckcl;
        
        // Calculate deltaS
        deltaS = ((curTemperature - 15) / (1 + 0.0162 * (curTemperature - 15)))
            * (b0 + b1 * Math.pow(rt, 1/2) + b2 * rt + b3 * Math.pow(rt, 3/2)
            + b4 * Math.pow(rt, 2) + b5 * Math.pow(rt, 5/2));
        
        // Calculate current practical salinity
        curSalinity = a0 + a1 * Math.pow(rt, 1/2) + a2 * rt 
            + a3 * Math.pow(rt, 3/2) + a4 * Math.pow(rt, 2) 
            + a5 * Math.pow(rt, 5/2) + deltaS;
        
        /*   Holy crap, man.  */
        
        console.addLine("PRACTICAL SALINITY SET TO " + curSalinity);
    }
    //-[END METHOD calculatePracticalSalinity]----------------------------------
    
    //-[BEGIN METHOD evaluateConditions]----------------------------------------
    private void evaluateConditions()
    {
        if(curConductivity < minConductivity)
            setConditionStatus("Current system conductivity is too low.", true);
        else if(curConductivity > maxConductivity)
            setConditionStatus("Current system conductivity is too high.", true);
        else if(curSalinity < 2)
            setConditionStatus("Current system salinity is below the cutoff.", true);
        else if(curSalinity > 42)
            setConditionStatus("Current system salinity is above the cutoff.", true);
        else
            setConditionStatus("Current system conditions are normal.", false);
    }
    //-[END METHOD evaluateConditions]------------------------------------------
    
    //-[BEGIN METHOD handleAdjustConductivityClicked]---------------------------
    public void handleAdjustConductivityClicked()
    {
        /*  This function depends on two things:  first, the constructed dialog
        MUST be modal, and two, the dialog CANNOT be forked off into its own
        thread.  Note that this method works because the dialog created here
        gets GC'ed when it leaves this scope, so when this method gets called
        again, we're constructing a brand-new dialog each time.  */
        
        dialog = new FOShSalinityModule.AdjustmentDialog(this, curConductivity);
        setConductivityManually(dialog.getSetValue());
    }
    //-[END METHOD handleAdjustConductivityClicked]-----------------------------
    
    //-[BEGIN METHOD readConductivityProbe]-------------------------------------
    private void readConductivityProbe()
    {
        // Candy for the console
        console.addLine("Reading conductivity probe...");
        
        int min = 53060 - RANDOM_LIMIT;
        int max = 53060 + RANDOM_LIMIT;
        setConductivity(new Random().nextInt((max - min) + 1) + min);
    }
    //-[END METHOD readConductivityProbe]---------------------------------------
    
    //-[BEGIN METHOD requestConductivityLimits]---------------------------------
    private void requestConductivityLimits()
    {
        // Candy for the console
        console.addLine("Requesting conductivity limits...");
        
        // TODO: Add network support here, these are just test values
        minConductivity = 51700;
        maxConductivity = 54400;
        
        // More candy
        console.addLine("CONDUCTIVITY LOWER LIMIT SET TO " + minConductivity);
        console.addLine("CONDUCTIVITY UPPER LIMIT SET TO " + maxConductivity);
    }
    //-[END METHOD requestConductivityLimits]-----------------------------------
    
    //-[BEGIN METHOD requestTemperature]----------------------------------------
    private void requestTemperature()
    {
        // Candy
        console.addLine("Requesting temperature...");
        
        //TODO:  flesh this out with network support
        setTemperature(25);
    }
    //-[END METHOD requestTemperature]------------------------------------------
    
    //-[BEGIN METHOD setConditionStatusMessage]---------------------------------
    public void setConditionStatusMessage(String s)
    { conditionLabel.setText(s); }
    //-[END METHOD setConditionStatusMessage]-----------------------------------
    
    //-[BEGIN METHOD setConditionStatus]----------------------------------------
    private void setConditionStatus(String s, boolean abnormal)
    {
        conditionLabel.setText(s);
        if(abnormal)
            conditionLabel.setIcon(iconRed);
        else
            conditionLabel.setIcon(iconGreen);
    }
    //-[END METHOD setConditionStatus]------------------------------------------
    
    //-[BEGIN METHOD setConductivity]-------------------------------------------
    private void setConductivity(int c)
    {
        // Not important, just candy for the console
        if(curConductivity == c)
            console.addLine("SET CONDUCTIVITY:  NO CHANGE");
        else
            console.addLine("SET CONDUCTIVITY:  FROM " + curConductivity + " TO " + c);
        
        curConductivity = c;
        calculatePracticalSalinity();
        evaluateConditions();
        updateLabels();
    }
    //-[END METHOD setConductivity]---------------------------------------------
    
    //-[BEGIN METHOD setConductivityManually]-----------------------------------
    private void setConductivityManually(int c)
    {
        // Not important, just candy for the console
        if(curConductivity == c)
            console.addLine("MANUAL SET CONDUCTIVITY:  NO CHANGE");
        else
            console.addLine("MANUAL SET CONDUCTIVITY:  FROM " + curConductivity + " TO " + c);
        
        curConductivity = c;
        calculatePracticalSalinity();
        evaluateConditions();
        updateLabels();
    }
    //-[END METHOD setConductivityManually]-------------------------------------
    
    //-[BEGIN METHOD setTemperature]--------------------------------------------
    private void setTemperature(int t)
    {
        /*  This is nothing but an internal convenience method that sets both
        the real value, and the label text so that it can all be done in one
        step.  */
        
        // Not important, just candy for the console
        if(curTemperature == t)
            console.addLine("SET TEMPERATURE:  NO CHANGE");
        else
            console.addLine("SET TEMPERATURE:  FROM " + curTemperature + " TO " + t);
        
        curTemperature = t;
        updateLabels();
    }
    //-[END METHOD setTemperature]----------------------------------------------
    
    //-[BEGIN METHOD setupFrame]------------------------------------------------
    private void setupFrame()
    {
        setSize(new Dimension(640, 640));
        setLocationRelativeTo(null);
        setTitle("FOSh Salinity Module");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    //-[END METHOD setupFrame]--------------------------------------------------
    
    //-[BEGIN METHOD setupGUI]--------------------------------------------------
    private void setupGUI()
    {
        /*  Handwritten GUI code, in genral, is difficult to read unless copious
        amounts of whitespace is added to spread individual elements out, and 
        unconventional line-lengths are used.  In the interest of not having a 
        GUI function that's eight-thousand lines long, these methods are not 
        used here; and this code is, consequently, hard to read.  */
        
        // Setup/configure data members
        iconGreen = new ImageIcon("src/Images/icon-green.png");
        iconRed = new ImageIcon("src/Images/icon-red.png");
        conditionLabel = new JLabel(iconRed);
            conditionLabel.setText("Abnormal");
        conductivityLabelT = new JLabel();
        conductivityLabelC = new JLabel();
        temperatureLabel = new JLabel();
        salinityLabel = new JLabel();
        timerLabel = new JLabel("60s");
        
//<editor-fold defaultstate="collapsed" desc="Top Panel Code">
        // Setup/configure/add top panel
        JLabel conductivityLabelHeaderT = new JLabel("Conductivity:");
            conductivityLabelHeaderT.setPreferredSize(new Dimension(128, 18));
        JPanel topSubPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topSubPanel1.add(conductivityLabelHeaderT);
            topSubPanel1.add(conductivityLabelT);
        JButton adjustConductivityButton = new JButton("Manually Adjust Conductivity");
            adjustConductivityButton.setActionCommand("CMD_ADJCON");
            adjustConductivityButton.addActionListener(this);
        JPanel topSubPanel2 = new JPanel(new FlowLayout());
            topSubPanel2.add(adjustConductivityButton);
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
            topPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Probe Data"));
            topPanel.add(topSubPanel1);
            topPanel.add(topSubPanel2);
            
        add(topPanel, BorderLayout.NORTH);
//</editor-fold>
            
//<editor-fold defaultstate="collapsed" desc="Center Panel Code">
        // Setup/configure/add center panel
        JLabel conductivityLabelHeaderC = new JLabel("Current System Conductivity:");
            conductivityLabelHeaderC.setPreferredSize(new Dimension(256, 18));
        JPanel centerSubPanel1a = new JPanel(new FlowLayout(FlowLayout.LEFT));
            centerSubPanel1a.add(conductivityLabelHeaderC);
            centerSubPanel1a.add(conductivityLabelC);
        JLabel temperatureLabelHeader = new JLabel("Current System Temperature:");
            temperatureLabelHeader.setPreferredSize(new Dimension(256, 18));
        JPanel centerSubPanel1b = new JPanel(new FlowLayout(FlowLayout.LEFT));
            centerSubPanel1b.add(temperatureLabelHeader);
            centerSubPanel1b.add(temperatureLabel);
        JLabel salinityLabelHeader = new JLabel("Current System Practical Salinity:");
            salinityLabelHeader.setPreferredSize(new Dimension(256, 18));
        JPanel centerSubPanel1c = new JPanel(new FlowLayout(FlowLayout.LEFT));
            centerSubPanel1c.add(salinityLabelHeader);
            centerSubPanel1c.add(salinityLabel);
        JLabel pollTimeLabelHeader = new JLabel("Conductivity Sensor Poll Interval: ");
            pollTimeLabelHeader.setPreferredSize(new Dimension(256, 18));
        JPanel centerSubPanel1d = new JPanel(new FlowLayout(FlowLayout.LEFT));
            centerSubPanel1d.add(pollTimeLabelHeader);
            centerSubPanel1d.add(timerLabel);
        JPanel centerSubPanel1 = new JPanel();
            BoxLayout blo1 = new BoxLayout(centerSubPanel1, BoxLayout.PAGE_AXIS);
            centerSubPanel1.setLayout(blo1);
            centerSubPanel1.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Salinity Module"));
            centerSubPanel1.add(centerSubPanel1a);
            centerSubPanel1.add(centerSubPanel1b);
            centerSubPanel1.add(centerSubPanel1c);
            centerSubPanel1.add(centerSubPanel1d);
        JPanel centerSubPanel2 = new JPanel(new GridLayout(1, 1));
            centerSubPanel2.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Console"));
            centerSubPanel2.add(new JScrollPane(console));
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
            centerPanel.add(centerSubPanel1);
            centerPanel.add(centerSubPanel2);
        
        add(centerPanel, BorderLayout.CENTER);
//</editor-fold>
        
//<editor-fold defaultstate="collapsed" desc="Bottom Panel Code">
        // Setup/configure/add bottom panel
        JButton exitButton = new JButton("Close Module");
        exitButton.setActionCommand("CMD_EXIT");
        exitButton.addActionListener(this);
        JPanel bottomSubPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomSubPanel1.add(conditionLabel);
        JPanel bottomSubPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomSubPanel2.add(exitButton);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(bottomSubPanel1);
        bottomPanel.add(bottomSubPanel2);
        add(bottomPanel, BorderLayout.SOUTH);
//</editor-fold>
        
        // Final businesses
        getRootPane().setDefaultButton(exitButton);
        exitButton.requestFocus();
    }
    //-[END METHOD setupGUI]----------------------------------------------------
    
    //-[BEGIN METHOD setupTimer]------------------------------------------------
    private void setupTimer()
    {
        timer = new Timer(60000, this);
            timer.setActionCommand("CMD_POLL");
            timer.setRepeats(true);
            timer.setInitialDelay(60000);
            timer.start();
    }
    //-[END METHOD setupTimer]--------------------------------------------------
    
    //-[BEGIN METHOD updateLabels]----------------------------------------------
    private void updateLabels()
    {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        conductivityLabelT.setText(nf.format(curConductivity) + " μS/cm");
        conductivityLabelC.setText(nf.format(curConductivity) + " μS/cm");
        temperatureLabel.setText(nf.format(curTemperature) + "° C");
        salinityLabel.setText(nf.format(curSalinity) + "");
    }
    //-[END METHOD updateLabels]------------------------------------------------

}
//=[END CLASS PrimaryFrame]=====================================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
