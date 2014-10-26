import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

public class RangeDialog extends JDialog
						 implements ActionListener
{
	JTextField minTF;
	JTextField maxTF;
	JLabel     minLabel;
	JLabel     maxLabel;
	PHFrame    pHFrame;
	JButton    setButton;
	JButton    exitButton;

	public RangeDialog(Frame owner)
	{
		super(owner);
		pHFrame = (PHFrame)owner;

		JPanel mainPanel	= new JPanel(new GridLayout(3,1));
		JPanel minPanel 	= new JPanel(new FlowLayout());
		JPanel maxPanel 	= new JPanel(new FlowLayout());
		JPanel buttonPanel  = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		minLabel = new JLabel("Min pH:");
		maxLabel = new JLabel("Max pH:");

		minTF = new JTextField(10);
		minTF.setEditable(true);
		minTF.setInputVerifier(new ValueVerifier());

		maxTF = new JTextField(10);
		maxTF.setEditable(true);
		maxTF.setInputVerifier(new ValueVerifier());

		setButton = new JButton("Set");
		setButton.addActionListener(this);
		setButton.setActionCommand("CMD_SET");

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		exitButton.setActionCommand("CMD_EXIT");
		exitButton.setVerifyInputWhenFocusTarget(false);

		minPanel.add(minLabel);
		minPanel.add(minTF);

		maxPanel.add(maxLabel);
		maxPanel.add(maxTF);

		buttonPanel.add(setButton);
		buttonPanel.add(exitButton);

		mainPanel.add(minPanel);
		mainPanel.add(maxPanel);
		mainPanel.add(buttonPanel);
		getContentPane().add(mainPanel);

		setupDialogFrame();
	}

	//==================================================================================

	void setupDialogFrame()
    {
    	Toolkit tk;
       	Dimension d;

       	tk = Toolkit.getDefaultToolkit();
       	d = tk.getScreenSize();
       	setSize(d.width/3, d.height/4);
       	setLocation(d.width/3, d.height/3);

       	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
       	setTitle("Input Range");
       	setModal(true);
       	setResizable(true);

       	setVisible(true);
    }

    //===================================================================================

    public void actionPerformed(ActionEvent e)
    {
    	String cmd = e.getActionCommand();

    	if(cmd.equals("CMD_SET"))
    	{
    		if(minTF.getText().trim().equals("") || maxTF.getText().trim().equals(""))
    		{
    			minTF.requestFocus();
    			new JOptionPane().showMessageDialog(this, "One or more of the fields is empty. Please input a value between 0 and 14 for the fields", "EMPTY FIELDS", JOptionPane.ERROR_MESSAGE);			
    		}

    		else if(Double.parseDouble(minTF.getText().trim()) > Double.parseDouble(maxTF.getText().trim()))
    		{
    			minTF.requestFocus();
    			new JOptionPane().showMessageDialog(this, "Min field should be at least .3 less than the max", "INVALID MIN", JOptionPane.ERROR_MESSAGE);
    		}

    		else
    		{
    			pHFrame.minpH = Double.parseDouble(minTF.getText().trim());
    			pHFrame.maxpH = Double.parseDouble(maxTF.getText().trim());
    			dispose();
    		}
    	}

    	else if(cmd.equals("CMD_EXIT"))
    	{
    		dispose();
    	}
    }
}