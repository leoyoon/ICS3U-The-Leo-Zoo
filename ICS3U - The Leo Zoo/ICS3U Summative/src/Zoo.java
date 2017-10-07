import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * Part of Leo's ICS3U Summative.
 * This is the biggest class in the entire Java Project, that will hold the GUI.
 * This is The Leo Zoo.
 * 
 * @author Leo Yoon
 * @since Thursday, January 8th of 2014
 */

/**
 * @TODO 	DONE			
 *
 */

public class Zoo extends JFrame implements ActionListener
{
	/*
	 * If newGame was pressed, then this variable will be true,
	 * and false if loadGame was pressed
	 * 
	 * Used to determine whether to change the value of the saveFile variable or not.
	 */
	private boolean isNewGame;
	
	//will be true if the animal was successfully rescued.
	private boolean rescueSuccess;
	
	/*
	 * startRescue: starts the rescue, calling the generatePlayField method
	 * exitGame: exits and saves the game (accessible outside of rescue)
	 * loadGame: calls up the JPanel that contains the files JButtons
	 * newGame: same as loadGame, but this one sets 'isNewGame' to true
	 * seeProgress: brings up a pop-up dialogue with all of the animals rescued
	 * returnToFarm: accessible after the rescue, brings the user back to the Farm
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
	 * animalType: displays the picture of the animal being rescued
	 * descriptions: displays various information (Ex: title, help, other info, etc. - and this changes often)
	 * rescuedAnimal: displays the picture of the animal that was rescued
	 */
	private JLabel animalType, descriptions, rescuedAnimal;
	
	//the four JLabels that display the name of the 4 animals
	private JLabel[] animalNames = new JLabel[4];
			
	//rescueBoard: A GridLayout JPanel that represents the 5x5 Rescue Board
	private JPanel rescueBoard;
	
	//Screens: Title, Load File, Animal Pen, Rescue Board, Post-Rescue, Victory
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
	 * animalSpawned: represents the animal that was spawned, starting from 1(sheep), 2(rabbit)... so on
	 * goodTiles: The number of correct tile choices by the user
	 * currentlyOn: The animal Pen the user is currently browsing
	 */
	private int animalRandomNumber, rescueTries, saveFile, animalSpawned, goodTiles, currentlyOn;
	
	//array to store the tile indexes with the animals
	private int[] correctTiles = new int[4];
	
	//the timer that makes the user wait after the rescue is over
	private Timer timer;
	
	//the colour of my zoo
	private Color farmColour = new Color(120, 185, 29);
	
	/*
	 * the Arrays that store each animal. 
	 * When an animal is rescued, an index is added to the corresponding Array
	 */
	Sheep[] sheep = new Sheep[4];
	Rabbit[] rabbit = new Rabbit[4];
	Pig[] pig = new Pig[4];
	Cow[] cow = new Cow[4];
	Horse[] horse = new Horse[4];
	Unicorn[] unicorn = new Unicorn[4];
	
	public Zoo()
	{
		//this sets the title of the GUI
		super("The Leo Zoo");
		
		//this is to ensure that the program shuts down once the window has been closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets the size of the GUI
		setSize(750, 900);				
		
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
			animalDisplay[i] = new JButton(new ImageIcon("Data/BigAnimal1.png"));
			animalDisplay[i].setFont(f);
			animalDisplay[i].addActionListener(this);
			animalDisplay[i].setHorizontalAlignment(SwingConstants.CENTER);
			animalDisplay[i].setVerticalAlignment(SwingConstants.CENTER);
			animalDisplay[i].setEnabled(false);
					
			if(i < 2)			
				animalDisplay[i].setBounds(100 + i * 350, 90, 200, 200);			
			
			if(i > 1)			
				animalDisplay[i].setBounds(100 + (i - 2) * 350, 390, 200, 200);		
			
			screens[2].add(animalDisplay[i]);
		}
		
		for (int i = 0; i < 4; i++)
		{
			animalNames[i] = new JLabel("Not Acquired");
			animalNames[i].setFont(f);
			animalNames[i].setHorizontalAlignment(SwingConstants.CENTER);
			animalNames[i].setVerticalAlignment(SwingConstants.CENTER);
			
			if(i < 2)			
				animalNames[i].setBounds(100 + i * 350, 300, 200, 50);			
			
			if(i > 1)			
				animalNames[i].setBounds(100 + (i - 2) * 350, 600, 200, 50);		
			
			screens[2].add(animalNames[i]);
			
		}
		
		seeProgress = new JButton("See Progress");
		seeProgress.setBounds(5, 680, 240, 70);
		seeProgress.setFont(f);
		seeProgress.addActionListener(this);
		
		startRescue = new JButton("Start Rescue");
		startRescue.setBounds(253, 680, 240, 70);
		startRescue.setFont(f);
		startRescue.addActionListener(this);
		
		exitGame = new JButton("Save & Exit");
		exitGame.setBounds(500, 680, 240, 70);
		exitGame.setFont(f);
		exitGame.addActionListener(this);
			
		screens[2].add(seeProgress);
		screens[2].add(startRescue);
		screens[2].add(exitGame);
		
		screens[2].setVisible(false);
		// // // // end of "Animal Pen" JPanel
		
		// // // // start of "Rescue Board" JPanel
		screens[3] = new JPanel();
		screens[3].setSize(750, 900);
		screens[3].setLayout(null);
		screens[3].setBackground(farmColour);
		
		rescueBoard = new JPanel();
		rescueBoard.setBorder(new EmptyBorder(5, 5, 5, 5));
		rescueBoard.setLayout(new GridLayout(5, 5, 5, 5));
		rescueBoard.setBounds(0, 127, 745, 745);
				
		for (int i = 0; i < playingField.length; i++)
		{
			playingField[i] = new JButton();
			playingField[i].addActionListener(this);
			playingField[i].setBackground(farmColour);
			
			rescueBoard.add(playingField[i]);
		}
				
		animalType = new JLabel(new ImageIcon("Data/Animal1.jpg"));
		animalType.setBounds(0, 10, 750, 100);
		animalType.setHorizontalAlignment(SwingConstants.CENTER);
		animalType.setVerticalAlignment(SwingConstants.CENTER);		
				
		screens[3].add(animalType);
		screens[3].add(rescueBoard);	
		screens[3].setVisible(false);		
		// // // // end of "Rescue Board" JPanel
		
		// // // // start of "Post Rescue" JPanel
		screens[4] = new JPanel();
		screens[4].setLayout(null);
		screens[4].setSize(750, 900);
		screens[4].setBackground(farmColour);
				
		rescuedAnimal = new JLabel();
		rescuedAnimal.setBounds(0, 150, 750, 250);
		rescuedAnimal.setHorizontalAlignment(SwingConstants.CENTER);
		rescuedAnimal.setVerticalAlignment(SwingConstants.CENTER);		
		
		returnToFarm = new JButton("Return to Farm");
		returnToFarm.setBounds(200, 450, 350, 100);	
		returnToFarm.setFont(f);
		returnToFarm.addActionListener(this);
		returnToFarm.setHorizontalAlignment(SwingConstants.CENTER);
		returnToFarm.setVerticalAlignment(SwingConstants.CENTER);	

		screens[4].add(returnToFarm);
		screens[4].add(rescuedAnimal);
		screens[4].setVisible(false);
		// // // // end of "Post Rescue" JPanel		
		
		//add all of the screens to the JFrame
		add(screens[0]);
		add(screens[1]);
		add(screens[2]);
		add(screens[3]);
		add(screens[4]);
		
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
			//stop the timer so that the timer code doesn't infinitely run
			timer.stop();
			
			//teleport the user to the Sheep Pen as default.
			descriptions.setVisible(true);
			descriptions.setBounds(25, 25, 700, 50);
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
			descriptions.setText(Sheep.getRarity() + " Sheep Pen - Rescued: " + Sheep.getNumSheep());
			
			/*
			 * because the 'returnToFarm' button automatically teleports the user to the Sheep pen,
			 * this value should be 0.
			 */
			currentlyOn = 0;
			
			/*
			 * first set the pictures of the buttons to Sheep, 
			 * disable them & set the JLabel to default values (for error preventions)
			 */
			for (int i = 0; i < 4; i++)
			{
				animalDisplay[i].setIcon(new ImageIcon("Data/BigAnimal1.png"));
				animalDisplay[i].setEnabled(false);
				animalNames[i].setText("Not Acquired");					
			}
			
			/*
			 * for each number of Sheep rescued,
			 * set the JLabels to the corresponding names (by using Array)
			 * and enable the correct number of JButtons
			 */
			for (int a = 0; a < Sheep.getNumSheep(); a++)
			{
				animalNames[a].setText(sheep[a].getName());
				animalDisplay[a].setEnabled(true);
			}
			
			/*
			 * hide the Post-Rescue screen and reveal the Animal Pen screen
			 */
			screens[4].setVisible(false);
			screens[2].setVisible(true);				 
			
			//default value for rescueSuccess
			rescueSuccess = false;
	
			//basically, check for Victory after every rescue
			if (isVictory())
				descriptions.setText("VICTORY HAS BEEN ACHIEVED!");
		}		
		
		//if the user starts a new game
		if (e.getSource() == newGame)
		{
			//set 'descriptions' to fit the screen
			descriptions.setText("Select New Slot");
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 72));
			
			//hide the Title screen and reveal the Load File screen
			screens[0].setVisible(false);
			screens[1].setVisible(true);
			
			//this game is now a New Game.
			isNewGame = true;
		}
	
		//if the user loads a previously saved game
		if (e.getSource() == loadGame) 
		{
			descriptions.setText("Select Saved Slot");
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 72));
			
			//hide the Title screen and reveal the Load File screen
			screens[0].setVisible(false);
			screens[1].setVisible(true);
			
			//default: every button is disabled
			for (int j = 0; j < 3; j++)
			{
				files[j].setEnabled(false);
			}
			
			/*
			 * for every file that has the first line as 'true'
			 * enable that button
			 */			
			for (int i = 0; i < 3; i++)
			{
				IO.openInputFile("Data\\SaveFile" + (i + 1) + ".txt");			
				
				try 
				{			
					//storing a temporary String variable
					String temp = IO.readLine();
					
					if (temp.equalsIgnoreCase("true"))
					{
						files[i].setEnabled(true);
					}
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
								
				try 
				{
					IO.closeInputFile();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}			
			
			//and obviously this is not a New Game
			isNewGame = false;	
		}
		
		for (int i = 0; i < files.length; i++)
		{
			if (e.getSource() == files[i])
			{
				saveFile = (i + 1);
				
				//if the game is New
				if (isNewGame == true)
				{
					//default: set to Sheep
					descriptions.setText("Common Sheep Pen - Rescued: 0");
					descriptions.setBounds(25, 25, 700, 50);
					descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
					
					//hide the Load File screen and reveal the Animal Pen screen
					screens[1].setVisible(false);
					screens[2].setVisible(true);
					
					//currently on Sheep pen
					currentlyOn = 0;					
				}
				
				//if the game is Old
				if (isNewGame == false)
				{										
					try 
					{
						//load a file
						loadFile();
					} 
					catch (IOException e1) 			
					{				
						e1.printStackTrace();
					}
					
					//default: set to Sheep, but with Sheep.getNumSheep value rather than 0		
					descriptions.setText("Common Sheep Pen - Rescued: " + Sheep.getNumSheep());
					descriptions.setBounds(25, 25, 700, 50);
					descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
					
					//hide the Load File screen and reveal the Animal Pen screen
					screens[1].setVisible(false);
					screens[2].setVisible(true);
					
					//currently on Sheep pen
					currentlyOn = 0;
					
					/*
					 * first: 	set all buttons to the Sheep image
					 * 			disable all buttons
					 * 			set the name JLabel to 'Not Acquired'
					 */
					for (int j = 0; j < 4; j++)
					{
						animalDisplay[i].setIcon(new ImageIcon("Data/BigAnimal1.png"));
						animalDisplay[i].setEnabled(false);
						animalNames[i].setText("Not Acquired");					
					}
					
					/*
					 * THEN:	only repeat Sheep.getNumSheep amount of times
					 * 			change the name JLabels to the corresponding names
					 * 			enable the necessary buttons
					 */
					for (int a = 0; a < Sheep.getNumSheep(); a++)
					{
						animalNames[a].setText(sheep[a].getName());
						animalDisplay[a].setEnabled(true);
					}
					
					//check for victory as the file loads
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}	
					
				}			
			}							
		}
		
		//if the user wishes to see the progress,
		if (e.getSource() == seeProgress)
		{			
			/*
			 * this is the default MessageDialog
			 * this lists all of the animals rescued, including extra animals
			 */
			if (isVictory() == false)
			{
				JOptionPane.showMessageDialog	(null, "Sheep: " + Sheep.getNumSheep() + "\n" +
										 		"Rabbit: " + Rabbit.getNumRabbit() + "\n" +
										 		"Pig: " + Pig.getNumPig() + "\n" + 
												"Cow: " + Cow.getNumCow() + "\n" + 
												"Horse: " + Horse.getNumHorse() + "\n" + 
												"Unicorn: " + Unicorn.getNumUnicorn() + "\n" +
												"Extra Sheep: " + Sheep.getNumExtraSheep() + "\n" + 
												"Extra Rabbit: " + Rabbit.getNumExtraRabbit() + "\n" +
												"Extra Pig: " + Pig.getNumExtraPig() + "\n" + 
												"Extra Cow: " + Cow.getNumExtraCow() + "\n" + 
												"Extra Horse: " + Horse.getNumExtraHorse() + "\n" + 
												"Extra Unicorn: " + Unicorn.getNumExtraUnicorn());
			}
				
			/*
			 * if the user has currently achieved victory,
			 * add an extra line of "VICTORY!!!" to the default MessageDialog
			 */				
			if (isVictory())
			{
				JOptionPane.showMessageDialog	(null, "Sheep: " + Sheep.getNumSheep() + "\n" +
												"Rabbit: " + Rabbit.getNumRabbit() + "\n" +
												"Pig: " + Pig.getNumPig() + "\n" + 
												"Cow: " + Cow.getNumCow() + "\n" + 
												"Horse: " + Horse.getNumHorse() + "\n" + 
												"Unicorn: " + Unicorn.getNumUnicorn() + "\n" +
												"Extra Sheep: " + Sheep.getNumExtraSheep() + "\n" + 
												"Extra Rabbit: " + Rabbit.getNumExtraRabbit() + "\n" +
												"Extra Pig: " + Pig.getNumExtraPig() + "\n" + 
												"Extra Cow: " + Cow.getNumExtraCow() + "\n" + 
												"Extra Horse: " + Horse.getNumExtraHorse() + "\n" + 
												"Extra Unicorn: " + Unicorn.getNumExtraUnicorn() + "\n\n" +
												"VICTORY!!!");				
			}			
		}			
					
		//if the user wishes to rescue...
		if (e.getSource() == startRescue)
		{
			//if the previously spawned animal was Sheep, Rabbit, or Pig
			if (animalSpawned <= 3)
			{
				//repeat 4 times
				for (int i = 0; i < Sheep.getNumTiles(); i++)
				{
					/*
					 * reset the pictures from the previous correct tiles 
					 * and
					 * set ALL of the correct tile values to -1, to prevent a Cow, Horse, or Unicorn from having
					 * 4 correct tiles
					 */
					playingField[correctTiles[i]].setIcon(new ImageIcon("Data/Blank.png"));
					correctTiles[i] = -1;
				}
			}
			else
			{
				//repeat 3 times
				for (int i = 0; i < Cow.getNumTiles(); i++)
				{
					//same as above
					playingField[correctTiles[i]].setIcon(new ImageIcon("Data/Blank.png"));
					correctTiles[i] = -1;
				}
			}			
			
			//reset EVERY tile's text to null
			for (int i = 0; i < playingField.length; i++)
			{
				playingField[i].setText(null);
			}
			
			/*
			 * reset the goodTiles value, because
			 * this means the user hasn't found any animal tiles yet
			 */
			goodTiles = 0;
			
			//generate playfield!
			generatePlayfield();			
		}
				
		for (int i = 0; i < playingField.length; i++)
		{
			//check each of the 25 tiles for ActionListener
			if (e.getSource() == playingField[i])
			{
				//this keeps track of the number of tile mismatches
				int failTiles = 0;				
				
				//every time a button is pressed, deduct a rescue try
				rescueTries--;
				
				//this is basically repainting the descriptions
				descriptions.setText("Rescue This:                  Rescue Tries: " + rescueTries);
				
				//the button that is pressed is now disabled to prevent overlap
				playingField[i].setEnabled(false);
							
				//run through the entire array
				for (int j = 0; j < correctTiles.length; j++)
				{
					//if the tile clicked matches with one of the correct tiles
					if(i == correctTiles[j])
					{
						//change the target JButton to the animal's picture
						playingField[i].setIcon(new ImageIcon("Data/Animal" + animalSpawned + ".jpg"));
						playingField[i].setHorizontalAlignment(SwingConstants.CENTER);
						playingField[i].setVerticalAlignment(SwingConstants.CENTER);		
						
						//keeps track of the number of correct tiles clicked
						goodTiles++;
					}					
					else
						//if the tile is incorrect, increment failTiles
						failTiles++;
				}	
				
				//now, when failTiles is up to 4, 
				if (failTiles == correctTiles.length)
				{
					//set the button to X
					playingField[i].setText("X");
					playingField[i].setForeground(Color.BLACK);
					playingField[i].setFont(new Font("Trebuchet MS", Font.BOLD, 96));
				}
								
				//when the user runs out of rescue tries
				if (rescueTries == 0) 
				{
					for (int k = 0; k < playingField.length; k++)
					{
						//disable ALL of the buttons
						playingField[k].setEnabled(false);
					}
					
					//timer object (worth 2 seconds)
					timer = new Timer(2000, new ActionListener()
					{ 
						//hide 
			            public void actionPerformed(ActionEvent e) 
			            {
			               //hide the Rescue Board screen and reveal the Post-Rescue screen
			               screens[3].setVisible(false);
			               screens[4].setVisible(true);
			               
			               //rescued:
			               descriptions.setText("You rescued: ");
			               descriptions.setBounds(20, 250, 300, 50);
			     			               
			               /*
			                * if the animal spawned was Sheep, Rabbit or Pig
			                * AND
			                * the user had 4 correct tiles ('goodTiles')
			                */
			               if (animalSpawned <= 3 && goodTiles == 4)
			               {
			            	  //the rescue is now a success, so display the animal that was rescued
			            	  rescueSuccess = true;
			            	  rescuedAnimal.setIcon(new ImageIcon("Data/BigAnimal" + animalSpawned + ".png"));			   	
			               }
			               
			               /*
			                * or if the animal spawned was Cow, Horse, or Unicorn
			                * AND
			                * the user had 3 correct tiles ('goodTiles')
			                */
			               else if (animalSpawned >= 4 && goodTiles == 3)
			               {
			            	   //the rescue is now a success, so display the animal that was rescued
			            	   rescueSuccess = true;
			            	   rescuedAnimal.setIcon(new ImageIcon("Data/BigAnimal" + animalSpawned + ".png"));
			               }
			               		               		               
			               //if the user qualifies for none of the above
			               else
			               {
			            	   /*
			            	    * get a special message from Steve
			            	    * and this rescue is a fail
			            	    */
			            	   descriptions.setText("You ripped grass. Good job. -Steve");
			            	   descriptions.setBounds(0, 250, 750, 50);
			            	   rescueSuccess = false;
			            	   rescuedAnimal.setIcon(new ImageIcon("Data/Blank.png"));
			               }           	   		               
			              
			               //now it's time to add the animal, if the rescue was successful
			               if(rescueSuccess)
			               {        	   		
			            	   //each value (1 ~ 6) of animalSpawned represents an animal
			            	   switch(animalSpawned)
			            	   {
			            	   case 1:
			            		   //first declare a new Sheep 
			            		   Sheep s = new Sheep();
			            		   
			            		   //if this Sheep is one of the first four Sheep
			            		   if (Sheep.getNumSheep() < 5 && Sheep.getNumExtraSheep() == 0)
			            			   //add the Sheep object to a slot in the sheep Array
			            			   sheep[Sheep.getNumSheep() - 1] = s;
			            		   break;
			            		   
			            		   //each of the cases below work the same as 'case 1', for each animal
			            	   case 2:
			            		   Rabbit r = new Rabbit();		
			            		   
			            		   if (Rabbit.getNumRabbit() < 5 && Rabbit.getNumExtraRabbit() == 0)
			            			   rabbit[Rabbit.getNumRabbit() - 1] = r;
			            		   break;
			            	   
			            	   case 3:
			            		   Pig p = new Pig();
			            		   
			            		   if (Pig.getNumPig() < 5 && Pig.getNumExtraPig() == 0)
			            			   pig[Pig.getNumPig() - 1] = p;
			            		   break;
			            		   
			            	   case 4:
			            		   Cow c = new Cow();
			            		   
			            		   if (Cow.getNumCow() < 5 && Cow.getNumExtraCow() == 0)
			            			   cow[Cow.getNumCow() - 1] = c;
			            		   break;
			            		   
			            	   case 5: 
			            		   Horse h = new Horse();
			            		   
			            		   if (Horse.getNumHorse() < 5 && Horse.getNumExtraHorse() == 0)
			            			   horse[Horse.getNumHorse() - 1] = h;
			            		   break;
			            		   
			            	   case 6:
			            		   Unicorn u = new Unicorn();
			            		   
			            		   if (Unicorn.getNumUnicorn() < 5 && Unicorn.getNumExtraUnicorn() == 0)
			            			   unicorn[Unicorn.getNumUnicorn() - 1] = u;       
			            		   break;
			            	   }
			               }
			               
			            }
			        }
					);
					
					timer.start();
				}
			}
		}
		
		for (int i = 0; i < 6; i++)
		{
			//check each of the 6 teleporting mechanism
			if (e.getSource() == animalPens[i])
			{				
				//set each of the animal pictures to the correct ones
				for (int j = 0; j < 4; j++)
				{
					animalDisplay[j].setIcon(new ImageIcon("Data/BigAnimal" + (i + 1) + ".png"));
					animalDisplay[j].setEnabled(false);
					animalNames[j].setText("Not Acquired");					
				}						
								
				//now the user is currently on the 'i' animal
				currentlyOn = i;
								
				switch (i)
				{
				//if the user is on Sheep
				case 0:		
					//set the description to match the animal
					descriptions.setText(Sheep.getRarity() + " Sheep Pen - Rescued: " + Sheep.getNumSheep());
					
					/*
					 * for the number of Sheep rescued, 
					 * update each of the JLabels' names
					 * enable each JButton
					 */
					for (int a = 0; a < Sheep.getNumSheep(); a++)
					{
						animalNames[a].setText(sheep[a].getName());
						animalDisplay[a].setEnabled(true);
					}
					
					//if the user is currently victorious, each animal name will be 'Victory!'
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}						
					
					break;
				
					//each of the cases below work the same as 'case 0', for each animal
				case 1:			
					descriptions.setText(Rabbit.getRarity() + " Rabbit Pen - Rescued: " + Rabbit.getNumRabbit());

					for (int b = 0; b < Rabbit.getNumRabbit(); b++)
					{
						animalNames[b].setText(rabbit[b].getName());
						animalDisplay[b].setEnabled(true);
					}
					
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}
					break;
				
				case 2:
					descriptions.setText(Pig.getRarity() + " Pig Pen - Rescued: " + Pig.getNumPig());
				
					for (int c = 0; c < Pig.getNumPig(); c++)
					{
						animalNames[c].setText(pig[c].getName());
						animalDisplay[c].setEnabled(true);
					}
					
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}
					break;
				
				case 3:
					descriptions.setText(Cow.getRarity() + " Cow Pen - Rescued: " + Cow.getNumCow());
										
					for (int d = 0; d < Cow.getNumCow(); d++)
					{
						animalNames[d].setText(cow[d].getName());
						animalDisplay[d].setEnabled(true);
					}
					
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}
					break;				
					
				case 4:
					descriptions.setText(Horse.getRarity() + " Horse Pen - Rescued: " + Horse.getNumHorse());

					for (int f = 0; f < Horse.getNumHorse(); f++)
					{
						animalNames[f].setText(horse[f].getName());
						animalDisplay[f].setEnabled(true);
					}
					
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}
					break;
				
				case 5:
					descriptions.setText(Unicorn.getRarity() + " Unicorn Pen - Rescued: " + Unicorn.getNumUnicorn());
										
					for (int g = 0; g < Unicorn.getNumUnicorn(); g++)
					{
						animalNames[g].setText(unicorn[g].getName());
						animalDisplay[g].setEnabled(true);
					}
					
					if (isVictory())
					{
						descriptions.setText("VICTORY!!!!!!");
						
						for (int j = 0; j < 4; j++)
						{
							animalNames[j].setText("Victory!");
						}
					}
					break;						
					
				}
			}
		}
		
		for (int i = 0; i < 4; i++)
		{
			//for each of the 4 animal JButtons
			if (e.getSource() == animalDisplay[i])
			{
				//first check for which Animal Pen the user is currently browsing
				switch (currentlyOn)
				{
				//if the user is browsing Sheep
				case 0:
					//show an InputDialog for the user to name the Sheep
					sheep[i].setName(JOptionPane.showInputDialog("Give your Sheep a new name."));
					animalNames[i].setText(sheep[i].getName());
					break;
					
				case 1:
					rabbit[i].setName(JOptionPane.showInputDialog("Give your Rabbit a new name."));
					animalNames[i].setText(rabbit[i].getName());
					break;
					
				case 2:
					pig[i].setName(JOptionPane.showInputDialog("Give your Pig a new name."));
					animalNames[i].setText(pig[i].getName());
					break;
					
				case 3:
					cow[i].setName(JOptionPane.showInputDialog("Give your Cow a new name."));
					animalNames[i].setText(cow[i].getName());
					break;
					
				case 4:
					horse[i].setName(JOptionPane.showInputDialog("Give your Horse a new name."));
					animalNames[i].setText(horse[i].getName());
					break;
					
				case 5:
					unicorn[i].setName(JOptionPane.showInputDialog("Give your Unicorn a new name."));
					animalNames[i].setText(unicorn[i].getName());
				}
			}
		}
		
		//if the user wishes to exit the game
		if (e.getSource() == exitGame)
		{
			//exit and save
			exitAndSave();
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
		//the user gets 10 rescue tries.
		rescueTries = 10;
		
		//change the descriptions to fit the context
		descriptions.setText("Rescue This:                  Rescue Tries: " + rescueTries);
		descriptions.setBounds(50, 25, 700, 50);
			
		//hide the Animal Pen screen and reveal the Rescue Board screen
		screens[2].setVisible(false);
		screens[3].setVisible(true);
		
		//enable each button, because it was previously disabled 
		for (int j = 0; j < playingField.length; j++)
		{
			playingField[j].setEnabled(true);
		}
		
		//time to pick the animal.
		pickAnimal();		
	}

	private void pickAnimal()
	{
		//roll a number from 1 ~ 100
		animalRandomNumber = (int)(Math.random()*100 + 1);
				
		/*
		 * as described above (when declaring variables), 
		 * each animal is randomly spawned using a RNG		
		 */
		if(animalRandomNumber > 0 && animalRandomNumber < 26)
			makePattern("Sheep");	//call the makePattern method with the corresponding String parameter
		
		else if(animalRandomNumber > 25 && animalRandomNumber < 51)
			makePattern("Rabbit");
		
		else if(animalRandomNumber > 50 && animalRandomNumber < 76)
			makePattern("Pig");
		
		else if(animalRandomNumber > 75 && animalRandomNumber < 86)
			makePattern("Cow");
		
		else if(animalRandomNumber > 85 && animalRandomNumber < 96)
			makePattern("Horse");		
		
		else if (animalRandomNumber > 95 && animalRandomNumber < 101)
			makePattern("Unicorn");
	}

	private void makePattern(String s)
	{
		//each animal has a different pattern
		switch(s)
		{
		case "Sheep": 	
			//the animal spawned is the first one in this case
			animalSpawned = 1;
			animalType.setIcon(new ImageIcon("Data/Animal1.jpg"));
			
			//generate pattern...
			int rowNum = (int)(Math.random()*5 + 1);
			int firstTile = (int)(Math.random()*2 + 1) + ((rowNum - 1) * 5) - 1;
			
			for (int i = 0; i < Sheep.getNumTiles(); i++)
			{
				correctTiles[i] = (firstTile + i);
			}
			
			break;
			
			/*
			 * each case below works the same as case "Sheep", with the differences being
			 * 
			 * --> The animalSpawned value
			 * --> animalType's image
			 * --> the code that generates each pattern
			 * --> the pattern itself
			 */
			
		case "Rabbit":
			animalSpawned = 2;
			animalType.setIcon(new ImageIcon("Data/Animal2.jpg"));
			
			int firstTile2 = (int)(Math.random()*9 + 1);
			
			for (int i = 0; i < Rabbit.getNumTiles(); i++)
			{
				correctTiles[i] = (firstTile2 + (i * 5));
			}
				
			break;
			
		case "Pig":
			animalSpawned = 3;
			animalType.setIcon(new ImageIcon("Data/Animal3.jpg"));
			
			int rowNum3 = (int)(Math.random()*4 + 1);
			int firstTile3 = ((int)(Math.random()*4 + 1)) + ((rowNum3 - 1) * 5) - 1;
			
			for (int i = 0; i < 2; i++)
			{
				correctTiles[i] = (firstTile3 + i);
			}
			
			for (int i = 0; i < 2; i++)
			{
				correctTiles[i + 2] = (firstTile3 + 5 + i);
			}
				
			break;
			
		case "Cow":
			animalSpawned = 4;
			animalType.setIcon(new ImageIcon("Data/Animal4.jpg"));
			
			int rowNum4 = (int)(Math.random()*5 + 1);
			int firstTile4 = (int)(Math.random()* 3 + 1) + ((rowNum4 - 1) * 5) - 1;
			
			for (int i = 0; i < Cow.getNumTiles(); i++)
			{
				correctTiles[i] = (firstTile4 + i); 
			}
			
			break;
			
		case "Horse":
			animalSpawned = 5;
			animalType.setIcon(new ImageIcon("Data/Animal5.jpg"));
			
			int firstTile5 = (int)(Math.random()*14 + 1);
			
			for (int i = 0; i < Horse.getNumTiles(); i++)
			{
				correctTiles[i] = (firstTile5 + (i * 5));
			}
			
			break;
			
		case "Unicorn":
			animalSpawned = 6;
			animalType.setIcon(new ImageIcon("Data/Animal6.jpg"));
			
			int rowNum6 = (int)(Math.random()*4 + 1);
			int firstTile6 = (int)(Math.random()*3 + 1) + ((rowNum6 - 1) * 5) - 1;
			
			correctTiles[0] = (firstTile6);
			
			for (int i = 0; i < 2; i++)
			{
				correctTiles[i + 1] = (firstTile6 + 6 + i);
			}
			
		}
	}
	
	private void exitAndSave()
	{
		IO.openOutputFile("Data\\SaveFile" + saveFile + ".txt");
		
		/*
		 * basically, re-write each text file with new info
		 * 
		 * each line represents different information
		 * check 'Save File Format.txt' for details
		 */		
		
		IO.println("true");
		IO.println(Sheep.getNumSheep() + "");
		IO.println(Sheep.getNumExtraSheep() + "");
		IO.println(Rabbit.getNumRabbit() + "");
		IO.println(Rabbit.getNumExtraRabbit() + "");
		IO.println(Pig.getNumPig() + "");
		IO.println(Pig.getNumExtraPig() + "");
		IO.println(Cow.getNumCow() + "");
		IO.println(Cow.getNumCow() + "");
		IO.println(Horse.getNumHorse() + "");
		IO.println(Horse.getNumExtraHorse() + "");
		IO.println(Unicorn.getNumUnicorn() + "");
		IO.println(Unicorn.getNumExtraUnicorn() + "");
		
		for (int i = 0; i < Sheep.getNumSheep(); i++)
			IO.println(sheep[i].getName());
		
		for (int i = 0; i < 4 - Sheep.getNumSheep(); i++)
			IO.println("Not Acquired");
		
		for (int i = 0; i < Rabbit.getNumRabbit(); i++)
			IO.println(rabbit[i].getName());
		
		for (int i = 0; i < 4 - Rabbit.getNumRabbit(); i++)
			IO.println("Not Acquired");
		
		for (int i = 0; i < Pig.getNumPig(); i++)
			IO.println(pig[i].getName());
		
		for (int i = 0; i < 4 - Pig.getNumPig(); i++)
			IO.println("Not Acquired");
		
		for (int i = 0; i < Cow.getNumCow(); i++)
			IO.println(cow[i].getName());
		
		for (int i = 0; i < 4 - Cow.getNumCow(); i++)
			IO.println("Not Acquired");
		
		for (int i = 0; i < Horse.getNumHorse(); i++)
			IO.println(horse[i].getName());
		
		for (int i = 0; i < 4 - Horse.getNumHorse(); i++)
			IO.println("Not Acquired");
		
		for (int i = 0; i < Unicorn.getNumUnicorn(); i++)
			IO.println(unicorn[i].getName());
		
		for (int i = 0; i < 4 - Unicorn.getNumUnicorn(); i++)
			IO.println("Not Acquired");
		
		IO.closeOutputFile();
		
		System.exit(0);
	}
	
	private void loadFile() throws IOException
	{
		IO.openInputFile("Data\\SaveFile" + saveFile + ".txt");
				
		/*
		 * each line represents different information
		 * check 'Save File Format.txt' for details
		 */		
		
		String temp = IO.readLine();
		int numSheep = Integer.parseInt(IO.readLine()); 		
		for (int i = 0; i < numSheep; i++)
		{
			Sheep s = new Sheep();
 		   	sheep[Sheep.getNumSheep() - 1] = s;			
		}
		
		int numExtraSheep = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraSheep; i++)
		{
			Sheep s = new Sheep();
		}
		
		int numRabbit = Integer.parseInt(IO.readLine()); 
		for (int i = 0; i < numRabbit; i++)
		{
			Rabbit r = new Rabbit();
 		   	rabbit[Rabbit.getNumRabbit() - 1] = r;			
		}
		
		int numExtraRabbit = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraRabbit; i++)
		{
			Rabbit r = new Rabbit();
		}
		
		int numPig = Integer.parseInt(IO.readLine()); 
		for (int i = 0; i < numPig; i++)
		{
			Pig p = new Pig();
 		   	pig[Pig.getNumPig() - 1] = p;			
		}
		
		int numExtraPig = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraPig; i++)
		{
			Pig p = new Pig();
		}
			
		int numCow = Integer.parseInt(IO.readLine()); 
		for (int i = 0; i < numCow; i++)
		{
			Cow c = new Cow();
 		   	cow[Cow.getNumCow() - 1] = c;			
		}
		
		int numExtraCow = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraCow; i++)
		{
			Cow c = new Cow();
		}
		
		int numHorse = Integer.parseInt(IO.readLine()); 
		for (int i = 0; i < numHorse; i++)
		{
			Horse h = new Horse();
 		   	horse[Horse.getNumHorse() - 1] = h;			
		}
		
		int numExtraHorse = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraHorse; i++)
		{
			Horse h = new Horse();
		}
		
		int numUnicorn = Integer.parseInt(IO.readLine()); 
		for (int i = 0; i < numUnicorn; i++)
		{
			Unicorn u = new Unicorn();
 		   	unicorn[Unicorn.getNumUnicorn() - 1] = u;			
		}
		
		int numExtraUnicorn = Integer.parseInt(IO.readLine());		
		for (int i = 0; i < numExtraUnicorn; i++)
		{
			Unicorn u = new Unicorn();
		}
		
		for (int i = 0; i < numSheep; i++)
		{
			sheep[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numSheep); i++)
		{
			IO.readLine();
		}	
		
		for (int i = 0; i < numRabbit; i++)
		{
			rabbit[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numRabbit); i++)
		{
			IO.readLine();
		}
		
		for (int i = 0; i < numPig; i++)
		{
			pig[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numPig); i++)
		{
			IO.readLine();
		}
		
		for (int i = 0; i < numCow; i++)
		{
			cow[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numCow); i++)
		{
			IO.readLine();
		}		
		
		for (int i = 0; i < numHorse; i++)
		{
			horse[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numHorse); i++)
		{
			IO.readLine();
		}
		
		for (int i = 0; i < numUnicorn; i++)
		{
			unicorn[i].setName(IO.readLine());
		}		
			
		for (int i = 0; i < (4 - numUnicorn); i++)
		{
			IO.readLine();
		}
		
		IO.closeInputFile();
	}
}

/* The original UML Diagram 
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

/* Added after Friday the 9th
- isNewGame: boolean
- rescueSuccess: boolean
- animalDisplay: JButton[]
- farmColour: Color
- animalSpawned: int
- correctTiles: int[]
- goodTiles: int
- currentlyOn: int
- sheep: Sheep[]
- Pig: Rabbit[]
- pig: Pig[]
- cow: Cow[]
- horse: Horse[]
- unicorn: Unicorn[]

- exitAndSave(): void 
- loadFile(): void
*/

/* removed after Friday the 9th
- gameStarted: boolean
- menuBoard: JPanel
- animalHotbar: JPanel
- animalCount: JLabel
*/

/* Functions changed after Friday the 9th
- animalType: JLabel
- makePattern(Object o): void ---> makePattern(String s): void
*/