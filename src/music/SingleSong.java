package music;

import javax.swing.JList;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class SingleSong extends JList implements MP3Listener,DropTargetListener
{
	private static String empty = new String("Drag song here");
	private Thread song_thread;
	private MP3 mp3;
	
	public SingleSong()
	{
		super();
		setObject(empty);
		song_thread = new Thread(new NullRunnable());
		mp3 = null;
	}
	

	public void setObject(Object obj)
	{
		Object[] item = {obj};
		setListData(item);
	}
	
	public void clear()
	{
		setObject(empty);
	}
	
	/*public void mp3Done()
	{
		clear();
	} */
	
	public void drop(DropTargetDropEvent dtde)
	{
		Transferable transfer = dtde.getTransferable();
		DataFlavor[] flavors = transfer.getTransferDataFlavors();
		Song dropSong;
		try
		{
			dropSong = (Song) transfer.getTransferData(flavors[0]);
		}
		catch (UnsupportedFlavorException ufe)
		{
			dtde.rejectDrop();
			return;
		}
		catch (java.io.IOException ioe)
		{
			dtde.rejectDrop();
			return;
		}
		
		dtde.acceptDrop(DnDConstants.ACTION_COPY);
		
		dtde.dropComplete(true);
		setObject(dropSong);
		if(mp3 == null)
		{
			mp3 = new MP3(dropSong,this);
		}
		//song_thread = new Thread(mp3);
		//song_thread.start();
		if(song_thread.getState() != Thread.State.TERMINATED) 
		{
			//song_thread.stop();
			mp3.stopSong();
		}
		mp3 = new MP3(dropSong,this);
		song_thread = new Thread(mp3);
		song_thread.start(); 
	}
	
	public void MP3Done()
	{
		clear();
	}
	
	public void dragExit(DropTargetEvent dte)
	{
		
	}
	
	public void dragEnter(DropTargetDragEvent dtde)
	{
		
	}
	
	public void dragOver(DropTargetDragEvent dtde)
	{
		
	}
	
	public void dropActionChanged(DropTargetDragEvent dtde)
	{
		
	}
}