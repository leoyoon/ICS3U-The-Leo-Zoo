import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Test extends JFrame
{
	private JButton sheep;
	
	public Test()
	{	
		//this sets the title of the GUI
		super("ICS3U Summative Test");
		
		this.setResizable(false);
		this.setLayout(null);
		
		//this is to ensure that the program shuts down once the window has been closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//sets the size of the GUI
		setSize(800, 800);
		
		sheep = new JButton(new ImageIcon("Data/Sheep.jpg"));
		sheep.setBounds(100, 100, 81, 81);
		sheep.setSize(81, 81);
		add(sheep);
		
		setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new Test();
	}

}
