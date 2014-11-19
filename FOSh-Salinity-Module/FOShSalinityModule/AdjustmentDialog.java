/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   FOSh-Salinity-Module
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2014-10-03
   FILENAME:                  AdjustmentDialog.java
   PURPOSE:                   Modal dialog for changing conductivity manually
   VERSION:                   20141004-0027
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
//package FOShSalinityModule;

//=[BEGIN IMPORTS]==============================================================
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
//=[END IMPORTS]================================================================

//=[BEGIN CLASS AdjustmentDialog]===============================================
public class AdjustmentDialog extends JDialog
implements ActionListener
{
    //-[BEGIN DATA MEMBERS]-----------------------------------------------------
    private Component                   parent;
    private final int                   defaultValue;
    private int                         setValue;
    private JTextField                  conductivityTF;
    //-[END DATA MEMBERS]-------------------------------------------------------
    
    //-[BEGIN CONSTRUCTOR(S)]---------------------------------------------------
    AdjustmentDialog(Component c, int defValue)
    {
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        parent = c;
        defaultValue = defValue;
        setValue = defValue;
        
        setupGUI();
        setupDialog();
    }
    //-[END CONSTRUCTOR(S)]-----------------------------------------------------
    
    //-[BEGIN METHOD actionPerformed]-------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
        
        switch(cmd)
        {
            case "CMD_ACCEPT":
                handleAcceptClicked();
                break;
            case "CMD_CANCEL":
                setVisible(false);
                break;
            default:
                throw new UnsupportedOperationException("actionPerformed() in "
                    + "AdjustmentDialog encountered an unrecognized Action "
                    + "Command.");
        }
    }
    //-[END METHOD actionPerformed]---------------------------------------------
    
    //-[BEGIN METHOD getSetValue]-----------------------------------------------
    public int getSetValue()
    { return setValue; }
    //-[END METHOD getSetValue]-------------------------------------------------
    
    //-[BEGIN METHOD handleAcceptClicked]---------------------------------------
    private void handleAcceptClicked()
    {
        /*  We don't need to catch NumberFormatException in here because
        Integer.parseInt was already called (and the error checked for) in the
        InputValidator code.  We're just using parseInt here to do the actual
        String/int conversion.  */
        
        if(inputIsValid())
        {
            setValue = Integer.parseInt(conductivityTF.getText());
            setVisible(false);
        }
        else
            showErrorMessage("ERROR:  The specified value is not valid.");
    }
    //-[END METHOD handleAcceptClicked]-----------------------------------------
    
    //-[BEGIN METHOD inputIsValid]----------------------------------------------
    private boolean inputIsValid()
    {
        // Easy, just use the TF's own InputVerifier
        return conductivityTF.getInputVerifier().verify(conductivityTF);
    }
    //-[END METHOD inputIsValid]------------------------------------------------
    
    //-[BEGIN METHOD setupDialog]-----------------------------------------------
    private void setupDialog()
    {
        setSize(new Dimension(320, 130));
        setLocationRelativeTo(parent);
        setTitle("Manually Adjust Conductivity");
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    //-[END METHOD setupDialog]-------------------------------------------------
    
    //-[BEGIN METHOD setupGUI]--------------------------------------------------
    private void setupGUI()
    {
        conductivityTF = new JTextField();
            conductivityTF.setInputVerifier(
                new ConductivityInputVerifier());
            conductivityTF.setText("" + defaultValue);
            conductivityTF.setPreferredSize(new Dimension(128, 18));
        JLabel conductivityLabel = new JLabel(" μS/cm");
        JPanel topPanel = new JPanel(new FlowLayout());
            topPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Manual Adjustment"));
            topPanel.add(conductivityTF);
            topPanel.add(conductivityLabel);
        add(topPanel, BorderLayout.NORTH);
        
        JButton cancelButton = new JButton("Cancel");
            cancelButton.setActionCommand("CMD_CANCEL");
            cancelButton.addActionListener(this);
        JButton acceptButton = new JButton("Accept");
            acceptButton.setActionCommand("CMD_ACCEPT");
            acceptButton.addActionListener(this);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottomPanel.add(acceptButton);
            bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);
        
        getRootPane().setDefaultButton(acceptButton);
    }
    //-[END METHOD setupGUI]----------------------------------------------------
    
    //-[BEGIN METHOD showErrorMessage]------------------------------------------
    private void showErrorMessage(String message)
    {
        // Generic JOptionPane shower with customizable message
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    //-[END METHOD showErrorMessage]--------------------------------------------
}
//=[END CLASS AdjustmentDialog]=================================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡