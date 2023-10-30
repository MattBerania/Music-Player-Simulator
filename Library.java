// Name: Matthew Berania
// Student Id: 501176063

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}

	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content) 
	{
		
		String type = "";	// declaring a string type and setting it to type of the content variable from the parameters
		
		if(content == null)
		{
			throw new AudioContentNotFoundException("Content Not Found Store"); // throws custom AudioContentNotFound Exception
		}
		else
		{
			type = content.getType();
		}

		if(type.equals(Song.TYPENAME))	// if type is equal to SONG
		{
			Song cont = (Song) content;	// creates a Song object using the content variable from parameters
			for(int i = 0; i < songs.size(); i++)	// loops through the songs arraylist to check if the song given is already in the list
			{
				if(songs.get(i).equals(content)) // checks if the songs at index 'i' is equal to content
				{
					throw new AlreadyDownloadedException(cont.getType() + " " + cont.getTitle() + " Already Downloaded"); // throws custom AlreadyDownloadedException
				}
			}
			songs.add(cont);	// adds our Song object to songs list
			System.out.println(cont.getType() + " " + cont.getTitle()+ " Added to Library"); 

		}
		else if(type.equals(AudioBook.TYPENAME))	// if type is AUDIOBOOK
		{
			AudioBook cont = (AudioBook) content;	// creates a AudioBook object using the content variable from parameters
			for(int i = 0; i < audiobooks.size(); i++)	// loops through audiobooks list
			{
				if(audiobooks.get(i).equals(content)) // if audiobooks at index 'i' is equal to content
				{
					throw new AlreadyDownloadedException(cont.getType() + " " + cont.getTitle() + " Already Downloaded"); // throws custom AlreadyDownloadedException 
				}
			}
			audiobooks.add(cont);	// adds our Audiobook object to audiobooks list
			System.out.println(cont.getType() + " " + cont.getTitle()+ " Added to Library");
		}
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)	// for loops through songs
		{
			int index = i + 1;
			System.out.print("" + index + ". ");	
			songs.get(i).printInfo(); // gets songs at index 'i' and prints info using printinfo()
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for(int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		// not doing
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for(int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". " + playlists.get(i).getTitle());
			System.out.println();
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> art = new ArrayList<String>();
		for(int i = 0; i < songs.size(); i++)
		{
			if(!art.contains(songs.get(i).getArtist()))	// if the artist is not already in the artist arraylist
			{
				art.add(songs.get(i).getArtist());	// adds the artist to the artist(art) arraylist
			}
		}
		for(int i = 0; i < art.size(); i++)	// loops through artist arraylist
		{
			int index = i + 1; // formatting like the video
			System.out.print("" + index + ". " + art.get(i)); // formatting
			System.out.println();	//formatting 
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		if(index < 1 || index > songs.size()) // checks if the index given is valid 
		{
			// errorMsg = "Song Not Found";
			throw new AudioContentNotFoundException("Song Not Found"); // throws custom AudioContentNotFound Exception
		}
		else
		{
			Song rem = songs.get(index-1);	//makes new song object in order to use equals method

			for(int i = 0; i < playlists.size(); i++)	// loops throught the arraylist of playlists
			{
				for(int j = 0; j < playlists.get(i).getContent().size(); j++)	// loops through the playlist at index 'i'
				{
					if(playlists.get(i).getContent().get(j).equals(rem)) // if the playlist were checking at index 'j' is equal to our Song object
					{
						playlists.get(i).getContent().remove(j); // removes what is stored at index 'j'
					}
				}
			}
		}
		songs.remove(index-1);	// removes song at index - 1, this is last so we can remove from the playlist first
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());	// sorting the songs arraylist using new SongyYearComparator class we created below
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song> //I implemented the Comparator<Song> so that the compare method below works
	{
		public int compare(Song a, Song b)	// takes in 2 song object
		{
			if(a.getYear() < b.getYear()) return -1; // if less than, return -1
			if(a.getYear() > b.getYear()) return 1;	// if greater than, return 1
			return 0;	// if equal return 0

		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
		Collections.sort(songs, new SongLengthComparator()); // sorting the songs arraylist using SongLengthComparator class created below
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song> // I added implements Comparator<Song> 
	{
		public int compare(Song a, Song b) // takes in 2 song objects
		{
			if(a.getLength() < b.getLength()) return -1; // if less than, return -1
			if(a.getLength() > b.getLength()) return 1;	// if greater than, return 1
			return 0;	// if equal return 0

		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);	// sorting the songs by name 
	}
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size()) // if index is in the range of the index of songs
		{
			// errorMsg = "Song Not Found"; // error msg updated
			throw new AudioContentNotFoundException("Song Not Found"); // throws custom AudioContentNotFound Exception
		}
		else
		{
			songs.get(index-1).play(); // using play() method 
		}
	
	}
	
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size()) // if index given is in the range of audiobooks indexes 
		{
			throw new AudioContentNotFoundException("AudioBook Not Found"); // throws custom AudioContentNotFound Exception
		}
		else
		{
			if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters()) // if chapter int is in the range of number of chapters
			{
				throw new AudioContentNotFoundException("Chapter Not Found"); // throws custom AudioContentNotFound Exception
			}
			else
			{
				audiobooks.get(index-1).selectChapter(chapter); // selectChapter method using the user input chapter.
				audiobooks.get(index-1).play();	// play method
			}
		}

	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())	// if index int is in range of audiobooks index
		{
			throw new AudioContentNotFoundException("Book Not Found"); // throws custom AudioContentNotFound Exception
		}
		audiobooks.get(index-1).printTOC(); // using printTOC method in audiobook class
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist playlist = new Playlist(title); // creates new Playlist object using title given by user
		if(!playlists.contains(playlist)) // if playlist is not contained in our playlists arraylist
		{
			playlists.add(playlist); // adds playlist to playlists arraylist
		}
		else
		{

			throw new AlreadyExistsException("Playlist " + title + " Already Exists"); // throws custom AlreadyExistsException
 		}
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) 
	{
		Playlist playlist = new Playlist(title); // creates new Playlist object using title given by user
		if(playlists.contains(playlist)) // if playlist object is contained in playlists arraylist
		{
			int ind = playlists.indexOf(playlist); // finds the index of the playlist 
			playlists.get(ind).printContents(); // utilizes printContents method from playlist class, at playlists at index ind(where the playlist is located)
		}
		else
		{
			throw new AudioContentNotFoundException("Playlist Not Found"); // throws custom AudioContentNotFound Exception
		}

	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) 
	{
		Playlist playlist = new Playlist(playlistTitle); // creates new playlist object using title given
		if(playlists.contains(playlist)) // if playlist is contained in playlists
		{
			int ind = playlists.indexOf(playlist); // finds index of our playlists
			playlists.get(ind).playAll(); // uses playAll method from playlist class
		}
		else
		{
			throw new AudioContentNotFoundException("Playlist Not Found"); // throws custom AudioContentNotFound Exception
		}

	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) 
	{
		Playlist playlist = new Playlist(playlistTitle); // creates new playlist object using title given
		if(playlists.contains(playlist)) // if playlist object is contained in playlists arraylist
		{
			int index = playlists.indexOf(playlist); // finds index of the playlists
			if(!playlists.get(index).contains(indexInPL)) // if indexInPl is not contained in our playlist(using contains() method from playlist class)
			{
				throw new AudioContentNotFoundException("Playlist Content Not Found"); // throws custom AudioContentNotFound Exception
			}
			else
			{
				playlists.get(index).play(indexInPL); // uses play() method for index given of the playlist given
			}
		}
		else
		{
			throw new AudioContentNotFoundException("Playlist Not Found"); // throws custom AudioContentNotFound Exception
		}

	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		Playlist playlist = new Playlist(playlistTitle); // new playlist object using title given
		if(playlists.contains(playlist)) // if playlist object is contained in playlists arraylist
		{
			int ind = playlists.indexOf(playlist); // find the index of the playlist given
			if(type.compareTo(Song.TYPENAME) == 0) // if type is Song
			{
				if(index < 1 || index > songs.size()) // if index is in range of songs index
				{
					throw new AudioContentNotFoundException("Song Not Found"); // throws custom AudioContentNotFound Exception
				}
				else
				{
					playlists.get(ind).addContent(songs.get(index-1)); // utilizes addContent() method from playlist class
				}
			}
			else if(type.compareTo(AudioBook.TYPENAME) == 0) // if type is audiobook
			{
				if(index < 1 || index > audiobooks.size()) // if index is in range of audiobook index
				{
					throw new AudioContentNotFoundException("AudioBook Not Found"); // throws custom AudioContentNotFound Exception
				}
				else
				{
					playlists.get(ind).addContent(audiobooks.get(index-1)); // utilizes addContent() method from playlist class
				}
			}
			else
			{
				throw new InvalidTypeException("Invalid type"); // throws custom InvalidTypeException
			}
		}	
		else
		{
			throw new AudioContentNotFoundException("Playlist Not Found"); // throws custom AudioContentNotFound Exception
		}
	}

  	// Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		Playlist playlist = new Playlist(title); // creates new playlist object using title given 
		if(playlists.contains(playlist)) // if playlist object is contained in playlists arraylist
		{
			int ind = playlists.indexOf(playlist); // finds index of playlist object
			if(playlists.get(ind).contains(index)) // if index is contained in our playlists, uses contains method from playlist class
			{
				playlists.get(ind).deleteContent(index); // utilizes deleteContent method from playlist class
			}
			else
			{
				throw new AudioContentNotFoundException("Content Not Found"); // throws custom AudioContentNotFound Exception
			}
		}
		else
		{
			throw new AudioContentNotFoundException("Playlist Not Found"); // throws custom AudioContentNotFound Exception
		}
	}
}

// creating custom exceptions
class AlreadyDownloadedException extends RuntimeException{

    AlreadyDownloadedException(String Message) {
        super(Message); // super message using RuntimeException class
    }
}
class AlreadyExistsException extends RuntimeException{

    AlreadyExistsException(String Message)
    {
        super(Message);
    }
}
class AudioContentNotFoundException extends RuntimeException {

    AudioContentNotFoundException(String Message) {
        super(Message);
    }
}
class InvalidGenreException extends RuntimeException{
    InvalidGenreException(String Message)
    {
        super(Message);
    }
}
class InvalidTypeException extends RuntimeException{
    InvalidTypeException(String Message)
    {
        super(Message);
    }
}