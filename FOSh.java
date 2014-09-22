//This is just a basic prototype for the interface, it can be changed at any time
//Started 9/21/14 @ 7:45 P.M.
//Dylan Davis

import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
//=============================================================================================================
		public class FOSh extends JFrame implements ActionListener
			{
				JButton rightButton;
				JButton leftButton;
				JButton upButton;
				JButton downButton;
				JButton selectButton;//These are for directions, I believe we said that there would be a keyboard on the interface and these buttons would be to move around and select

				/*JList list;
				DefaultListModel listModel;
				These would be for our database of fish, I plan on us creating a class called fish and it would just have a list of the fish.*/

				float temperature;
				float pH;
				float salinity;//Variables for what our algorithims are going to do

				Container osContainer;
				JPanel buttonPanel;

//-------------------------------------------------------------------------------------------------------------
				public static void main(String[]args)
					{
						new FOSh();
					}
//-------------------------------------------------------------------------------------------------------------
				FOSh()
				{
					leftButton=new JButton("Left");			//In an actual model these would be to the side of the screen or somewhere
					rightButton=new JButton("Right");		//relatively close so that you could interact with the screen.
					upButton=new JButton("Up");				//In the VM I plan on representing them with arrow pictures.
					downButton=new JButton("Down");
					selectButton=new JButton("Select");

					buttonPanel=new JPanel(new GridLayout(3,3));
					buttonPanel.add(new JPanel());
					buttonPanel.add(upButton);
					buttonPanel.add(new JPanel());
					buttonPanel.add(leftButton);
					buttonPanel.add(selectButton);
					buttonPanel.add(rightButton);
					buttonPanel.add(new JPanel());
					buttonPanel.add(downButton);
					buttonPanel.add(new JPanel());


					osContainer=getContentPane();
					osContainer.add(buttonPanel,BorderLayout.EAST);
					setupMainFrame();
				}
					public void actionPerformed(ActionEvent e)
						{
						}


					void setupMainFrame()
						{
							Toolkit tk;
							Dimension d;

							tk = Toolkit.getDefaultToolkit();
							d = tk.getScreenSize();
							setSize(d.width/3, d.height/3);
							setLocation(d.width/4, d.height/4);

							setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

							setTitle("FOSh");

							setVisible(true);
						}// end of setup
			}