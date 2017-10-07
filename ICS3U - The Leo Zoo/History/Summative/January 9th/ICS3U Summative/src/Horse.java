/**
 * Part of Leo's ICS3U Summative.
 * This is the Horse class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Sheep, Rabbit, Pig, Cow, or Unicorn class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Horse extends Animal
{

	private static int numHorse = 0;				//keeps track of the number of Horse rescued
	private static int numExtraHorse = 0;			//keeps track of the number of Extra Horse rescued
	private final static int NUMTILES = 3;			//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Rare";	//represents the rarity of this Animal
	private final static String TYPE = "Horse";		//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Horse, then it will add a counter to numExtraHorse
	 * If not, numHorse will be increased
	 */
	public Horse()
	{
		if (numHorse == 4)
			numExtraHorse++;
		else
			numHorse++;
	}
	
	/**
	 * Accessor (static) method to get the number of Horse
	 * @return the static variable, numHorse
	 */
	public static int getNumHorse() {return numHorse;}
	
	/**
	 * Accessor (static) method to get the number of Extra Horse
	 * @return the static variable, numExtraHorse
	 */
	public static int getNumExtraHorse() {return numExtraHorse;}
	
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
	 * Returns a String representation of the number of Horse rescued and
	 * the number of Extra Horse rescued
	 * @return the number of Horse and the number of Extra Horse
	 */
	public String toString()
	{
		return ("Number of Horse: " + numHorse + " & Number of Extra Horse: " + numExtraHorse);
	}
	
}

/*
- numHorse: int
- numExtraHorse: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Horse()
+ getNumHorse(): int
+ getNumExtraHorse(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String
*/