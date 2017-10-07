/**
 * Part of Leo's ICS3U Summative.
 * This is the Cow class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Sheep, Rabbit, Pig, Horse, or Unicorn class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Cow extends Animal
{

	private static int numCow = 0;					//keeps track of the number of Cow rescued
	private static int numExtraCow = 0;				//keeps track of the number of Extra Cow rescued
	private final static int NUMTILES = 3;			//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Rare";	//represents the rarity of this Animal
	private final static String TYPE = "Cow";		//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Cow, then it will add a counter to numExtraCow
	 * If not, numCow will be increased
	 */
	public Cow()
	{
		if (numCow == 4)
			numExtraCow++;
		else
			numCow++;
	}
	
	/**
	 * Accessor (static) method to get the number of Cow
	 * @return the static variable, numCow
	 */
	public static int getNumCow() {return numCow;}
	
	/**
	 * Accessor (static) method to get the number of Extra Cow
	 * @return the static variable, numExtraCow
	 */
	public static int getNumExtraCow() {return numExtraCow;}
	
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
	 * Returns a String representation of the number of Cow rescued and
	 * the number of Extra Cow rescued
	 * @return the number of Cow and the number of Extra Cow
	 */
	public String toString()
	{
		return ("Number of Cow: " + numCow + " & Number of Extra Cow: " + numExtraCow);
	}
	
}

/*
- numCow: int
- numExtraCow: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Cow()
+ getNumCow(): int
+ getNumExtraCow(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String 
*/