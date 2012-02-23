package soundPlayer;
import java.util.*;
import java.io.*;
public class Database 
{
	private TreeMap<Integer, MP3> database;
	private File lib;
	private ArrayList<MP3> list;
	public Database(File aLibraryPath)
	{
		lib = aLibraryPath;
	}
	
	public Database()
	{
		String path = "./Library";
		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
	   	list = new ArrayList<MP3>();	   
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();

				if (files.endsWith(".mp3") || files.endsWith(".MP3"))
				{
					MP3 song = new MP3("./Library/" + files);
					//database.put(database.size(),song);
					//System.out.println(song.title);
					list.add(song);
				}
			}
		}	
	}
		
	
	public void importNewTracks(File songs) throws FileNotFoundException, IOException 
	{
		
	    try {
	      //use buffering reading one line at a time
	      BufferedReader input =  new BufferedReader(new FileReader(songs));
	      try {
	        String line = null; 
	        //go through the input buffer and read the line and do all sorts of jazz with the file path given
	        while (( line = input.readLine()) != null)
	        {
	        	//add mp3 objects into the map of songs 
	    		MP3 track = new MP3(line);
	    		database.put(database.size()+1, track);
	    		
	    		//next write the file name and path into the library file 
	    		
	    		//check if there are any problems
	    	if (lib == null) {
				throw new IllegalArgumentException("The Library should not be null.");
		    }
		    if (!lib.exists()) {
		    	throw new FileNotFoundException ("The library does not exist: " + lib);
		    }
		    if (!lib.isFile()) {
		    	throw new IllegalArgumentException("Should not be a directory: " + lib);
		    }
			if (!lib.canWrite()) {
				throw new IllegalArgumentException("Library cannot be written: " + lib);
			}
			// create a buffered writer to write to the library
		    Writer output = new BufferedWriter(new FileWriter(lib));
		    try 
		    {
		        //FileWriter always assumes default encoding is OK!
		        output.write( line );
		        output.write("\n");
		    }
		    	finally 
		    	{
		        output.close();
		    	}
	        }
	      }
	      finally 
	      {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	}

	public Queue<ArrayList<MP3>> getSongs(int numSets)
	{
		//creta and array list of array lists of mp3 objects
		Queue<ArrayList<MP3>> sets = new LinkedList<ArrayList<MP3>>();
		//random number
		Random gen = new Random();
		for(int c = 0; c<numSets; c++)
		{
			ArrayList<MP3> tracks = new ArrayList<MP3>();
			for(int i = 0; i<4; i++)
			{
				//add a random track to the track list
				int num = gen.nextInt(list.size());//+1;
				boolean ready = false;

					for(MP3 each : tracks)
					{
						if(each.title == list.get(num).title || list.get(num).title == null)
						{	
						num = gen.nextInt(list.size());//+1;
						}
					}
					tracks.add(list.get(num));
				//set one song in the list of tracks to true so it is the song that will be played
			}
			int pos = gen.nextInt(tracks.size());
			MP3 change = tracks.get(pos);
			change.setCorrect();
			tracks.set(pos,change);
			//add track list to the sets of tracks
			sets.add(tracks);
		}
		return sets;	
	}
}
