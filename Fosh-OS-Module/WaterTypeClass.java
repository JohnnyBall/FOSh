 import java.util.*;
 import java.io.*;

 public class WaterTypeClass<e> extends Vector <BiomeClass>
 {
 	public DataOutputStream   dos;
    public DataInputStream    dis;
	public FileOutputStream   fos;
	public FileInputStream    fis;

	public String             wtcName;
	public File               fileName;
	public String             dir;
	public int                numberOfBiomes;

 	WaterTypeClass(String waterTypeName)
 	{
		wtcName  = new String(waterTypeName);
		fileName = new File(waterTypeName + ".dat");
	}//End of WaterTypeClass(String waterTypeName)---------------------------------------

    //WaterTypeClass(){ }//End of WaterTypeClass()---------------------------------------

	void saveFile()throws IOException
	{
       Iterator<BiomeClass> it;
	   fos = new FileOutputStream(fileName);
	   dos = new DataOutputStream(fos);
       it  = this.listIterator();

       dos.writeInt(this.size());
       System.out.println(this.size());
	   while(it.hasNext())
       {
	   	it.next().saveBiome(dos);
       }
	}//End of saveFile()-----------------------------------------

    void loadFile() throws IOException
	{
		fis            = new FileInputStream(fileName);
		dis            = new DataInputStream(fis);
		numberOfBiomes = dis.readInt();

		for(int i=0 ;i < numberOfBiomes; i++)
		  this.addElement(new BiomeClass(dis));
	}//End of loadFile()-----------------------------------------*/
 }//endof watertypeclass
