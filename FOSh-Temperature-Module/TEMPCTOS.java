import java.io.*;
import java.awt.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;

public class TEMPCTOS
                implements Runnable
{
  Talker  talker;
  TEMPFrame tf;
  String  msg;// Must be placed here to pass to chat client, other wise must be effectivly final.
  String[] splitString;// see above.
  String id;
//====================================================================================================================
  public TEMPCTOS(String serverName,int port,String id, TEMPFrame tf)
  {   try
      {
        this.id = id;
        talker  = new Talker(serverName,port,id);
        this.tf = tf;
        new Thread(this).start();
      }
      catch(IOException ioe)
      {
         System.out.println("Error connecting to OS, OS module might not be running!");
         System.out.println("");
         JOptionPane.showMessageDialog(null, "Error connecting to OS!", "Error connecting to OS please close and try again!", JOptionPane.ERROR_MESSAGE);
         System.exit(1);
         ioe.printStackTrace();
      }
  }//end of constructor
//====================================================================================================================
  public synchronized  void sendMessage(String msgToSend)
  {
    try
      {
        talker.send(msgToSend);
      }
      catch(IOException ioe)
      {
        System.out.println("Error sending message  to this server, connection might have been lost. ");
        ioe.printStackTrace();
      }
  }
//====================================================================================================================
  public void run()
  {
    boolean connected = true;
    try
      {
        while(connected)
        {
           msg = talker.recieve();
           if(msg.startsWith("+WHORU"))
           {
             this.sendMessage("+IAMA TEMP_MOD");
           }
           if(msg.startsWith("+CONNECTED"))
           {
            tf.connected();
           }
           else if(msg.startsWith("+REQ_TEMP"))
           {

           }
        }
        talker.close();// CLOSES CONNECTION TO TALKER IF  NOT CONNECTED!!
      }
      catch(IOException ioe)
      {
         connected = false;
         System.out.println("Error connecting to server from the CTS of this Client, the connection was established but might have timed out!");
         JOptionPane.showMessageDialog(null, "Connection timed out", "Connection to the OS module has been lost, the program will now close. Please relaunch to start again!", JOptionPane.ERROR_MESSAGE);
         System.out.println("");
         ioe.printStackTrace();
         System.exit(1);
      }
  }
//====================================================================================================================
}
