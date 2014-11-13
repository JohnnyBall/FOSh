 import java.util.*;
 import java.io.*;

public class Setup
{
	public static void main(String []Args){new Setup();}

	public  WaterTypeClass<BiomeClass> wtc1;
	public 	BiomeClass 	  biome;

	Setup()
	{
		wtc1   = new WaterTypeClass<BiomeClass>("Fresh Water");
		biome  = new BiomeClass("White Water");

		biome.fishTempMax = 82;
		biome.fishTempMin = 76;
		biome.fishPHMin   = 6.5;
		biome.fishPHMax   = 7.3;
		biome.fishSaltMax = 65.0;
		biome.fishSaltMin = 49.0;
		wtc1.addElement(biome);
		System.out.println(biome);
		System.out.println("added Biome: "+biome.biomeName);

		biome  = new BiomeClass("Black Water");

		biome.fishTempMax = 84;
		biome.fishTempMin = 76;
		biome.fishPHMin   = 4.5;
		biome.fishPHMax   = 5.7;
		biome.fishSaltMax = 7.0;
		biome.fishSaltMin = 11.0;
		wtc1.addElement(biome);
		System.out.println(biome);
		System.out.println("added Biome: "+biome.biomeName);

		try
		{
			System.out.println("Saving file...");
			wtc1.saveFile();
			System.out.println("Saved");
		}
		catch(IOException ioe){System.out.println("Problem saving Water Type Class");}


		wtc1   = new WaterTypeClass<BiomeClass>("Salt Water");
		biome  = new BiomeClass("Tropical Reef");

		biome.fishTempMax = 83;
		biome.fishTempMin = 78;
		biome.fishPHMin   = 8.0;
		biome.fishPHMax   = 8.3;
		biome.fishSaltMax = 54400.0;
		biome.fishSaltMin = 51700.0;
		wtc1.addElement(biome);
		System.out.println(biome);
		System.out.println("added Biome: "+biome.biomeName);

		biome  = new BiomeClass("Tropical Marine");

		biome.fishTempMax = 83;
		biome.fishTempMin = 76;
		biome.fishPHMin   = 8.1;
		biome.fishPHMax   = 8.3;
		biome.fishSaltMax = 54400.0;
		biome.fishSaltMin = 51700.0;
		wtc1.addElement(biome);
		System.out.println(biome);
		System.out.println("added Biome: "+biome.biomeName);

		try
		{
			System.out.println("Saving file...");
			wtc1.saveFile();
			System.out.println("Saved");
		}
		catch(IOException ioe){System.out.println("Problem saving Water Type Class");}
	}//setup ends
}//end of setupclass
