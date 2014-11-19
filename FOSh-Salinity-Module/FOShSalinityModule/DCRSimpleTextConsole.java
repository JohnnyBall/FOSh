/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   GenUtils
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2012-10-13
   FILENAME:                  DCRSimpleTextConsole.java
   PURPOSE:                   A plain text console for output, whatever.
   VERSION:                   20141002-2345
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
//package FOShSalinityModule; //Added 2014-10-02 for compatibility -DCR

/*  2014-10-02:  Changed a few things with Date and SimpleDateFormat to bring 
the class more "up to date," fingerquotes.  */

//=[BEGIN IMPORTS]==============================================================
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;
//=[END IMPORTS]================================================================

//=[BEGIN CLASS DCRSimpleTextConsole]===========================================
public class DCRSimpleTextConsole extends JTextArea
{
    //-[BEGIN MEMBER DATA]------------------------------------------------------
    public static final int             INS_BEG = 0;
    public static final int             INS_END = 1;
    
    private final boolean               insertAtTop;
    //-[END MEMBER DATA]--------------------------------------------------------
    
    //-[BEGIN CONSTRUCTOR(S)]---------------------------------------------------
    DCRSimpleTextConsole(int insertMode)
    {
        // Set some defaults
        setFont(new Font("Courier New", Font.PLAIN, 12));
        setLineWrap(false);
        setEditable(false);
        
        // Determine if we're adding lines at top or bottom
        insertAtTop = insertMode == INS_BEG;
        
        // Throw a message in there, just for the taste of it
        addLine("Console initialized.");
    }
    //-[END CONSTRUCTOR(S)]-----------------------------------------------------
    
    //-[BEGIN METHOD addLine]---------------------------------------------------
    public final void addLine(String s)
    {
        // Create a simple timestamp
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        
        // Append timestamp and add line at appropriate position
        if(insertAtTop)
        {
            insert(timeStamp + " " + s + "\n", 0);
            setCaretPosition(0);
        }
        else
        {
            append(timeStamp + " " + s + "\n");
            setCaretPosition(getDocument().getLength());
        }
    }
    //-[END METHOD addLine]-----------------------------------------------------
    
}
//=[END CLASS DCRSimpleTextConsole]=============================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡