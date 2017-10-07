import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private JButton startRescue, exitGame, loadGame, newGame, seeProgress;
	private JButton[] files = new JButton[3];
	private JButton[] animalPens = new JButton[6];
	private JButton[] playingField = new JButton[25];
	private JLabel animalCount, animalType, descriptions;
	private JLabel[] animalNames = new JLabel[4];
	private JPanel rescueBoard, menuBoard, animalHotbar;
	private JPanel[] screens = new JPanel[5];
	private int animalRandomNumber, rescueTries, saveFile;
	private Timer timer;
	
	public Zoo()
	{
		
	}
	
	public static void main(String[] args)
	{
		new Zoo();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
	}

	private boolean isVictory()
	{
		
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