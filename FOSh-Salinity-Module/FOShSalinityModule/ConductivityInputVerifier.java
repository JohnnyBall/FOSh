/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   FOSh-Salinity-Module
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2014-10-02
   FILENAME:                  ConductivityInputVerifier.java
   PURPOSE:                   Input verifier for manual probe input
   VERSION:                   20141002-1839
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
package FOShSalinityModule;

//=[BEGIN IMPORTS]==============================================================
import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
//=[END IMPORTS]================================================================

//=[BEGIN CLASS ConductivityInputVerifier]======================================
public class ConductivityInputVerifier extends InputVerifier
{
    //-[BEGIN METHOD verify]----------------------------------------------------
    @Override
    public boolean verify(JComponent jc)
    {
        // Guilty until proven innocent
        boolean valid = false;
        
        JTextField tf = (JTextField)jc;
        String text = tf.getText().trim().toLowerCase();
        
        if(!text.equals(""))
        {
            try
            {
                int num = Integer.parseInt(text);
                
                if(num < 0)
                    throw new Exception("Conductivity cannot be less than "
                        + "zero.");
                
                tf.setBackground(Color.WHITE);
                valid = true;
            }
            catch(Exception e)
            {
                tf.setBackground(Color.RED);
                valid = false;
            }
        }
        else
        {
            tf.setBackground(Color.RED);
            valid = false;
        }
        
        return valid;
    }
    //-[END METHOD verify]------------------------------------------------------
}
//=[END CLASS ConductivityInputVerifier]========================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡