import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Part of Leo's ICS3U Summative.
 * This is the biggest class in the entire Java Project, that will hold the GUI.
 * This is The Leo Zoo.
 * 
 * @author Leo Yoon
 * @since Thursday, January 8th of 2014
 */

/**
 * @TODO 	Make the other 3 JPanel screens
 * 			Make the other methods
 *
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
	 * used in the "Animal Pen" JPanel, to display each animal's picture
	 * this is a button because pressing these will give the user the ability to name each animal
	 */	
	private JButton[] animalDisplay = new JButton[4];
	
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
	 */
	private JPanel rescueBoard;
	
	//Screens: Title, Load File, Animal Pen, Rescue Board, Post-Rescue
	private JPanel[] screens = new JPanel[5]; 	
		
	/*
	 * animalRandomNumber: the random number that determines which animal is to be spawned.
	 * 					   this represents the percentage of each animal being spawned.
	 * 					  	 1 ~ 25:	Sheep
	 * 					   	26 ~ 50:	Rabbit
	 * 					   	51 ~ 75:	Pig
	 * 					  	76 ~ 85:	Cow
	 * 						86 ~ 95:	Horse
	 * 						96 ~ 100:	Unicorn
	 * rescueTries: number of rescue tries remaining during a rescue. Starts with 10, then steadily decreases
	 * saveFile: represents the number of the saveFile. This determines which file is to be updated.
	 */
	private int animalRandomNumber, rescueTries, saveFile;
	
	//the timer that makes the user wait after the rescue is over
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
		
		//this means the JFrame's size cannot be modified
		setResizable(false);
		
		//this ensures that the buttons actually become the correct size
		setLayout(null);
		
		//Font object for the buttons
		Font f = new Font("Trebuchet MS", Font.BOLD, 32);		
		
		descriptions = new JLabel("The Leo Zoo");
		descriptions.setBounds(50, 100, 0, 0);
		descriptions.setSize(650, 100);
		descriptions.setHorizontalAlignment(SwingConstants.CENTER);
		descriptions.setVerticalAlignment(SwingConstants.BOTTOM);
		descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 96));	
		descriptions.setVisible(true);
		add(descriptions);
		
		// // // // beginning of "Title" JPanel		
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
		screens[0].setLayout(null);
		screens[0].setSize(750, 900);
		screens[0].add(newGame);
		screens[0].add(loadGame);
		screens[0].setBackground(farmColour);
		screens[0].setVisible(true);		
		// // // // end of "Title" JPanel		
				
		// // // // beginning of "Load File" JPanel
		screens[1] = new JPanel();
		screens[1].setSize(750, 900);
		screens[1].setLayout(null);
		screens[1].setBackground(farmColour);
		
		for (int i = 0; i < 3; i++)
		{
			files[i] = new JButton("File #" + (i + 1));
			files[i].setFont(f);
			files[i].setBounds(200, 300 + (i * 150), 350, 100);
			files[i].addActionListener(this);
						
			screens[1].add(files[i]);
		}
		
		screens[1].setVisible(false);
		// // // // end of "Load File" JPanel
		
		// // // // beginning of "Animal Pen" JPanel
		screens[2] = new JPanel();
		screens[2].setSize(750, 900);
		screens[2].setLayout(null);
		screens[2].setBackground(farmColour);
	
		for (int i = 0; i < 6; i++)
		{
			animalPens[i] = new JButton(new ImageIcon("Data/Animal" + (i + 1) + ".jpg"));
			animalPens[i].addActionListener(this);
			animalPens[i].setBounds(20 + i * 125, 775, 81, 81);
			animalPens[i].setBackground(farmColour);
			
			screens[2].add(animalPens[i]);
		}
		
		for (int i = 0; i < 4; i++)
		{
			animalDisplay[i] = new JButton("Not Acquired");
			animalDisplay[i].setFont(f);
			animalDisplay[i].addActionListener(this);
			animalDisplay[i].setHorizontalAlignment(SwingConstants.CENTER);
			animalDisplay[i].setVerticalAlignment(SwingConstants.BOTTOM);
			
			if(i < 2)			
				animalDisplay[i].setBounds(100 + i * 300, 50, 250, 250);			
			
			if(i > 1)			
				animalDisplay[i].setBounds(100 + (i - 2) * 300, 350, 250, 250);		
			
			screens[2].add(animalDisplay[i]);
		}
		
		seeProgress = new JButton("See Progress");
		seeProgress.setBounds(1, 650, 245, 100);
		seeProgress.setFont(f);
		seeProgress.addActionListener(this);
		
		startRescue = new JButton("Start Rescue");
		startRescue.setBounds(250, 650, 245, 100);
		startRescue.setFont(f);
		startRescue.addActionListener(this);
		
		exitGame = new JButton("Exit Game");
		exitGame.setBounds(500, 650, 245, 100);
		exitGame.setFont(f);
		exitGame.addActionListener(this);
			
		screens[2].add(seeProgress);
		screens[2].add(startRescue);
		screens[2].add(exitGame);
		
		screens[2].setVisible(false);
		// // // // end of "Animal Pen" JPanel
				
		add(screens[0]);
		add(screens[1]);
		add(screens[2]);
		
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
		
		if (e.getSource() == newGame)
		{
			descriptions.setText("Select New Slot");
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 72));
			
			screens[0].setVisible(false);
			screens[1].setVisible(true);
			
			isNewGame = true;
		}
	
		if (e.getSource() == loadGame)
		{
			//testing purposes
			descriptions.setVisible(false);
			screens[0].setVisible(false);
			screens[2].setVisible(true);
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
	
	private void exitAndSave()
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

/* added after Friday the 9th
- isNewGame: boolean
- animalDisplay: JButton[]

- exitAndSave(): void 
*/

/* removed after Friday the 9th
- menuBoard: JPanel
- animalHotbar: JPanel
*/
