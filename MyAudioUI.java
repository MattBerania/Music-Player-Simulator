// Name: Matthew Berania
// Student Id: 501176063

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args) 
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			try
			{
			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int index1 = 0;
				int index2 = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					index1 = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					index2 = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				
				while(index1 <= index2)
				{
					try
					{
							AudioContent content = store.getContent(index1); // makes an AudioContent object which is store.getContent() at index index1
							mylibrary.download(content); // downloads content from store
							index1++; // increments index1 by 1
					}
					catch(AlreadyDownloadedException e) // catches the exception if the audio content is already downloaded
					{
						System.out.println(e.getMessage()); // prints error msg
						index1++; // increments index1 so its not an infinite loop
					}
				}			
			}

			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				System.out.print("Song Number: ");
				int index = 0; // user input int
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.playSong(index);
						
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				System.out.print("Audio Book Number: ");
				int index = 0;
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.printAudioBookTOC(index);
			
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				System.out.print("Audio Book Number: ");
				int bookNum = 0; // user input int
				if (scanner.hasNextInt())
				{
					bookNum = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("Chapter: ");
				int chap = 0; // user input int
				if (scanner.hasNextInt())
				{
					chap = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.playAudioBook(bookNum, chap);
				
			}
			
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine(); // takes in user input String

				mylibrary.playPlaylist(name);
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();	// takes in user input string and assigns to an string variable
				System.out.print("Content Number: ");
				int index = 0;	// takes in user input int and assigns it to an int variable
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.playPlaylist(name, index);
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				System.out.print("Library Song #: ");
				int remove = 0;
				if (scanner.hasNextInt())
				{
					remove = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}

				mylibrary.deleteSong(remove);
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine(); // user input
				
				mylibrary.makePlaylist(name);
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();	// user input

				mylibrary.printPlaylist(name);
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine(); // user input string
				System.out.print("Content type [SONG, AUDIOBOOK]: ");
				String type = scanner.nextLine(); // user input string
				type = type.toUpperCase(); // to uppercase
				System.out.print("Library Content #: ");
				int index = 0; // user input int
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				
				mylibrary.addContentToPlaylist(type, index, name);

			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine(); // user input string
				System.out.print("Playlist Content #: ");
				int index = 0; // user input int
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}


				mylibrary.delContentFromPlaylist(index, name);
				
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

// START OF ASSIGNMENT 2**********************************************************
			else if(action.equalsIgnoreCase("SEARCH"))
			{
				System.out.print("Title: ");
				String title = scanner.nextLine();
				
				// store.listTitle(title);
				if(store.contentTitleMap.get(title) == null) // if contentTitleMap.get(string) is null it will throw an exception
				{
					throw new AudioContentNotFoundException("No Matches For " + title); // throws AudioContentNotFound Exception
				}
				
				int index = store.contentTitleMap.get(title);
				System.out.print("" + (index+1) + ". ");
				store.contents.get(index).printInfo();

			}
			else if(action.equalsIgnoreCase("SEARCHA"))
			{
				System.out.print("Artist: ");
				String artist = scanner.nextLine();
			
				// store.listArtist(artist);
				if(store.contentArtistMap.get(artist) == null) // if no arraylist is attached to artist given 
				{
					throw new AudioContentNotFoundException("No Matches For " + artist); // throws AudioContentNotFound Exception
				}
				
				for(int i = 0; i < store.contentArtistMap.get(artist).size(); i++ ) // loops through arraylist of artist given(using contentArtistMap)
				{
						int index = store.contentArtistMap.get(artist).get(i); // finds indexs from arraylist attached to artist 
						System.out.print("" + (index+1) + ". "); 
						store.contents.get(index).printInfo();
						System.out.println();	
				}
				
			}
			else if(action.equalsIgnoreCase("SEARCHG"))
			{
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = (scanner.nextLine()).toUpperCase(); // easier for user to type since its toUpperCase
				
				if(store.contentGenreMap.get(genre) == null) // if no arraylist is attached to genre given 
				{
					throw new InvalidGenreException("Invalid Genre"); // throws InvalidGenreException
				}

				for(int i = 0; i < store.contentGenreMap.get(genre).size(); i++) // loops through arraylist attached to string value 'genre'
				{
						int index = store.contentGenreMap.get(genre).get(i); // finds index stored in array list
						System.out.print("" + (index+1) + ". ");
						store.contents.get(index).printInfo(); // prints
						System.out.println();
				}
			}
			else if(action.equalsIgnoreCase("DOWNLOADA"))
			{
				System.out.print("Artist: ");
				String artist = scanner.nextLine();
				if(store.contentArtistMap.get(artist) == null) // if store.contentArtistMap.get(artist) is empty, meaning there is no arraylist
				{
					throw new AudioContentNotFoundException("No Matches for " + artist); // throws Audio Content Not Found Exception

				}
				
				for(int i = 0; i < store.contentArtistMap.get(artist).size(); i++)
				{
					try
					{
						AudioContent content = store.getContent((store.contentArtistMap.get(artist).get(i))+1); // gets index stored arraylist attached to key in contentArtistMap. uses the index for store.getContent
						mylibrary.download(content);
					}
					catch(AlreadyDownloadedException e) // if content is already downloaded
					{
						System.out.println(e.getMessage());
					}
					catch(AudioContentNotFoundException e) // if Audio content is not found
					{
						System.out.println(e.getMessage());
					}
				}
				
				
			}
			else if(action.equalsIgnoreCase("DOWNLOADG"))
			{
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre = (scanner.nextLine()).toUpperCase();
				
				if(store.contentGenreMap.get(genre) == null) // if no arraylist is attached to genre given 
				{
					throw new InvalidGenreException("Invalid Genre"); // throws exception when store.contentGenreMap.get(genre) is null
				}
			
				for(int i = 0; i < store.contentGenreMap.get(genre).size(); i++) // loops through arraylist which is the value of the key for contentGenreMap
				{
					
					try
					{
						AudioContent content = store.getContent((store.contentGenreMap.get(genre).get(i))+1); // gets the index stored in the arraylist from the key in contentGenreMap and uses it for getContent
						mylibrary.download(content); // downloads content
					}
					catch(AlreadyDownloadedException e) // if already downloaded
					{
						System.out.println(e.getMessage());
					}
					catch(InvalidGenreException e)	// if invalid genre
					{
						System.out.println(e.getMessage());
					}
				}
				
			}

			System.out.print("\n>");
		}
		// every catch 
		catch(AlreadyDownloadedException e)
		{	
			System.out.println(e.getMessage());
		}
		catch(AlreadyExistsException e)
		{
			System.out.println(e.getMessage());
		}
		catch(AudioContentNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(InvalidGenreException e)
		{
			System.out.println(e.getMessage());
		}
		catch(InvalidTypeException e)
		{
			System.out.println(e.getMessage());
		}
		}
	}
}
