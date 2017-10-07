import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Part of Leo's ICS3U Summative.
 * This is the biggest class in the entire Java Project, that will hold the GUI.
 * This is The Zoo.
 * 
 * @author Leo Yoon
 * @since Thursday, January 8th of 2014
 */

public class Zoo extends JFrame implements ActionListener
{
	/*
	 * this variable will change to true once the newGame button is pressed
	 * if this value is false, the corresponding save file will not load.
	 */
	private boolean gameStarted = false;
	
	/*
	 * If newGame was pressed, then this variable will be true,
	 * and false if loadGame was pressed
	 * 
	 * Used to determine whether to change the value of the saveFile variable.
	 */
	private boolean isNewGame;
	
	/*
	 * startRescue: starts the rescue, calling the generatePlayField method
	 * exitGame: exits and saves the game (accessible outside of rescue)
	 * loadGame: calls up the JPanel that contains the files JButtons
	 * newGame: same as loadGame
	 */
	private JButton startRescue, exitGame, loadGame, newGame, seeProgress, returnToFarm;
	
	//the three JButtons that each lead to their corresponding save files
	private JButton[] files = new JButton[3];
	
	//the six JButtons that act as a teleporting mechanism to each of the animal pens
	private JButton[] animalPens = new JButton[6];
	
	//the 25 JButtons that form the 5x5 grid Playing Field for the rescue
	private JButton[] playingField = new JButton[25];
	
	/*
	 * animalCount: displays the number of animals rescued (will change its display for each animal)
	 * animalType: displays the type of the animal (animal species)
	 * descriptions: displays various information (Ex: title, help, other info, etc. - and this changes often)
	 */
	private JLabel animalCount, animalType, descriptions;
	
	//the four JLabels that display the name of the 4 animals
	private JLabel[] animalNames = new JLabel[4];
	
	/*
	 * rescueBoard: A GridLayout JPanel that represents the 5x5 Rescue Board
	 * menuBoard: A GridLayout JPanel that represents the 3x1 menu board consisting of 3 buttons
	 * animalHotbar: A GridLayout JPanel that represents the 6x1 board that contains the 6 animalPens buttons
	 */
	private JPanel rescueBoard, menuBoard, animalHotbar;
	
	//Screens: Title, Load File, Animal Pen, Rescue Board, Post-Rescue
	private JPanel[] screens = new JPanel[5]; 	
	private int animalRandomNumber, rescueTries, saveFile;
	private Timer timer;
	
	public Zoo()
	{
		//this sets the title of the GUI
		super("The Leo Zoo");
		
		//this is to ensure that the program shuts down once the window has been closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets the size of the GUI
		setSize(750, 900);		
		
		Color farmColour = new Color(120, 185, 29);
				
		//the container (the content within the Frame)
		Container c = getContentPane();
		c.setBackground(farmColour);
		
		//this means the JFrame's size cannot be modified
		setResizable(false);
		
		//this ensures that the buttons actually become the correct size
		setLayout(null);
		
		//Font object for the buttons
		Font f = new Font("Trebuchet", Font.BOLD, 32);
		
		// // // // beginning of "Title" JPanel
		descriptions = new JLabel("The Leo Zoo");
		descriptions.setBounds(50, 100, 0, 0);
		descriptions.setSize(650, 100);
		descriptions.setHorizontalAlignment(SwingConstants.CENTER);
		descriptions.setVerticalAlignment(SwingConstants.BOTTOM);
		descriptions.setFont(new Font("Trebuchet", Font.BOLD, 96));		
		
		newGame = new JButton("Start New Game");
		newGame.setBounds(200, 300, 0, 0);
		newGame.setSize(350, 150);
		newGame.setFont(f);		
		newGame.addActionListener(this);
		
		loadGame = new JButton("Load Existing File");
		loadGame.setBounds(200, 500, 0, 0);
		loadGame.setSize(350, 150);
		loadGame.setFont(f);
		loadGame.addActionListener(this);
				
		screens[0] = new JPanel();
		add(descriptions);
		add(newGame);
		add(loadGame);
		// // // // end of "Title" JPanel
		
		//this ensures that the window is visible: should be at the end of the code
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Zoo();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == returnToFarm)
		{
			if (isVictory())
			{
				
			}
		}
	}

	private boolean isVictory()
	{
		//collect the number of each animals rescued
		//Note: this works because the animal constructors stop increasing the number of 
		//animals once it reaches 4
		int totalAnimals = Sheep.getNumSheep() + Rabbit.getNumRabbit() + Pig.getNumPig() +
						   Cow.getNumCow() + Horse.getNumHorse() + Unicorn.getNumUnicorn();
		
		//if the person has reached all of the animals,
		if (totalAnimals == 24)
			return true;
		else 
			return false;
	}

	private void generatePlayfield()
	{
		
	}

	private void pickAnimal()
	{
		
	}

	private void makePattern(Object o)
	{
		
	}
}

/*
- gameStarted: boolean
- animalRandomNumber: int
- rescueTries: int
- saveFile: int
- playingField: JButton[]
- animalPens: JButton[]
- files: JButton[]
- startRescue: JButton
- exitGame: JButton
- newGame: JButton
- loadGame: JButton
- seeProgress: JButton
- animalNames: JLabel[]
- animalCount: JLabel
- animalType: JLabel
- descriptions: JLabel
- screens: JPanel[]
- rescueBoard: JPanel
- menuBoard: JPanel
- animalHotbar: JPanel
- timer: Timer

+ Zoo()
+ main(String[] args): void
+ actionPerformed(ActionEvent e): void
- isVictory(): boolean
- generatePlayfield(): void
- pickAnimal(): void
- makePattern(Object o): void    
*/