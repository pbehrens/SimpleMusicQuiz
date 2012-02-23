package soundPlayer;


import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class MainWW extends JFrame implements ActionListener
{
	//gui stuff
	private JTextArea textA;
	private JRadioButton fiveR;
	private JTextArea textArea;
	private JFrame help,about;
	private JRadioButton tenR;
	private JRadioButton custR;
	private JTextField custV;
	private JLabel fiveL ;
	private JLabel tenL;
	private JButton startB;
	private JFrame trackList;
	//fc stuff 
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JPopupMenu popmenu;
	private  JFileChooser fc;
	//constraints
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	//non gui stuff
	private Database db;
	public MainWW()
	{
		JFrame frame = new JFrame();
		Container pane = getContentPane();
		db = new Database();
		textA = new JTextArea("Welcome \nto the \nQuiztastic \nMusic Quiz \nGame");
		fiveR = new JRadioButton();
		tenR = new JRadioButton();
		custR = new JRadioButton();
		custV = new JTextField();
		fiveL = new JLabel("5 Song Quiz");
		tenL = new JLabel("10 Song Quiz");
		startB = new JButton("Start");
		menuBar = new JMenuBar();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		pane.setLayout(layout);
		//text area
		//total label
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pane.add(textA,c);

		//radio buttons
		//fiveR;
		ButtonGroup btnG = new ButtonGroup();
		btnG.add(fiveR);
		btnG.add(tenR);
		btnG.add(custR);
		c.gridx = 2;
		pane.add(fiveR,c);

		c.gridy = 1;
		pane.add(tenR,c);

		c.gridy = 2;
		pane.add(custR,c);

		//labels for radio buttons
		c.gridx = 4;
		c.gridy = 0;
		pane.add(fiveL,c);

		c.gridy = 1;
		pane.add(tenL,c);

		c.gridy = 2;
		//pane.add(custV);


		//add start button
		c.gridy = 4;
		c.gridx = 1;
		pane.add(startB,c);
		db = new Database();

		//menubar setup
		menu = new JMenu("File");
		menuItem = new JMenuItem("Help");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem); 
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		//about window
		about = new JFrame();
		JPanel aboutpanel = new JPanel();
		textArea= new JTextArea("This program was made by: \n Pat Behrens & Jon McKeown \n for Mr. Clark's Apcs");
		aboutpanel.add(textArea);
		about.add(aboutpanel);
		about.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		about.setSize(200,100);
		about.setLocation(500,250);

		//  help window
		help = new JFrame();
		JPanel helppanel = new JPanel();
		textArea= new JTextArea("Instructions: \n1) turn the music up! \n2) Select the button that corresponds to the song playing \n3) 10pts for every correct answer -5 for wrong answers ");
		helppanel.add(textArea);
		help.add(helppanel);
		help.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		help.setSize(330,110);
		help.setLocation(500,250);

		//song menu 
		// trackList = new List(db.exportDB());


		startB.addActionListener(this);
		frame.add(pane);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocation(100, 50);
		frame.setVisible(true);

	}
	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		// File[] file = fc.getSelectedFiles();
		if(btn == startB)
		{
			//get stuff from radio buttons here
			//create an array list from teh database
			//add in stuff to parse radio buttons or to make it easy just have a text field
			Queue<ArrayList<MP3>> sets = new LinkedList<ArrayList<MP3>>();
			sets = db.getSongs(10);
			System.out.println("//////////////////////");
			System.out.println(sets);

			Game newGame = new Game(sets);
		}

		if (e.getActionCommand().equals("About")) 
		{
			about.setVisible(true);
		}
		else if (e.getActionCommand().equals("Exit"))
		{
			System.exit(0);
		}
		else if (e.getActionCommand().equals("Help")) 
		{
			help.setVisible(true);
		}


	}
	public static void main(String[] args)
	{
		JFrame frame = new MainWW();



	}
}
//class MyFilter extends javax.swing.filechooser.FileFilter 
//{
// public boolean accept(File file) {
//   String filename = file.getName();
//   return filename.endsWith(".mp3");
// }
//
// public String getDescription() {
//   return "*.mp3";
// }
//}