import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.text.*;
import java.math.*;

public class TEMPFrame extends JFrame
                          implements ActionListener
{
     public static void main(String[] args){new TEMPFrame();}// Launcher for OS FRAME

    private TEMPCTOS             ctos;
    private DCRSimpleTextConsole console;
    private ImageIcon            iconGreen;
    private ImageIcon            iconRed;
    private JLabel               conditionLabeltemperature;
    private JLabel               temperatureLabel;
    public  Float                randomNum;
    public  Float                tempMax;
    public  Float                tempMin;
    public  Random               ran;
    public  Timer                randomGen;
    public  DecimalFormat        df;

    TEMPFrame()
    {
        console                       = new DCRSimpleTextConsole(DCRSimpleTextConsole.INS_BEG);

        iconGreen                     = new ImageIcon("icon-green.png");
        iconRed                       = new ImageIcon("icon-red.png");

        conditionLabeltemperature     = new JLabel(iconRed);

        randomNum                     = new Float(70);
        tempMax                       = new Float(80);
        tempMin                       = new Float(70);
        ran                           = new Random(1645);
        randomGen                     = new Timer(10000, this);

        temperatureLabel              = new JLabel(Float.toString(randomNum));

        JButton adjustButton          = new JButton("Manually Adjust Settings");
        JButton exitButton            = new JButton("Close");

        JPanel  topSubPanel1          = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  topSubPanel2          = new JPanel(new FlowLayout());
        JPanel  topPanel              = new JPanel(new GridLayout(1, 2));

        JPanel  centerSubPanel1       = new JPanel();
        JPanel  centerSubPanel1a      = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  centerSubPanel1b      = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  centerSubPanel1c      = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  centerSubPanel1d      = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel  centerSubPanel2       = new JPanel(new GridLayout(1, 1));
        JPanel  centerPanel           = new JPanel(new GridLayout(2, 1));

        JPanel  bottomSubPanel1       = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel  bottomSubPanel2       = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel  bottomPanel           = new JPanel(new GridLayout(1, 2));

        BoxLayout boxLay              = new BoxLayout(centerSubPanel1, BoxLayout.PAGE_AXIS);

        JLabel temperatureLabelHeader = new JLabel("Current System Temperature:");

        df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);

        adjustButton.setActionCommand("CMD_ADJUST");
        adjustButton.addActionListener(this);

        topSubPanel2.add(temperatureLabel);
        topSubPanel1.add(temperatureLabelHeader);
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Probe Data"));
        topPanel.add(topSubPanel1);
        topPanel.add(topSubPanel2);

        this.add(topPanel, BorderLayout.NORTH);


        centerSubPanel2.add(new JScrollPane(console));

        centerPanel.add(centerSubPanel2);

        this.add(centerPanel, BorderLayout.CENTER);

        exitButton.setActionCommand("CMD_EXIT");
        exitButton.addActionListener(this);

        bottomSubPanel1.add(new JLabel("Temperature Status:"));
        bottomSubPanel1.add(conditionLabeltemperature);

        bottomSubPanel2.add(exitButton);

        bottomPanel.add(bottomSubPanel1);
        bottomPanel.add(bottomSubPanel2);
        this.add(bottomPanel, BorderLayout.SOUTH);

        randomGen.setActionCommand("TIMER_FIRE");
        randomGen.start();

        getRootPane().setDefaultButton(exitButton);
        exitButton.requestFocus();
        this.setupFrame();

        ctos = new TEMPCTOS("127.0.0.1",12345,"Temp_Mod",this);

    }//-[END CONSTRUCTOR(S)]----------------------------------------------------





    void connected()
    {
       conditionLabeltemperature = new JLabel(iconGreen);
       this.repaint();
       console.addLine("Connected to OS module.");
    }










    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "CMD_NULL":
                break;
            case "CMD_EXIT":
                System.exit(1);
            case "CMD_ADJUST":
                console.addLine("Adjust...");
                break;
            case "CMD_BIOME":
                console.addLine("BIOME...");
                break;
            case "TIMER_FIRE":
                randomNum = ran.nextFloat()*(tempMax - tempMin) + 70;
                temperatureLabel.setText(df.format(randomNum));
                console.addLine("New Temperature Reading: " + df.format(randomNum));
                break;
            default:
                throw new UnsupportedOperationException("actionPerformed() in PrimaryFrame encountered an unrecognized Action Command.");
        }
    }//-[END METHOD actionPerformed]---------------------------------------------

    private void setupFrame()
    {
        this.setSize(new Dimension(800, 240));
        this.setLocationRelativeTo(null);
        this.setTitle("FOSh Temperature Module");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }//-[END METHOD setupFrame]--------------------------------------------------

}//end of class
