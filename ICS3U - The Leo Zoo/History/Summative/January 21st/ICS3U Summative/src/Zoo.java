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
 * @TODO 	fix a bunch of errors				
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
	
	//will be true if the animal was rescued.
	private boolean rescueSuccess;
	
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
	 * animalType: displays the picture of the animal being rescued
	 * descriptions: displays various information (Ex: title, help, other info, etc. - and this changes often)
	 * rescuedAnimal: displays the picture of the animal that was rescued
	 */
	private JLabel animalType, descriptions, rescuedAnimal;
	
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
	 * the ArrayLists that store each animal. 
	 * When an animal is rescued, an index is added to the corresponding ArrayList
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
		
		exitGame = new JButton("Exit Game");
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
			rescueTries = -1;
			timer.stop();
			
			descriptions.setVisible(true);
			descriptions.setBounds(25, 25, 700, 50);
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
			descriptions.setText(Sheep.getRarity() + " Sheep Pen - Rescued: " + Sheep.getNumSheep());
			
			currentlyOn = 0;
			
			for (int i = 0; i < 4; i++)
			{
				animalDisplay[i].setIcon(new ImageIcon("Data/BigAnimal1.png"));
				animalDisplay[i].setEnabled(false);
				animalNames[i].setText("Not Acquired");					
			}
			
			for (int a = 0; a < Sheep.getNumSheep(); a++)
			{
				animalNames[a].setText(sheep[a].getName());
				animalDisplay[a].setEnabled(true);
			}
			
			screens[4].setVisible(false);
			screens[2].setVisible(true);				 
			
			rescueSuccess = false;
			
			if (isVictory())
				descriptions.setText("VICTORY HAS BEEN ACHIEVED!");
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
			descriptions.setText("Select Saved Slot");
			descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 72));
			
			screens[0].setVisible(false);
			screens[1].setVisible(true);
			
			for (int j = 0; j < 3; j++)
			{
				files[j].setEnabled(false);
			}
			
			for (int i = 0; i < 3; i++)
			{
				IO.openInputFile("Data\\SaveFile" + (i + 1) + ".txt");			
				
				try 
				{					
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
			
			isNewGame = false;	
		}
		
		for (int i = 0; i < files.length; i++)
		{
			if (e.getSource() == files[i])
			{
				saveFile = (i + 1);
				
				if (isNewGame == true)
				{
					descriptions.setText("Common Sheep Pen - Rescued: 0");
					descriptions.setBounds(25, 25, 700, 50);
					descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
					
					gameStarted = true;
					
					screens[1].setVisible(false);
					screens[2].setVisible(true);
				}
				
				if (isNewGame == false)
				{										
					try 
					{
						loadFile();
					} 
					catch (IOException e1) 			
					{				
						e1.printStackTrace();
					}
							
					descriptions.setText("Common Sheep Pen - Rescued: " + Sheep.getNumSheep());
					descriptions.setBounds(25, 25, 700, 50);
					descriptions.setFont(new Font("Trebuchet MS", Font.BOLD, 32));
					
					screens[1].setVisible(false);
					screens[2].setVisible(true);
								
					currentlyOn = 0;
					
					for (int j = 0; j < 4; j++)
					{
						animalDisplay[i].setIcon(new ImageIcon("Data/BigAnimal1.png"));
						animalDisplay[i].setEnabled(false);
						animalNames[i].setText("Not Acquired");					
					}
					
					for (int a = 0; a < Sheep.getNumSheep(); a++)
					{
						animalNames[a].setText(sheep[a].getName());
						animalDisplay[a].setEnabled(true);
					}
					
				}			
			}							
		}
		
		if (e.getSource() == seeProgress)
		{
			JOptionPane.showMessageDialog	(null, "Sheep: " + Sheep.getNumSheep() + "\n"
											+ "Rabbit: " + Rabbit.getNumRabbit() + "\n" +
											"Pig: " + Pig.getNumPig() + "\n" + 
											"Cow: " + Cow.getNumCow() + "\n" + 
											"Horse: " + Horse.getNumHorse() + "\n" + 
											"Unicorn: " + Unicorn.getNumUnicorn() + "\n" +
											"Extra Sheep: " + Sheep.getNumExtraSheep() + "\n" + 
											"Extra Rabbit: " + Rabbit.getNumExtraRabbit() + "\n" +
											"Extra Pig: " + Pig.getNumExtraPig() + "\n" + 
											"Extra Cow: " + Cow.getNumExtraCow() + "\n" + 
											"Extra Horse: " + Horse.getNumExtraHorse() + "\n" + 
											"Extra Unicorn: " + Unicorn.getNumExtraUnicorn() + "\n");
		}			
				
		if (e.getSource() == startRescue)
		{
			if (animalSpawned <= 3)
			{
				for (int i = 0; i < 4; i++)
				{
					playingField[correctTiles[i]].setIcon(new ImageIcon("Data/Blank.png"));
					correctTiles[i] = -1;
				}
			}
			else
			{
				for (int i = 0; i < 3; i++)
				{
					playingField[correctTiles[i]].setIcon(new ImageIcon("Data/Blank.png"));
					correctTiles[i] = -1;
				}
			}
			
			
			for (int i = 0; i < playingField.length; i++)
			{
				playingField[i].setText(null);
			}
			
			goodTiles = 0;
			
			generatePlayfield();			
		}
		
		for (int i = 0; i < playingField.length; i++)
		{
			if (e.getSource() == playingField[i])
			{
				int failTiles = 0;				
				
				rescueTries--;
				descriptions.setText("Rescue This:                  Rescue Tries: " + rescueTries);
				playingField[i].setEnabled(false);
				
				for (int j = 0; j < correctTiles.length; j++)
				{
					if(i == correctTiles[j])
					{
						playingField[i].setIcon(new ImageIcon("Data/Animal" + animalSpawned + ".jpg"));
						playingField[i].setHorizontalAlignment(SwingConstants.CENTER);
						playingField[i].setVerticalAlignment(SwingConstants.CENTER);		
						
						goodTiles++;
					}					
					else
						failTiles++;
				}	
				
				if (failTiles == correctTiles.length)
				{
					playingField[i].setText("X");
					playingField[i].setForeground(Color.BLACK);
					playingField[i].setFont(new Font("Trebuchet MS", Font.BOLD, 96));
				}
				
				if (rescueTries == 0) 
				{
					for (int k = 0; k < playingField.length; k++)
					{
						playingField[k].setEnabled(false);
					}
					
					timer = new Timer(2000, new ActionListener()
					{ 
			            public void actionPerformed(ActionEvent e) 
			            {
			               screens[3].setVisible(false);
			               screens[4].setVisible(true);
			               
			               descriptions.setText("You rescued: ");
			               descriptions.setBounds(20, 250, 300, 50);
			     			               
			               if (animalSpawned <= 3 && goodTiles == 4)
			               {
			            	  rescueSuccess = true;
			            	  rescuedAnimal.setIcon(new ImageIcon("Data/BigAnimal" + animalSpawned + ".png"));			   	
			               }
			               else if (animalSpawned >= 4 && goodTiles == 3)
			               {
			            	   rescueSuccess = true;
			            	   rescuedAnimal.setIcon(new ImageIcon("Data/BigAnimal" + animalSpawned + ".png"));
			               }
			               else
			               {
			            	   descriptions.setText("You ripped grass. Good job. -Steve");
			            	   descriptions.setBounds(0, 250, 750, 50);
			            	   rescueSuccess = false;
			            	   rescuedAnimal.setIcon(new ImageIcon("Data/Blank.png"));
			               }           	   		               
			              
			               if(rescueSuccess)
			               {        	   			            	   
			            	   switch(animalSpawned)
			            	   {
			            	   case 1:
			            		   Sheep s = new Sheep();
			            		   
			            		   if (Sheep.getNumSheep() < 4)
			            			   sheep[Sheep.getNumSheep() - 1] = s;
			            		   break;
			            		   
			            	   case 2:
			            		   Rabbit r = new Rabbit();		
			            		   
			            		   if (Rabbit.getNumRabbit() < 4)
			            			   rabbit[Rabbit.getNumRabbit() - 1] = r;
			            		   break;
			            	   
			            	   case 3:
			            		   Pig p = new Pig();
			            		   
			            		   if (Pig.getNumPig() < 4)
			            			   pig[Pig.getNumPig() - 1] = p;
			            		   break;
			            		   
			            	   case 4:
			            		   Cow c = new Cow();
			            		   
			            		   if (Cow.getNumCow() < 4)
			            			   cow[Cow.getNumCow() - 1] = c;
			            		   break;
			            		   
			            	   case 5: 
			            		   Horse h = new Horse();
			            		   
			            		   if (Horse.getNumHorse() < 4)
			            			   horse[Horse.getNumHorse() - 1] = h;
			            		   break;
			            		   
			            	   case 6:
			            		   Unicorn u = new Unicorn();
			            		   
			            		   if (Unicorn.getNumUnicorn() < 4)
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
			if (e.getSource() == animalPens[i])
			{				
				for (int j = 0; j < 4; j++)
				{
					animalDisplay[j].setIcon(new ImageIcon("Data/BigAnimal" + (i + 1) + ".png"));
					animalDisplay[j].setEnabled(false);
					animalNames[j].setText("Not Acquired");					
				}						
								
				currentlyOn = i;
				
				switch (i)
				{
				case 0:		
					descriptions.setText(Sheep.getRarity() + " Sheep Pen - Rescued: " + Sheep.getNumSheep());
					
					for (int a = 0; a < Sheep.getNumSheep(); a++)
					{
						animalNames[a].setText(sheep[a].getName());
						animalDisplay[a].setEnabled(true);
					}
					break;
				
				case 1:			
					descriptions.setText(Rabbit.getRarity() + " Rabbit Pen - Rescued: " + Rabbit.getNumRabbit());
					
					for (int b = 0; b < Rabbit.getNumRabbit(); b++)
					{
						animalNames[b].setText(rabbit[b].getName());
						animalDisplay[b].setEnabled(true);
					}
					break;
				
				case 2:
					descriptions.setText(Pig.getRarity() + " Pig Pen - Rescued: " + Pig.getNumPig());
					
					for (int c = 0; c < Pig.getNumPig(); c++)
					{
						animalNames[c].setText(pig[c].getName());
						animalDisplay[c].setEnabled(true);
					}
					break;
				
				case 3:
					descriptions.setText(Cow.getRarity() + " Cow Pen - Rescued: " + Cow.getNumCow());
					
					for (int d = 0; d < Cow.getNumCow(); d++)
					{
						animalNames[d].setText(cow[d].getName());
						animalDisplay[d].setEnabled(true);
					}
					break;				
					
				case 4:
					descriptions.setText(Horse.getRarity() + " Horse Pen - Rescued: " + Horse.getNumHorse());
					
					for (int f = 0; f < Horse.getNumHorse(); f++)
					{
						animalNames[f].setText(horse[f].getName());
						animalDisplay[f].setEnabled(true);
					}
					break;
				
				case 5:
					descriptions.setText(Unicorn.getRarity() + " Unicorn Pen - Rescued: " + Unicorn.getNumUnicorn());
					
					for (int g = 0; g < Unicorn.getNumUnicorn(); g++)
					{
						animalNames[g].setText(unicorn[g].getName());
						animalDisplay[g].setEnabled(true);
					}
					break;						
					
				}
			}
		}
		
		for (int i = 0; i < 4; i++)
		{
			if (e.getSource() == animalDisplay[i])
			{
				switch (currentlyOn)
				{
				case 0:
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
		
		if (e.getSource() == exitGame)
		{
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
		rescueTries = 10;
		
		descriptions.setText("Rescue This:                  Rescue Tries: " + rescueTries);
		descriptions.setBounds(50, 25, 700, 50);
			
		screens[2].setVisible(false);
		screens[3].setVisible(true);
		
		for (int j = 0; j < playingField.length; j++)
		{
			playingField[j].setEnabled(true);
		}
		
		pickAnimal();		
	}

	private void pickAnimal()
	{
		animalRandomNumber = (int)(Math.random()*100 + 1);
				
		if(animalRandomNumber > 0 && animalRandomNumber < 26)
			makePattern("Sheep");
		
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
		switch(s)
		{
		case "Sheep": 	
			animalSpawned = 1;
			animalType.setIcon(new ImageIcon("Data/Animal1.jpg"));
			
			int rowNum = (int)(Math.random()*5 + 1);
			int firstTile = (int)(Math.random()*2 + 1) + ((rowNum - 1) * 5) - 1;
			
			for (int i = 0; i < Sheep.getNumTiles(); i++)
			{
				correctTiles[i] = (firstTile + i);
			}
			
			break;
			
		case "Rabbit":
			animalSpawned = 2;
			animalType.setIcon(new ImageIcon("Data/Animal2.jpg"));
			
			int firstTile2 = (int)(Math.random()*10 + 1);
			
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
			
			int firstTile5 = (int)(Math.random()*15 + 1);
			
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
- menuBoard: JPanel
- animalHotbar: JPanel
- animalCount: JLabel
*/

/* functions chagned after Friday the 9th
- animalType: JLabel
- makePattern(Object o): void ---> makePattern(String s): void
*/