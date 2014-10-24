 import java.util.*;
 import java.io.*;

 public class BiomeClass
 {
	 	public String biomeName;
 		public double fishTempMax;
	    public double fishTempMin;
	 	public double fishSaltMax;
	 	public double fishSaltMin;
	 	public double fishPHMax;
	 	public double fishPHMin;
	 	public DataOutputStream dos;
		public FileOutputStream fos;
		public DataInputStream dis;
		public FileInputStream fis;
		public File fileName;
		public String dir;//Variables-------------------------------------------------

	 	BiomeClass(String nameOfBiome,String waterTypeOwner)
	 	{
			biomeName=nameOfBiome;
			fileName=new File(biomeName+".dat");
		}//End of BiomeClass()------------------------------------------------
	 	void setTempMax(double tempMax)
	 	{
	 		fishTempMax=tempMax;
	 	}
	 	void setTempMin(double tempMin)
	 	{
	 		fishTempMin=tempMin;
	 	}//SetTemp Classes end------------------------------------------------

	 	void setPHMax(double phMax)
	 	{
	 		fishPHMax=phMax;
	 	}
	 	void setPHMin(double phMin)
	 	{
	 		fishPHMin=phMin;
	 	}//SetPH Classes end--------------------------------------------------
	 	void setSaltMax(double saltMax)
	 	{
	 		fishSaltMax=saltMax;
	 	}
	 	void setSaltMin(double saltMin)
	 	{
	 		fishSaltMin=saltMin;
	 	}//SetSalt Classes end------------------------------------------------
	 	void saveBiome() throws IOException
	 	{

				fos=new FileOutputStream(fileName);
				dos=new DataOutputStream(fos);
				dos.writeDouble(fishTempMax);
				dos.writeDouble(fishTempMin);
				dos.writeDouble(fishPHMax);
				dos.writeDouble(fishPHMin);
				dos.writeDouble(fishSaltMax);
				dos.writeDouble(fishSaltMin);
		}//SaveBiome end------------------------------------------------------
		void loadBiome() throws IOException
		{
				fis=new FileInputStream(fileName);
				dis=new DataInputStream(fis);
				fishTempMax=dis.readDouble();
				fishTempMin=dis.readDouble();
				fishPHMax=dis.readDouble();
				fishPHMin=dis.readDouble();
				fishSaltMax=dis.readDouble();
				fishSaltMin=dis.readDouble();
		}//LoadBiome end------------------------------------------------------
 }