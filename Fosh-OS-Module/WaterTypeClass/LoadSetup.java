 import java.util.*;
 import java.io.*;

public class LoadSetup
{
	public WaterTypeClass wtc1;

	public static void main(String []Args){new LoadSetup();}

	LoadSetup()
	{
		wtc1  = new WaterTypeClass<BiomeClass>("Fresh Water");
		try
		{
			wtc1.loadFile();
			System.out.println(wtc1.get(0));
			System.out.println(wtc1.get(1));
		}
		catch(IOException ioe){System.out.println("LOADERS FAILED T.T");}

        wtc1  = new WaterTypeClass<BiomeClass>("Salt Water");
        try
        {
            wtc1.loadFile();
            System.out.println(wtc1.get(0));
            System.out.println(wtc1.get(1));
        }
        catch(IOException ioe){System.out.println("LOADERS FAILED T.T");}
	}
}//Loadsetup
