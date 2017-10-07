/**
 * Part of Leo's ICS3U Summative.
 * This is the Rabbit class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Sheep, Pig, Cow, Horse, or Unicorn class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Rabbit extends Animal
{

	private static int numRabbit = 0;				//keeps track of the number of Rabbit rescued
	private static int numExtraRabbit = 0;			//keeps track of the number of Extra Rabbit rescued
	private final static int NUMTILES = 4;			//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Common";	//represents the rarity of this Animal
	private final static String TYPE = "Rabbit";	//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Rabbit, then it will add a counter to numExtraRabbit
	 * If not, numRabbit will be increased
	 */
	public Rabbit()
	{
		if (numRabbit == 4)
			numExtraRabbit++;
		else
			numRabbit++;
	}
	
	/**
	 * Accessor (static) method to get the number of Rabbit
	 * @return the static variable, numRabbit
	 */
	public static int getNumRabbit() {return numRabbit;}
	
	/**
	 * Accessor (static) method to get the number of Extra Rabbit
	 * @return the static variable, numExtraRabbit
	 */
	public static int getNumExtraRabbit() {return numExtraRabbit;}
	
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
	 * Returns a String representation of the number of Rabbit rescued and
	 * the number of Extra Rabbit rescued
	 * @return the number of Rabbit and the number of Extra Rabbit
	 */
	public String toString()
	{
		return ("Number of Rabbit: " + numRabbit + " & Number of Extra Rabbit: " + numExtraRabbit);
	}
	
}

/*
- numRabbit: int
- numExtraRabbit: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Rabbit()
+ getNumRabbit(): int
+ getNumExtraRabbit(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String
*/