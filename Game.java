package soundPlayer;



import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.*;

public class Game implements ActionListener
{
	private JButton play;
	private JButton pause;
	private JMenuBar menuBar;
	private JTextArea textArea;
	private JFrame help,about;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JLabel score;
	private JButton a;
	private JButton b;
	private JButton c;
	private JButton d;
	private JLabel aL;
	private JLabel bL;
	private JLabel cL;
	private JLabel dL;
	private JButton skip;
	private  JFileChooser fc;
	private JLabel message;
	private JButton end;
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	//non gui things
	private Queue<ArrayList<MP3>> songs;
	private MP3 songA;
	private MP3 songB;
	private MP3 songC;
	private MP3 songD;
	private MP3 correctSong;
	// must be sent
	public Game(Queue<ArrayList<MP3>> someSongs)// ,Song correct)
	{
		songs = someSongs;
		play = new JButton("Play");
		pause= new JButton("Pause");
		skip  = new JButton("I Give Up!!");
		//buttons for each track
		a =  new JButton("A: ");
		b= new JButton("B: ");
		c= new JButton("C: ");
		d= new JButton("D: ");
		//labels for the songs
		aL = new JLabel();
		bL = new JLabel();
		cL = new JLabel();
		dL = new JLabel();
		//end button
		end = new JButton("PLAY AGAIN");
		//menu bar 
		menuBar = new JMenuBar();
		message = new JLabel();
		//j frame
		JFrame window = new JFrame();
		JPanel canvas = new JPanel();
		JPanel board = new JPanel();
		//layout
		GridBagLayout layout = new GridBagLayout();
		canvas.setLayout(layout);
		canvas.setLayout(new GridLayout(6,1));
		board.setLayout(new FlowLayout());
		//play pause
		board.add(play);
		board.add(pause);
		canvas.add(board);
		c.setSize(6,6);
		board = new JPanel();
		//a
		board.add(a);
		board.add(aL);
		canvas.add(board);
		board = new JPanel();
		//b
		board.add(b);
		board.add(bL);
		canvas.add(board);
		board = new JPanel();
		//c 
		board.add(c);
		board.add(cL);
		canvas.add(board);
		board = new JPanel();
		//d
		board.add(d);
		board.add(dL);
		canvas.add(board);
		board = new JPanel();
		//message box
		board.add(message);
		board.add(score);
		canvas.add(board);
		board = new JPanel();
		//end button
		board.add(end);
		//add action listeners to various elements
		a.addActionListener(this);
		b.addActionListener(this);
		c.addActionListener(this);
		d.addActionListener(this);
		play.addActionListener(this);
		pause.addActionListener(this);
		end.addActionListener(this);

		//set window paramneters for size and such
		//menu setup
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
		window.setJMenuBar(menuBar);

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


		//game window
		window.add(canvas);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(300,300);
		window.setLocation(500,250);
		window.setResizable(true);
		//start the game 
		//get the first buttons ready
		ArrayList<MP3> set = songs.remove();
		for(MP3 track : set)
		{
			if(track.isCorrect())
			{
				correctSong = track;
				System.out.println(track.title);
			}
		}
		songA = set.remove(0);
		songB = set.remove(0);
		songC = set.remove(0);
		songD = set.remove(0);
		//set label text
		aL.setText(songA.title + " by " + songA.artist);
		bL.setText(songB.title + " by " + songB.artist);
		cL.setText(songC.title + " by " + songC.artist);
		dL.setText(songD.title + " by " + songD.artist);
		correctSong.play();
	}
	public void right()
	{
		String temp = score.getText();
		Double temp2 = Double.parseDouble(temp);
		temp2+=10.0;
		score.setText("Score: "+ temp2);
	}
	public void wrong()
	{
		String temp = score.getText();
		Double temp2 = Double.parseDouble(temp);
		temp2-=5.0;
		score.setText("Score: "+ temp2);
	}
	public void changeButtons(ArrayList<MP3> nextSet,MP3 theeSong)
	{
		String lineSeparator = (String) java.security.AccessController.doPrivileged(
				new sun.security.action.GetPropertyAction("line.separator"));
		correctSong.close();
		if(theeSong == correctSong)
		{
			message.setText("You have chosen the correct song"+lineSeparator +"The song was "+ theeSong.title + " by " + theeSong.artist);
		}
		else 
			message.setText("You have not chosen the correct song "+lineSeparator+" The song was "+ theeSong.title + " by " + theeSong.artist);

		//find the song that is correct and set that song to correctSong
		for(MP3 song : nextSet)
		{
			if(song.isCorrect())
			{
				correctSong = song;
			}
		}
		//set the song variables to the new songs
		songA = nextSet.get(0);
		songB = nextSet.get(1);
		songC = nextSet.get(2);
		songD = nextSet.get(3);
		//set label text
		aL.setText(songA.title + " by " + songA.artist);
		bL.setText(songB.title + " by " + songB.artist);
		cL.setText(songC.title + " by " + songC.artist);
		dL.setText(songD.title + " by " + songD.artist);
		play.setEnabled(true);
		if(songs.isEmpty())
		{
			//end
			// add in score right here
			message.setText("You have completed the game your score is:" + score.getText());
			//score;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton btn = (JButton) e.getSource();
		if(e.getSource() == play)
		{
			correctSong.play();
			play.setEnabled(false);
		}
		if(e.getSource() == pause)
		{
			correctSong.close();
			play.setEnabled(true);
		}

		if(e.getSource() == a)
		{
			System.out.println("A " + songA.title);

			if(songA.equals(correctSong))
			{
				changeButtons(songs.remove(),songA);
				right();
			}
			else
			{
				changeButtons(songs.remove(),correctSong);
				wrong();
			}  
		}
		else if(e.getSource() == b)
		{
			System.out.println("B " + songB.title);

			if(songB.equals(correctSong))
			{
				changeButtons(songs.remove(),songB);
				right();
			}
			else
			{
				changeButtons(songs.remove(),correctSong);
				wrong();
			}
		}
		else if(e.getSource() == c)
		{
			System.out.println("C " + songC.title);

			if(songC.equals(correctSong))
			{
				changeButtons(songs.remove(),songC);
				right();
			}
			else
			{
				changeButtons(songs.remove(),correctSong);
				wrong();
			}
		}
		else if(e.getSource() == d)
		{
			System.out.println("D " + songD.title);

			if(songD.equals(correctSong))
			{
				changeButtons(songs.remove(),songD);
				right();
			}
			else
			{
				changeButtons(songs.remove(),correctSong);
				wrong();
			}
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
}