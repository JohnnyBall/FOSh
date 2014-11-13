import java.util.*;
import java.io.*;

public class BiomeClass
{
	public String 			biomeName;
    //public String           note;
 	public double 			fishTempMax;
	public double 			fishTempMin;
	public double 			fishPHMax;
	public double 			fishPHMin;
    public double           fishSaltMax;
    public double           fishSaltMin;

	BiomeClass(String nameOfBiome)
	{
		biomeName = nameOfBiome;
	}//End of BiomeClass()-----------------------------------------------

    BiomeClass(){}//End of BiomeClass()----------------------------------

    @Override
    public String toString()
    {
        return biomeName +" "+" "+fishTempMax+" "+fishTempMin+" "+fishPHMax+" "+fishPHMin+" "+fishSaltMax+" "+fishSaltMin;
    }//End of BiomeClass()-----------------------------------------------
    BiomeClass(DataInputStream dis) throws IOException
    {
        biomeName   = dis.readUTF();
        fishTempMax = dis.readDouble();
        fishTempMin = dis.readDouble();
        fishPHMax   = dis.readDouble();
        fishPHMin   = dis.readDouble();
        fishSaltMax = dis.readDouble();
        fishSaltMin = dis.readDouble();
    }//LoadBiome end------------------------------------------------------

	void saveBiome(DataOutputStream dos) throws IOException
	{
        dos.writeUTF(biomeName);
		dos.writeDouble(fishTempMax);
		dos.writeDouble(fishTempMin);
		dos.writeDouble(fishPHMax);
		dos.writeDouble(fishPHMin);
		dos.writeDouble(fishSaltMax);
		dos.writeDouble(fishSaltMin);
	}//SaveBiome end------------------------------------------------------
}//end of class
