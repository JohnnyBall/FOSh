 import java.net.*;
 import java.util.*;
 import java.lang.Object.*;
 import javax.swing.*;
 import java.lang.Exception;
 import java.io.*;

 public class BiomeClass
 {
	 	String biomeName;
 		float fishTempMax;
	 	float fishTempMin;
	 	float fishSaltMax;
	 	float fishSaltMin;
	 	float fishPHMax;
	 	float fishPHMin;
	 	DataOutputStream dos;
		FileOutputStream fos;
		DataInputStream dis;
		FileInputStream fis;
		File fileName;
		String dir;
//Variables----------------------------------------------------------
	 	BiomeClass(String nameOfBiome,String waterTypeOwner)
	 	{
			biomeName=nameOfBiome;
			try
			{
				fileName=new File(biomeName);
				dir=fileName.getAbsolutePath();
				dir+="\\"+waterTypeOwner;
				fileName=new File(dir+"\\"+biomeName+".dat");
				fis=new FileInputStream(fileName);
				dis=new DataInputStream(fis);
				fos=new FileOutputStream(fileName);
				dos=new DataOutputStream(fos);
			}
			catch(IOException ioe)
			{
			}
		}
//SetTemp Classes----------------------------------------------------
	 	void setTempMax(float tempMax)
	 	{
	 		fishTempMax=tempMax;
	 	}
	 	void setTempMin(float tempMin)
	 	{
	 		fishTempMin=tempMin;
	 	}
//SetTemp Classes end------------------------------------------------
//SetPH Classes------------------------------------------------------
	 	void setPHMax(float phMax)
	 	{
	 		fishPHMax=phMax;
	 	}
	 	void setPHMin(float phMin)
	 	{
	 		fishPHMin=phMin;
	 	}
//SetPH Classes end--------------------------------------------------
//SetSalt Classes----------------------------------------------------
	 	void setSaltMax(float saltMax)
	 	{
	 		fishSaltMax=saltMax;
	 	}
	 	void setSaltMin(float saltMin)
	 	{
	 		fishSaltMin=saltMin;
	 	}
//SetSalt Classes end------------------------------------------------
//SaveBiome----------------------------------------------------------
	 	void saveBiome()
	 	{
			try
			{
				dos.writeFloat(fishTempMax);
				dos.writeFloat(fishTempMin);
				dos.writeFloat(fishPHMax);
				dos.writeFloat(fishPHMin);
				dos.writeFloat(fishSaltMax);
				dos.writeFloat(fishSaltMin);
			}
			catch(IOException ioe)
			{
			}
		}
//SaveBiome end------------------------------------------------------
//LoadBiome----------------------------------------------------------
		void loadBiome()
		{
			try
			{
				fishTempMax=dis.readFloat();
				fishTempMin=dis.readFloat();
				fishPHMax=dis.readFloat();
				fishPHMin=dis.readFloat();
				fishSaltMax=dis.readFloat();
				fishSaltMin=dis.readFloat();
			}
			catch(IOException ioe)
			{
			}
		}
//LoadBiome end------------------------------------------------------
 }