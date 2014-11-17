import java.io.*;
import java.awt.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import javax.swing.*;

public class CTM
                implements Runnable
{
  OSFrame server;
  Talker talker;
  String id;
//====================================================================================================================
  public CTM(Socket socket,String id,OSFrame server)throws IOException
  {
    try
    {
      this.id     = id;
      this.server = server;
      talker      = new Talker(socket,id);
      new Thread(this).start();
    }
    catch(IOException ioe)
    {
      System.out.println("Error with constructing the talker for the CTC");
      System.out.println("");
      ioe.printStackTrace();
    }
  }
  //====================================================================================================================
  public void  setID(String id)
  {
      this.id   = id;
      talker.id = id;
  }
  //====================================================================================================================
  public void sendMessage(String msgToSend)
  {
    try
      {
        talker.send(msgToSend);
      }
      catch(IOException ioe)
      {
        System.out.println("Error sending the message to the Client");
        System.out.println("");
        ioe.printStackTrace();
      }
  }
//====================================================================================================================
  public void run()
  {
     String   msg;
     String[] splitString;
     boolean  connected = true;
     while(connected)
     {
      try
      {
        msg = talker.recieve();

      }
      catch(IOException ioe)
      {
        connected = false;
        System.out.println("Connection to the Client: " + this.id + " has been lost or closed...");
        server.closeUsersCTC(id);
        //ioe.printStackTrace();
      }
     }//end of while
  }//end of method
}//end of CTC
