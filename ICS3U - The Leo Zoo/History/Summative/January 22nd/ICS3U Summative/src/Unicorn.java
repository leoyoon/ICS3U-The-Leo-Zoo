/**
 * Part of Leo's ICS3U Summative.
 * This is the Unicorn class that will extend the Animal parent class, 
 * thus gaining access to the Animal class' methods.
 * 
 * this class is almost identical to the Sheep, Rabbit, Pig, Cow, or Horse class.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Unicorn extends Animal 
{

	private static int numUnicorn = 0;					//keeps track of the number of Unicorn rescued
	private static int numExtraUnicorn = 0;				//keeps track of the number of Extra Unicorn rescued
	private final static int NUMTILES = 3;				//represents the number of tiles for this Animal's pattern
	private final static String RARITY = "Mythical";	//represents the rarity of this Animal
	private final static String TYPE = "Unicorn";		//represents this Animal species
	
	/**
	 * Constructor method that can do two things
	 * If there are four Unicorn, then it will add a counter to numExtraUnicorn
	 * If not, numUnicorn will be increased
	 */
	public Unicorn()
	{
		if (numUnicorn == 4)
			numExtraUnicorn++;
		else
			numUnicorn++;
	}
	
	/**
	 * Accessor (static) method to get the number of Unicorn
	 * @return the static variable, numUnicorn
	 */
	public static int getNumUnicorn() {return numUnicorn;}
	
	/**
	 * Accessor (static) method to get the number of Extra Unicorn
	 * @return the static variable, numExtraUnicorn
	 */
	public static int getNumExtraUnicorn() {return numExtraUnicorn;}
	
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
	 * Returns a String representation of the number of Unicorn rescued and
	 * the number of Extra Unicorn rescued
	 * @return the number of Unicorn and the number of Extra Unicorn
	 */
	public String toString()
	{
		return ("Number of Unicorn: " + numUnicorn + " & Number of Extra Unicorn: " + numExtraUnicorn);
	}
	
}

/*
- numUnicorn: int
- numExtraUnicorn: int
- NUMTILES: int
- RARITY: String
- TYPE: String

+ Unicorn()
+ getNumUnicorn(): int
+ getNumExtraUnicorn(): int
+ getNumtiles(): int
+ getRarity(): String
+ getType(): String
+ toString(): String
*/