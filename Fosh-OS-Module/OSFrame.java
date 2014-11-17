import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;



//package WaterTypeClass<e>;
/*
I've got no strings
To hold me down
To make me fret, or make me frown
I had strings
But now I'm free
There are no strings on me
*/

public class OSFrame extends JFrame
                          implements ActionListener
{

    public static void main(String[] args)
    {
        OSFrame osMod = new OSFrame();
        osMod.accept();
        System.out.println("IAMTHESERVER!");
    }// Launcher for OS FRAME

    private WaterTypeClass<BiomeClass>       saltWater;
    private WaterTypeClass<BiomeClass>       freshWater;
    private BiomeClass                       selectedBiome;
    private Talker                           talker;

    private ServerSocket                     serverSocket;
    private CTM                              tempCTM;
    private CTM                              salCTM;
    private CTM                              phCTM;



    private DCRSimpleTextConsole             console;
    private ImageIcon                        iconGreen;
    private ImageIcon                        iconRed;
    private JLabel                           conditionLabeltemperature;
    private JLabel                           conditionLabelSalinity;
    private JLabel                           conditionLabelPH;
    private JLabel                           temperatureLabel;
    private JLabel                           salinityLabel;
    private JLabel                           phLabel;

    OSFrame()
    {

        try
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

        JButton exitButton            = new JButton("Close");

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

        saltWater                     = new WaterTypeClass<BiomeClass>("Fresh Water");
        freshWater                    = new WaterTypeClass<BiomeClass>("Salt Water");
        serverSocket                  = new ServerSocket(12345);

        saltWater.loadFile();
        freshWater.loadFile();

        setJMenuBar(newMenuBar());
        this.setupFrame();
        }//end of first try
        catch(IOException IOE){ console.addLine("Error Opening Biomes.");}
    }//-[END CONSTRUCTOR(S)]----------------------------------------------------





    void accept()
    {
        Socket socket;
        CTM    ctm;
        while(true)
        {
          try
          {
            socket  = serverSocket.accept();
            ctm = new CTM(socket,"ctm",this);
            ctm.sendMessage("+WHORU");
          }
          catch(IOException ioe)
          {
            System.out.println("Error with constructing the socket in .accept()");
            ioe.printStackTrace();
          }
        }
    }//-[END OF ACCEPT]----------------------------------------------------

    void connectTemp(CTM temperaryCTM)
    {
       tempCTM                   = temperaryCTM;
       conditionLabeltemperature.setIcon(iconGreen);
       console.addLine("Temperature module connected.");
       this.repaint();
       tempCTM.sendMessage("+CONNECTED");
       tempCTM.sendMessage("+REQ_TEMP");
    }

    void connectSal(CTM temperaryCTM)
    {
       salCTM                 = temperaryCTM;
       conditionLabelSalinity.setIcon(iconGreen);
       console.addLine("Salinity module connected.");
       this.repaint();
       salCTM.sendMessage("+CONNECTED");
       salCTM.sendMessage("+REQ_SAL");
    }

    void connectPH(CTM temperaryCTM)
    {
       phCTM            = temperaryCTM;
       conditionLabelPH.setIcon(iconGreen);
       console.addLine("PH module connected.");
       this.repaint();
       phCTM.sendMessage("+CONNECTED");
       phCTM.sendMessage("+REQ_PH");
    }

    void disconnectMod(String id)
    {
        System.out.println("Disconnecting " +id);
        if(id.equals("TEMP_MOD"))
        {
          System.out.println("if " +id);
          conditionLabeltemperature.setIcon(iconRed);
          console.addLine("Temperature module disconnected.");
          tempCTM = null;
        }
        else if(id.equals("SALINITY_MOD"))
        {
          System.out.println("if " +id);
          conditionLabelSalinity.setIcon(iconRed);
          console.addLine("Salinity module disconnected.");
          tempCTM = null;
        }
        else if(id.equals("PH_MOD"))
        {
          System.out.println("if " +id);
          conditionLabelPH.setIcon(iconRed);
          console.addLine("PH module disconnected.");
          tempCTM = null;
        }

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
            case "Fresh_0":
                selectedBiome = freshWater.elementAt(0);
                console.addLine("Switched Biome to " +selectedBiome.biomeName);
                break;
            case "Fresh_1":
                selectedBiome = freshWater.elementAt(0);
                console.addLine("Switched Biome to " +selectedBiome.biomeName);
                break;
            case "Salt_0":
                selectedBiome = saltWater.elementAt(0);
                console.addLine("Switched Biome to " +selectedBiome.biomeName);
                break;
            case "Salt_1":
                selectedBiome = saltWater.elementAt(1);
                console.addLine("Switched Biome to " +selectedBiome.biomeName);
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
        Iterator<BiomeClass> it;
        JMenuBar   menuBar;
        JMenu      subMenu;
        JMenu      tempMenu;
        BiomeClass tempBiome;

        int count = 0;

        menuBar = new JMenuBar();
        subMenu = new JMenu("Menu");
        subMenu.add(newItem("Exit","CMD_EXIT",this,"Closes the program."));
        menuBar.add(subMenu);
        subMenu = new JMenu("Water Menu");

        tempMenu = new JMenu("Fresh Water");
        subMenu.add(tempMenu);

        it  = freshWater.listIterator();
        while(it.hasNext())
        {
            tempBiome = it.next();
            tempMenu.add(newItem(tempBiome.biomeName,"Fresh_"+count,this,"Selects "+tempBiome.biomeName+" biome..."));
            count++;
        }
        count = 0;

        tempMenu = new JMenu("Salt Water");
        subMenu.add(tempMenu);

        it  = saltWater.listIterator();
        while(it.hasNext())
        {
           tempBiome = it.next();
           tempMenu.add(newItem(tempBiome.biomeName,"Salt_"+count,this,"Selects "+tempBiome.biomeName+" biome..."));
           count++;
        }
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
