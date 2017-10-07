/**
 * Part of Leo's ICS3U Summative.
 * This is the Sheep class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Rabbit, Pig, Cow, Horse, or Unicorn class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Sheep extends Animal
{

	private static int numSheep = 0;				//keeps track of the number of Sheep rescued
	private static int numExtraSheep = 0;			//keeps track of the number of Extra Sheep rescued
	private final static int NUMTILES = 4;			//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Common";	//represents the rarity of this Animal
	private final static String TYPE = "Sheep";		//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Sheep, then it will add a counter to numExtraSheep
	 * If not, numSheep will be increased
	 */
	public Sheep()
	{
		if (numSheep == 4)
			numExtraSheep++;
		else
			numSheep++;
	}
	
	/**
	 * Accessor (static) method to get the number of Sheep
	 * @return the static variable, numSheep
	 */
	public static int getNumSheep() {return numSheep;}
	
	/**
	 * Accessor (static) method to get the number of Extra Sheep
	 * @return the static variable, numExtraSheep
	 */
	public static int getNumExtraSheep() {return numExtraSheep;}
	
	/**
	 * Accessor (static) method to get the number of tiles this Animal requires
	 * @return the final static variable, NUMTILES
	 */
	public static int getNumTiles() {return NUMTILES;}
	
	/**
	 * Accessor (static) method to get the rarity of this specific Animal
	 * @return the final static variable, RARITY
	 */
	public static String getRarity() {return RARITY;}
	
	/**
	 * Accessor (static) method to get the Animal Type(species) of this specific Animal
	 * @return the final static variable, TYPE
	 */
	public static String getType() {return TYPE;}
	
	/**
	 * Returns a String representation of the number of Sheep rescued and
	 * the number of Extra Sheep rescued
	 * @return the number of Sheep and the number of Extra Sheep
	 */
	public String toString()
	{
		return ("Number of Sheep: " + numSheep + " & Number of Extra Sheep: " + numExtraSheep);
	}
}

/*
- numSheep: int
- numExtraSheep: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Sheep()
+ getNumSheep(): int
+ getNumExtraSheep(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String
*/