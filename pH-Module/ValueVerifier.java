import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;

public class ValueVerifier extends InputVerifier
{
	public ValueVerifier(){}

	@Override
	public boolean verify(JComponent input)
	{
		if(((JTextField)input).getText().trim().equals(""))
            return true;

        else
        {
        	if(((JTextField)input).getText().trim().indexOf('.') != ((JTextField)input).getText().trim().lastIndexOf('.'))
        	{
        		new JOptionPane().showMessageDialog(null, "Values entered should have no more than 1 . ", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        		return false;
        	}

        	else if(!((JTextField)input).getText().trim().matches("^[0-9. ]*$"))
        	{
        		new JOptionPane().showMessageDialog(null, "No alphabetic character or punctuations other than . should be entered", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        		return false;
        	}

        	else
        		return true;
        }
	}
}