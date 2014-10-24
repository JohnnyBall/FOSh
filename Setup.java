 import java.util.*;
 import java.io.*;

public class Setup
{
	public WaterTypeClass wtc1;
	public BiomeClass biome;

	public static void main(String []Args)
	{
		new Setup();
	}

	Setup()
	{
		wtc1=new WaterTypeClass("Fresh Water");
		biome=new BiomeClass("White Water",wtc1.wtcName);
		wtc1.addBiome(biome);
		biome.setTempMax(82);
		biome.setTempMin(76);
		biome.setPHMin(6.5);
		biome.setPHMax(7.3);
		biome.setSaltMin(49.0);
		biome.setSaltMax(65.0);
		biome=new BiomeClass("Black Water",wtc1.wtcName);
		wtc1.addBiome(biome);
		biome.setTempMax(84);
		biome.setTempMin(76);
		biome.setPHMin(4.5);
		biome.setPHMax(5.7);
		biome.setSaltMin(7.0);
		biome.setSaltMax(11.0);
		try
		{
				wtc1.saveFile();
				System.out.println("Saved");
		}
		catch(IOException ioe)
		{
			System.out.println("Problem saving Water Type Class");
		}
	}
}