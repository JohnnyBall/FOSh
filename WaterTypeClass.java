 import java.net.*;
 import java.util.*;
 import java.lang.Object.*;
 import javax.swing.*;
 import java.lang.Exception;
 import java.io.*;

 public class WaterTypeClass
 {
 	Vector<BiomeClass> biome;
 	DataOutputStream dos;
	FileOutputStream fos;
	DataInputStream dis;
	FileInputStream fis;
	String wtcName;
	File fileName;
	String dir;
	int numberOfBiomes;
//Beginning of WaterTypeClass()----------------------------------
 	WaterTypeClass(String waterTypeName)
 	{
		wtcName=new String(waterTypeName);
		fileName=new File(waterTypeName);
		if(!fileName.exists())
			fileName.mkdir();
		dir=fileName.getAbsolutePath();
		fileName=new File(dir+"\\"+waterTypeName+".dat");
	}
//End of WaterTypeClass()---------------------------------------
//Beginning of setBiome()---------------------------------------
	void setBiome(String biomeName)
	{
		biome.add(new BiomeClass(biomeName,wtcName));
	}
//End of setBiome()---------------------------------------------
//Beginning of saveFile()---------------------------------------
	void saveFile()
		{
			try
				{
					fos=new FileOutputStream(fileName);
					dos=new DataOutputStream(fos);
					dos.writeInt(biome.size());
					for(int i=0;i<biome.size();i++)
						dos.writeUTF(biome.get(i).toString());
				}
			catch(IOException ioe)
			{}
		}
//End of saveFile()---------------------------------------------
//Beginning of loadFile()---------------------------------------
	void loadFile()
		{
			try
				{
					fis=new FileInputStream(fileName);
					dis=new DataInputStream(fis);
					numberOfBiomes=dis.readInt();
					for(int i=0;i<numberOfBiomes;i++)
						biome.add(new BiomeClass(dis.readUTF(),wtcName));
				}
			catch(IOException ioe)
			{}
		}
//End of loadFile()---------------------------------------------
 }