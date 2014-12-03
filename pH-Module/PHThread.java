import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;

class PHThread implements Runnable
{
	PHFrame owner;

	public PHThread(PHFrame frame)
	{
		owner = frame;
	}

	public void run()
	{
		try
		{
			while(true)
			{
				owner.pollPH();
				Thread.sleep(2000);
			}
		}

		catch(InterruptedException ie)
		{
			owner.console.addLine("Error encountered during polling. pH Module offline. Please Reset...");
		}
	}
}