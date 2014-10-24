 import java.util.*;
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
		fileName=new File(waterTypeName+".dat");
		biome=new Vector<BiomeClass>();
	}//End of WaterTypeClass()---------------------------------------

	void addBiome(BiomeClass biomeToAdd)
	{
		biome.addElement(biomeToAdd);
		return;
	}//End of addBiome()---------------------------------------------

	void saveFile() throws IOException
		{
					fos=new FileOutputStream(fileName);
					dos=new DataOutputStream(fos);
					dos.writeInt(biome.size());
							for(int i=0;i<biome.size();i++)
							{
								biome.get(i).saveBiome();
								dos.writeUTF(biome.get(i).biomeName);
							}
					return;
		}//End of saveFile()-----------------------------------------
	void loadFile() throws IOException
		{
					fis=new FileInputStream(fileName);
					dis=new DataInputStream(fis);
					numberOfBiomes=dis.readInt();
						for(int i=0;i<numberOfBiomes;i++)
							{

								biome.addElement(new BiomeClass(dis.readUTF(),wtcName));
								biome.get(i).loadBiome();
							}
					return;
		}//End of loadFile()-----------------------------------------
 }