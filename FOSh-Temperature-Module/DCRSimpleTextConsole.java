import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

public class DCRSimpleTextConsole extends JTextArea
{
    public static final int             INS_BEG = 0;
    public static final int             INS_END = 1;

    private final boolean               insertAtTop;

    DCRSimpleTextConsole(int insertMode)
    {
        setFont(new Font("Courier New", Font.PLAIN, 12));
        setLineWrap(false);
        setEditable(false);

        // Determine if we're adding lines at top or bottom
        insertAtTop = insertMode == INS_BEG;

        addLine("Console initialized.");
    }//end of constructor

    public final void addLine(String str)
    {
        // Create a simple timestamp
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

        // Append timestamp and add line at appropriate position
        if(insertAtTop)
        {
            insert(timeStamp + " " + str + "\n", 0);
            setCaretPosition(0);
        }
        else
        {
            append(timeStamp + " " + str + "\n");
            setCaretPosition(getDocument().getLength());
        }
    }//end of addline
}//end of class
