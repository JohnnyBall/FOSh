 import java.net.*;
 import java.util.*;
 import java.lang.Object.*;
 import javax.swing.*;
 import java.lang.Exception;
 import java.io.*;

 public class WaterTypeClass
 {
 	public Vector<BiomeClass> biome;
 	public DataOutputStream dos;
	public FileOutputStream fos;
	public DataInputStream dis;
	public FileInputStream fis;
	public String wtcName;
	public File fileName;
	public String dir;
	public int numberOfBiomes;
//Variables---------------------------------------------------------
 	WaterTypeClass(String waterTypeName)
 	{
		wtcName=new String(waterTypeName);
		fileName=new File(waterTypeName);
		if(!fileName.exists())
			fileName.mkdir();
		dir=fileName.getAbsolutePath();
		fileName=new File(dir+"\\"+waterTypeName+".dat");
	}//End of WaterTypeClass()---------------------------------------

	void setBiome(String biomeName)
	{
		biome.add(new BiomeClass(biomeName,wtcName));
	}//End of setBiome()---------------------------------------------

	void saveFile() throws IOException
		{
					fos=new FileOutputStream(fileName);
					dos=new DataOutputStream(fos);
					dos.writeInt(biome.size());
						dos.writeInt(biome.size());
						for(int i=0;i<biome.size();i++)
							dos.writeUTF(biome.get(i).toString());
		}//End of saveFile()-----------------------------------------

	void loadFile() throws IOException
		{
					fis=new FileInputStream(fileName);
					dis=new DataInputStream(fis);
					numberOfBiomes=dis.readInt();
						for(int i=0;i<numberOfBiomes;i++)
							biome.add(new BiomeClass(dis.readUTF(),wtcName));
		}//End of loadFile()-----------------------------------------
 }