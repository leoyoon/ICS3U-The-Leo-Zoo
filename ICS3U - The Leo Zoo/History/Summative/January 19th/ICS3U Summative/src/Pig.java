/**
 * Part of Leo's ICS3U Summative.
 * This is the Pig class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Sheep, Rabbit, Cow, Horse, or Unicorn class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Pig extends Animal
{

	private static int numPig = 0;					//keeps track of the number of Pig rescued
	private static int numExtraPig = 0;				//keeps track of the number of Extra Pig rescued
	private final static int NUMTILES = 4;			//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Common";	//represents the rarity of this Animal
	private final static String TYPE = "Pig";		//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Pig, then it will add a counter to numExtraPig
	 * If not, numPig will be increased
	 */
	public Pig()
	{
		if (numPig == 4)
			numExtraPig++;
		else
			numPig++;
	}
	
	/**
	 * Accessor (static) method to get the number of Pig
	 * @return the static variable, numPig
	 */
	public static int getNumPig() {return numPig;}
	
	/**
	 * Accessor (static) method to get the number of Extra Pig
	 * @return the static variable, numExtraPig
	 */
	public static int getNumExtraPig() {return numExtraPig;}
	
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
	 * Returns a String representation of the number of Pig rescued and
	 * the number of Extra Pig rescued
	 * @return the number of Pig and the number of Extra Pig
	 */
	public String toString()
	{
		return ("Number of Pig: " + numPig + " & Number of Extra Pig: " + numExtraPig);
	}
	
}

/*
- numPig: int
- numExtraPig: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Pig()
+ getNumPig(): int
+ getNumExtraPig(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String
*/