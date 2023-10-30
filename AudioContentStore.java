// Name: Matthew Berania
// Student Id: 501176063

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		public ArrayList<AudioContent> contents; 
		public HashMap<String, Integer> contentTitleMap;
		public HashMap<String, ArrayList<Integer>> contentArtistMap;
		public HashMap<String, ArrayList<Integer>> contentGenreMap;

		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			
			try
			{
				contents = getFromStore(); // makes contents arraylist equal to the arraylist returned from getFromStore method.
			}
			catch(FileNotFoundException e) // if file not found
			{
				System.out.println(e.getMessage()); // prints error msg
			}

			// Title hash map
			contentTitleMap = new HashMap<String, Integer>();
			for(int i = 0; i < contents.size(); i++)
			{
				contentTitleMap.put(contents.get(i).getTitle(), i);
			}

			// artists hash map
			contentArtistMap = new HashMap<String, ArrayList<Integer>>();
			
			for(int i = 0; i < contents.size(); i++)
			{
				String name = "";
				if(contents.get(i).getType() == "SONG")	// checks type if song
				{
					name = ((Song)contents.get(i)).getArtist(); // gets artist if song
				}
				else if(contents.get(i).getType() == "AUDIOBOOK") // if audiobook
				{
					name = ((AudioBook)contents.get(i)).getAuthor(); // gets author if book
				}

				if(contentArtistMap.containsKey(name)) // if the name is already in the map
				{
					contentArtistMap.get(name).add(i);	// adds index 'i' into the arraylist for the value name
				}
				else
				{
					contentArtistMap.put(name, new ArrayList<Integer>()); // if name isnt in map, puts new key with value thats a new arraylist
					contentArtistMap.get(name).add(i); // adds to the arraylist
				}
			}
					
			// Genre hash map
			contentGenreMap = new HashMap<String, ArrayList<Integer>>();

			for(int i = 0; i < contents.size(); i++)
			{
				if(contents.get(i).getType() == "SONG")
				{
					String type = ((Song)contents.get(i)).getGenre().toString(); // takes genre and turns into a string

					if(contentGenreMap.containsKey(type)) // if genre already in map
					{
						contentGenreMap.get(type).add(i); // adds the index to arraylist
					}
					else
					{
						contentGenreMap.put(type,new ArrayList<Integer>()); //creates new array list with a new key
						contentGenreMap.get(type).add(i); // adds index to the arraylist
					}
				}
			}
		}

		private ArrayList<AudioContent> getFromStore() throws FileNotFoundException	
		{
			ArrayList<AudioContent> content = new ArrayList<AudioContent>(); 
		
			File file = new File("store.txt"); // file created from txt file
			Scanner scan = new Scanner(file); // scanner which scans file
			
			// declaring variables
			String type, id, title, sentence, artist_author, composer_narrator, genre;

			type = id = title = sentence = artist_author = composer_narrator = genre = "";

			int length, year, lyricLength, chapterNum;
			length = year = lyricLength = chapterNum = 0;

			ArrayList<String> chapters = new ArrayList<String>();
			ArrayList<String> chaptersLyrics = new ArrayList<String>();

			while(scan.hasNextLine())
			{
				type = scan.nextLine();
				id = scan.nextLine();
				title = scan.nextLine();
				year = scan.nextInt();
				length = scan.nextInt();
				scan.nextLine(); // takes away error
				artist_author = scan.nextLine();
				composer_narrator = scan.nextLine();

				if(type.equals("SONG"))
				{
					sentence = "";
					genre = scan.nextLine();
					lyricLength = scan.nextInt();
					scan.nextLine(); // takes away error
					for(int i = 0; i < lyricLength; i++)
					{
						sentence += scan.nextLine() + "\n";
					}
					content.add(new Song(title, year, id, type, sentence, length, artist_author, composer_narrator, Song.Genre.valueOf(genre), sentence)); // adds new song to content arraylist
				}
				else if(type.equals("AUDIOBOOK"))
				{
					sentence = "";
					chapterNum = scan.nextInt();
					scan.nextLine(); // removes error
					for(int i = 0; i < chapterNum; i++)
					{
						chapters.add(scan.nextLine());
					}
					for(int i = 0; i < chapterNum; i++)
					{
						lyricLength = scan.nextInt();
						scan.nextLine(); // removes error
						for(int j = 0; j < lyricLength; j++)
						{
							sentence += scan.nextLine() + "\n";
						}
						chaptersLyrics.add(sentence); // adds the text of chapter into its own arraylist
					}
					content.add(new AudioBook(title, year, id, type, genre, length, artist_author, composer_narrator, chapters, chaptersLyrics)); // adds new audiobook to content arraylist
				}
			}
			scan.close(); // closes scanner
			return content; // returns content arraylist
		}

		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
}
