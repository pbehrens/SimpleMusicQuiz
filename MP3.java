package soundPlayer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.*;
import java.io.*;
import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.test.AllTests;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;
import java.util.*;

public class MP3 {
	private String fileName;
	private AdvancedPlayer player;
	private int start, end;
	private PlaybackListener listen;
	private boolean correct;
	public String title;
	public String artist;
	// constructor that takes the name of an MP3 file
	public MP3(String filename) //throws Exception
	{
		//set the filename, start and end points for the mp3
		this.fileName = filename;
		start = 0;
		end = Integer.MAX_VALUE;
		try {
			this.readTags();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			title = "Mystery title";
			artist = "mystery artist";
			e.printStackTrace();
		}
	}

	public void close() { if (player != null) player.close(); }


	public PlaybackEvent getPos()
	{
		return player.getPos();
	}
	// play the MP3 file to the sound card
	public void play() {
		try {
			//make buffer streams for the mp3
			FileInputStream fis     = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			//instantiate a advancedplayer
			player = new AdvancedPlayer(bis);
			player.setPlayBackListener(listen);

		}
		catch (Exception e) {
			System.out.println("Problem playing file " + fileName);
			System.out.println(e);
		}

		// run in new thread to play in background so other stuff can happen 
		new Thread() {
			public void run() 
			{
				try 
				{
					player.play(start,end);
				}
				catch (Exception e) { System.out.println(e); }
			}
		}.start();
	}

	public String readTags() throws Exception
	{
		//need to add in exceptions if the file doesn't not contain any ID3 tags
		//program would look real crappy if there were no tags
		//when loading in multiple tags we need to create a window that pops up
		//encase a bunch of errors pop up 
		ArrayList<String> info = new ArrayList<String>();
		//get the file needed to be read
		File source = new File(fileName);
		//create a new media file so the ID3 library can read the mp3
		MediaFile mFile = new MP3File(source);
		//create an arrray of the tags in the mp3
		ID3Tag[] tags = mFile.getTags();

		for (int i=0; i < tags.length; i++)
		{
			if (tags[i] instanceof ID3V2_3_0Tag)
			{
				ID3V2_3_0Tag tag2_3 = (ID3V2_3_0Tag)tags[i];
				// check for title
				if(tag2_3.getTitle() != null)
				{
					title = tag2_3.getTitle();
				}
				//check artist
				if(tag2_3.getArtist() != null)
				{
					artist = tag2_3.getArtist();
				}
			}
		}
		if(artist == null)
		{
			artist = "Mystery Artist";
		}
		if(title == null)
		{
			title = "Mystery Title";
		}

		return title + " by " + artist;
	}

	public boolean isCorrect()
	{
		return correct;
	}
	public void setCorrect()
	{
		correct = true;
	}

	public String toString()
	{
		return title + "by" + artist;
	}
	public boolean equals(MP3 other)
	{
		if(this.title == other.title && this.artist == other.artist)
		{
			return true;

		}
		else 
			return false;
	}

	// test client
	public static void main(String[] args) throws Exception {
		//String filename = args[0];
		String fileName = "./test.mp3";
		MP3 mp3 = new MP3(fileName);
		//mp3.play();
		PlaybackListener current;
		mp3.play();
		PlaybackEvent evt = mp3.getPos();
		String str;
		try {
			str = mp3.readTags();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//   		title = "Mystery title";
			//   		artist = "mystery artist";

			e.printStackTrace();
		}
	}
}