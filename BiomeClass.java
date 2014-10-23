 import java.net.*;
 import java.util.*;
 import java.lang.Object.*;
 import javax.swing.*;
 import java.lang.Exception;
 import java.io.*;

 public class BiomeClass
 {
	 	public String biomeName;
 		public float fishTempMax;
	    public float fishTempMin;
	 	public float fishSaltMax;
	 	public float fishSaltMin;
	 	public float fishPHMax;
	 	public float fishPHMin;
	 	public DataOutputStream dos;
		public FileOutputStream fos;
		public DataInputStream dis;
		public FileInputStream fis;
		public File fileName;
		public String dir;//Variables-------------------------------------------------

	 	BiomeClass(String nameOfBiome,String waterTypeOwner)
	 	{
			biomeName=nameOfBiome;
				fileName=new File(biomeName);
				dir=fileName.getAbsolutePath();
				dir+="\\"+waterTypeOwner;
				fileName=new File(dir+"\\"+biomeName+".dat");

		}//End of BiomeClass()------------------------------------------------
	 	void setTempMax(float tempMax)
	 	{
	 		fishTempMax=tempMax;
	 	}
	 	void setTempMin(float tempMin)
	 	{
	 		fishTempMin=tempMin;
	 	}//SetTemp Classes end------------------------------------------------

	 	void setPHMax(float phMax)
	 	{
	 		fishPHMax=phMax;
	 	}
	 	void setPHMin(float phMin)
	 	{
	 		fishPHMin=phMin;
	 	}//SetPH Classes end--------------------------------------------------
	 	void setSaltMax(float saltMax)
	 	{
	 		fishSaltMax=saltMax;
	 	}
	 	void setSaltMin(float saltMin)
	 	{
	 		fishSaltMin=saltMin;
	 	}//SetSalt Classes end------------------------------------------------
	 	void saveBiome() throws IOException
	 	{

				fos=new FileOutputStream(fileName);
				dos=new DataOutputStream(fos);
				dos.writeFloat(fishTempMax);
				dos.writeFloat(fishTempMin);
				dos.writeFloat(fishPHMax);
				dos.writeFloat(fishPHMin);
				dos.writeFloat(fishSaltMax);
				dos.writeFloat(fishSaltMin);
		}//SaveBiome end------------------------------------------------------
		void loadBiome() throws IOException
		{
				fis=new FileInputStream(fileName);
				dis=new DataInputStream(fis);
				fishTempMax=dis.readFloat();
				fishTempMin=dis.readFloat();
				fishPHMax=dis.readFloat();
				fishPHMin=dis.readFloat();
				fishSaltMax=dis.readFloat();
				fishSaltMin=dis.readFloat();
		}//LoadBiome end------------------------------------------------------
 }