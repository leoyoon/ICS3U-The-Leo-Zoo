/**
 * Part of Leo's ICS3U Summative.
 * This is the Animal class that will be the parent class of all of the 6 child animal classes.
 * 
 * @author Leo Yoon
 * @since Wednesday, January 7th of 2015
 */

public class Animal
{
	
	//private String variable to store the name of the animal
	private String name;	
	
	//Constructor method that sets the default value of the Animal name
	public Animal() {name = "No Name";}
	
	/**
	 * Accessor method to get the name of the Animal
	 * @return the name of the Animal
	 */
	public String getName() {return name;}
	
	/**
	 * Mutator method to set this person's first name
	 * @param n String used to set the name of the Animal
	 */
	public void setName(String n) {name = n;}	
	
	/**
	 * Compares this Animal to the specified Animal object (a). This returns true if
	 * they have the same names.
	 * @param a the Animal to compare this Person to
	 * @return true if the Animal objects are equal, false if not
	 */
	public boolean equals(Animal a) {return a.getName().equalsIgnoreCase(name);} 	
	
	/**
	 * Returns a String text of the name of the Animal
	 * @return the name of the Animal, in a text format
	 */
	public String toString() {return ("Name: " + name);}
	
}

/*
- name: String

+ Animal()
+ getName(): String
+ setName(String n): void
+ equals(Animal a): boolean
+ toString(): String
*/