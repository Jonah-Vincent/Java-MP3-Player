package music;

import java.util.Iterator;
import table.TableInterface;
import table.TableFactory;
import table.TableException;

public class Music
{
	private TableInterface<Artist, String> artists;
	private String file_name;
	private CompareArtists comp_keys;
	private int song_count;
	
	public Music(String file)
	{
		file_name = file;
		comp_keys = new CompareArtists(true);
		artists = TableFactory.createTable(comp_keys);
		readMusic(file_name);
		song_count = 0;
	}
   
	
	public java.util.Iterator<Artist> iterator()
	{
		return artists.iterator();
	}
	
   private void readMusic(String file_name)
   {
      util.ReadTextFile rf = new util.ReadTextFile(file_name);
      String art = rf.readLine();
      while (!rf.EOF())
      {
         String title = rf.readLine();
         //System.out.println(title);
         int year = Integer.parseInt(rf.readLine());
         int rating = Integer.parseInt(rf.readLine());
         int numTracks = Integer.parseInt(rf.readLine());
         CD cd = new CD(title, art, year, rating, numTracks);

         int tracks = 1;

         while (tracks <= numTracks)
         {
            String temp = rf.readLine();
            String[] line = temp.split(",");
            String len = line[0];
            String song_title = line[1];
			Song song = new Song(song_title, len, art, cd.getTitle(), tracks);
            cd.addSong(song);
            song_count++;
            tracks++;
         }
		 
		 //DO THIS
         //if the artist isn't already present in the table, create a new artist and insert it
		 //artist = tableRetrieve(art);
		// if art is null, create a new one
		 //else use the one you got
		Artist artist = artists.tableRetrieve(art);
		if(artist != null)
		{
			//Artist artist = new Artist(art);
			artist.tableInsert(cd);
		}
		else
		{
			artist = new Artist(art);
			artist.tableInsert(cd);
			artists.tableInsert(artist);
		}
		 
		 
		 
		 

         art = rf.readLine();
      }
	  rf.close();
   }

   public static void main(String[] args)
   {
      Music mc = new Music("resources/cds.txt");
      //instantiate your GUI here
	  MusicGUI mg = new MusicGUI(640,480,mc.iterator());
   }
}