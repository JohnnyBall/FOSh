import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;

public class OSFrame extends JFrame
                          implements ActionListener
{
    private DCRSimpleTextConsole console;
    private ImageIcon            iconGreen;
    private ImageIcon            iconRed;
    private JLabel               conditionLabeltemperature;
    private JLabel               conditionLabelSalinity;
    private JLabel               conditionLabelPH;
    private JLabel               temperatureLabel;
    private JLabel               salinityLabel;
    private JLabel               phLabel;

    OSFrame()
    {
        console                       = new DCRSimpleTextConsole(DCRSimpleTextConsole.INS_BEG);

        iconGreen                     = new ImageIcon("icon-green.png");
        iconRed                       = new ImageIcon("icon-red.png");

        conditionLabeltemperature     = new JLabel(iconRed);
        conditionLabelSalinity        = new JLabel(iconRed);
        conditionLabelPH              = new JLabel(iconRed);
        temperatureLabel              = new JLabel("TEMP_HERE");
        salinityLabel                 = new JLabel("SALINITY_HERE");
        phLabel                       = new JLabel("PH_HERE");

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
        JLabel salinityLabelHeader    = new JLabel("Current System Salinity:");
        JLabel phLabelHeader          = new JLabel("Current System PH:");

        adjustButton.setActionCommand("CMD_ADJUST");
        adjustButton.addActionListener(this);

        topSubPanel2.add(adjustButton);
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Probe Data"));
        topPanel.add(topSubPanel1);
        topPanel.add(topSubPanel2);

        this.add(topPanel, BorderLayout.NORTH);

        temperatureLabelHeader.setPreferredSize(new Dimension(256, 18));
        centerSubPanel1b.add(temperatureLabelHeader);
        centerSubPanel1b.add(temperatureLabel);

        salinityLabelHeader.setPreferredSize(new Dimension(256, 18));
        centerSubPanel1c.add(salinityLabelHeader);
        centerSubPanel1c.add(salinityLabel);

        phLabelHeader.setPreferredSize(new Dimension(256, 18));
        centerSubPanel1d.add(phLabelHeader);
        centerSubPanel1d.add(phLabel);

        centerSubPanel1.setLayout(boxLay);
        centerSubPanel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "OS Module"));
        centerSubPanel1.add(centerSubPanel1a);
        centerSubPanel1.add(centerSubPanel1b);
        centerSubPanel1.add(centerSubPanel1c);
        centerSubPanel1.add(centerSubPanel1d);


        centerSubPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Console"));
        centerSubPanel2.add(new JScrollPane(console));

        centerPanel.add(centerSubPanel1);
        centerPanel.add(centerSubPanel2);

        this.add(centerPanel, BorderLayout.CENTER);

        exitButton.setActionCommand("CMD_EXIT");
        exitButton.addActionListener(this);

        bottomSubPanel1.add(new JLabel("Temperature Status:"));
        bottomSubPanel1.add(conditionLabeltemperature);
        bottomSubPanel1.add(new JLabel("Salinity Status:"));
        bottomSubPanel1.add(conditionLabelSalinity);
        bottomSubPanel1.add(new JLabel("PH Status:"));
        bottomSubPanel1.add(conditionLabelPH);

        bottomSubPanel2.add(exitButton);

        bottomPanel.add(bottomSubPanel1);
        bottomPanel.add(bottomSubPanel2);
        this.add(bottomPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(exitButton);
        exitButton.requestFocus();
        setJMenuBar(newMenuBar());
        this.setupFrame();

    }//-[END CONSTRUCTOR(S)]----------------------------------------------------

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
            default:
                throw new UnsupportedOperationException("actionPerformed() in PrimaryFrame encountered an unrecognized Action Command.");
        }
    }//-[END METHOD actionPerformed]---------------------------------------------

    private void setupFrame()
    {
        this.setSize(new Dimension(800, 640));
        this.setLocationRelativeTo(null);
        this.setTitle("FOSh Operating System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }//-[END METHOD setupFrame]--------------------------------------------------

    //MENUBAR SECTION FOR DISPLAYING THE MENU
    private JMenuBar newMenuBar()
    {
        JMenuBar menuBar;
        JMenu    subMenu;

        menuBar = new JMenuBar();
        subMenu = new JMenu("Menu");
        subMenu.add(newItem("Adjust","CMD_ADJUST",this,"Adjusts the settings."));
        subMenu.add(newItem("Exit","CMD_EXIT",this,"Closes the program."));
        menuBar.add(subMenu);
        subMenu = new JMenu("Biome");
        subMenu.add(newItem("Fresh Water","CMD_BIOME",this,"Fresh Water Biome settings"));
        subMenu.add(newItem("Salt Water","CMD_BIOME",this,"Salt Water Biome settings"));
        menuBar.add(subMenu);

        return menuBar;
    }//-[END METHOD newMenuBar]--------------------------------------------------

    private JMenuItem newItem(String label,String actionCommand, ActionListener menuListener, String toolTipText)
    {
        JMenuItem mItem;
        mItem = new JMenuItem(label);
        mItem.getAccessibleContext().setAccessibleDescription(toolTipText);
        mItem.setToolTipText(toolTipText);
        mItem.setActionCommand(actionCommand);
        mItem.addActionListener(menuListener);
        return mItem;
    }//-[END METHOD newItem]--------------------------------------------------
}//end of class
