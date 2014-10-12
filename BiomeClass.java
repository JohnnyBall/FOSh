 import java.net.*;
 import java.util.*;
 import java.lang.Object.*;
 import javax.swing.*;
 import java.lang.Exception;

 public class BiomeClass
 {
 		float fishTempMax;
	 	float fishTempMin;
	 	float fishSaltMax;
	 	float fishSaltMin;
	 	float fishPHMax;
	 	float fishPHMin;

	 	BiomeClass()
	 	{}

	 	void setTempMax(float tempMax)
	 	{
	 		fishTempMax=tempMax;
	 	}
	 	void setTempMin(float tempMin)
	 	{
	 		fishTempMin=tempMin;
	 	}
	 	void setPHMax(float phMax)
	 	{
	 		fishPHMax=phMax;
	 	}
	 	void setPHMin(float phMin)
	 	{
	 		fishPHMin=phMin;
	 	}
	 	void setSaltMax(float saltMax)
	 	{
	 		fishSaltMax=saltMax;
	 	}
	 	void setSaltMin(float saltMin)
	 	{
	 		fishSaltMin=saltMin;
	 	}
 }