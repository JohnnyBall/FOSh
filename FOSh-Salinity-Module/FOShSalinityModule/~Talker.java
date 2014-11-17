/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   GenUtils
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2011-05-11
   FILENAME:                  Talker.java
   PURPOSE:                   Manages all actual network traffic
   VERSION:                   20110511-2316
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
package FOShSalinityModule; //Added 2014-10-02 for compatibility -DCR

/*  Changes added on 2014-10-02 to support features in FOSh project:
        * Added support for text console object in main GUI frame.
*/

//=[BEGIN IMPORTS]==============================================================
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
//=[END IMPORTS]================================================================

//=[BEGIN CLASS Talker]=========================================================
public class Talker
{
    //-[BEGIN MEMBER DATA]------------------------------------------------------
    private FOShSalinityModule.DCRSimpleTextConsole     console;
    
    private BufferedReader              reader;
    private DataOutputStream            dos;
    private Socket                      socket;
    private String                      talkerID;
    //-[END MEMBER DATA]--------------------------------------------------------
    
    //-[BEGIN CONSTRUCTOR(S)]---------------------------------------------------
    Talker(String server, int port, String id, FOShSalinityModule.DCRSimpleTextConsole c)
    throws UnknownHostException, IOException
    {
        talkerID = id;
        console = c;
        
        try
        {
            socket = new Socket(server, port);
            dos = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            throw new UnknownHostException("Talker was unable to create a "
                + "Socket using the provided host name.");
        }
        catch(IOException e)
        {
            throw new IOException("Talker was unable to obtain functional "
                + "data streams from the Socket.");
        }
    }
    
    Talker(Socket s, String id, FOShSalinityModule.DCRSimpleTextConsole c)
    throws IOException
    {
        talkerID = id;
        socket = s;
        console = c;
        
        try
        {
            dos = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e)
        {
            throw new IOException("Talker was unable to obtain functional "
                + "data streams from the provided Socket.");
        }
    }
    //-[END CONSTRUCTOR(S)]-----------------------------------------------------
    
    //-[BEGIN METHOD expect]----------------------------------------------------
    public void expect(String s)
    throws Exception
    {
        String rcv = receive();
        
        if(!rcv.startsWith(s))
            throw new Exception("Talker received a string that did not match "
                + "the expected value.");
    }
    //-[END METHOD expect]------------------------------------------------------
    
    //-[BEGIN METHOD receive]---------------------------------------------------
    public String receive()
    throws IOException
    {
        String rcv;
        
        try
        {
            rcv = reader.readLine();
        }
        catch(IOException e)
        {
            throw new IOException("Talker was unable to read from the Socket's "
                + "input stream.");
        }
        
        console.addLine(talkerID + " RECEIVE: " + rcv);
        return rcv;
    }
    //-[END METHOD receive]-----------------------------------------------------
    
    //-[BEGIN METHOD send]------------------------------------------------------
    public void send(String s)
    throws IOException
    {
        console.addLine(talkerID + " SEND: " + s);
        
        s = s.concat("\n");
        
        try
        {
            dos.writeBytes(s);
        }
        catch(IOException e)
        {
            throw new IOException("Talker was unable to write to the Socket's "
                + "output stream.");
        }
    }
    //-[END METHOD send]--------------------------------------------------------
    
    //-[BEGIN METHOD setID]-----------------------------------------------------
    public void setID(String id)
    { talkerID = id; }
    //-[END METHOD setID]-------------------------------------------------------
}
//=[END CLASS Talker]===========================================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡