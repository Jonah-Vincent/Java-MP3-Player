package music;

import table.TableInterface;
import table.TableFactory;
import table.TableException;

public class Artist 
{
	private TableInterface<CD,String> CDs;
	private String artist;
	private CompareCDTitles comp_keys;
	
	
	public Artist(String artist_name)
	{
		artist = artist_name;
		comp_keys = new CompareCDTitles(true);
		CDs = TableFactory.createTable(comp_keys);
	}
	
	public java.util.Iterator<CD> iterator()
	{
		return CDs.iterator();
	}

	public void tableInsert(CD cd)
	{
		try
		{
			CDs.tableInsert(cd);
		}
		catch (TableException e)
		{
			System.out.println("cd already added");
		}
	}
	
	/*public CD[] getCDs()
	{
		CD[] cds = new CD[CDs.tableSize()];
		java.util.Iterator<CD> iter = CD.iterator();
		int i = 0;
		while(iter.hasNext())
		{
			cds[i] = iter.next();
			i++;
		}
	} */
	
	public String getArtist()
	{
		return artist;
	}
	
	public String toString()
	{
		return artist;
	}
}