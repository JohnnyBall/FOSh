 import java.util.*;
 import java.io.*;

public class LoadSetup
{
		public WaterTypeClass wtc1;
		public BiomeClass biome;

		public static void main(String []Args)
		{
			new LoadSetup();
		}

		LoadSetup()
		{
			wtc1=new WaterTypeClass("Fresh Water");
			biome=new BiomeClass("White Water",wtc1.wtcName);
			try
			{
				wtc1.loadFile();
				System.out.println(wtc1.biome.get(0).fishTempMax);
				System.out.println(wtc1.biome.get(0).fishTempMin);
				System.out.println(wtc1.biome.get(0).fishPHMax);
				System.out.println(wtc1.biome.get(0).fishPHMin);
				System.out.println(wtc1.biome.get(0).fishSaltMax);
				System.out.println(wtc1.biome.get(0).fishSaltMin);
				System.out.println(wtc1.biome.get(1).fishTempMax);
				System.out.println(wtc1.biome.get(1).fishTempMin);
				System.out.println(wtc1.biome.get(1).fishPHMax);
				System.out.println(wtc1.biome.get(1).fishPHMin);
				System.out.println(wtc1.biome.get(1).fishSaltMax);
				System.out.println(wtc1.biome.get(1).fishSaltMin);
			}
			catch(IOException ioe)
			{
			}
	}
}