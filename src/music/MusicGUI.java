package music;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.Image;

import gui.CenterFrame;
import gui.Drawable;
import gui.DrawPanel;
import gui.EasyGridBag;
import gui.ImageLoader;
import gui.DrawImage;


public class MusicGUI extends CenterFrame implements Drawable,ActionListener
{
	private JComboBox<Artist> cArtists; 
	private ManySongs listSongs;
	private SingleSong listSong;
	private JComboBox<CD> cCDs;
	private DrawPanel drawable;
	private boolean highlightcheck;
	
	public MusicGUI(int width,int height,java.util.Iterator<Artist> iter)
	{
		super(width,height,"Music");
		setLayout(new BorderLayout());
		JPanel centerG = new JPanel();
		add(centerG,BorderLayout.CENTER);

		ImageLoader il = ImageLoader.getImageLoader();
		Image icon = il.getImage("resources/icon/musicicon.png");
		setIconImage(icon);


		Dimension d = new Dimension(10,17);
		listSong = new SingleSong();
		listSong.setPreferredSize(d);
		
		JPanel northP = new JPanel();
		northP.setLayout(new GridLayout(1,2));
		cArtists = new JComboBox<Artist>();
		cCDs = new JComboBox<CD>();
		northP.add(cArtists);
		northP.add(cCDs);
		add(northP,BorderLayout.NORTH);
		
		drawable = new DrawPanel();
		drawable.setDrawable(this);
		
		listSongs = new ManySongs();
		listSongs.setFont(new Font("Verdana",Font.PLAIN,12));
		listSongs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane songScroll = new JScrollPane();
		songScroll.setPreferredSize(d);
		songScroll.getViewport().add(listSongs);
		
		JPanel scroll = new JPanel();
		scroll.setLayout(new BorderLayout());
		scroll.add(songScroll,BorderLayout.CENTER);
		scroll.add(listSong,BorderLayout.SOUTH);
		
		EasyGridBag gbag = new EasyGridBag(1,4,centerG);
		centerG.setLayout(gbag);
		
		gbag.fillCellWithRowColSpan(1,2,1,4,GridBagConstraints.BOTH,drawable);
		gbag.fillCellWithRowColSpan(1,1,1,1,GridBagConstraints.BOTH,scroll);
		
		fillArtists(iter);
		cArtists.setActionCommand("artist");
		cArtists.addActionListener(this);
		cArtists.setSelectedIndex(0);
		
		cCDs.setActionCommand("cd");
		cCDs.addActionListener(this);
		cCDs.setSelectedIndex(0);
		
		DragSource drag = DragSource.getDefaultDragSource();
		DragGestureRecognizer dgr = drag.createDefaultDragGestureRecognizer(listSongs,DnDConstants.ACTION_COPY,listSongs);
		DropTarget target = new DropTarget(listSong,listSong);
		
		highlightcheck = false;
		setVisible(true);
	}
	
	public void draw(Graphics g, int width, int height)
	{
		//get the CD that is selected in the combo box
		//tell the CD to draw itself
		int sel_index = cCDs.getSelectedIndex();
		CD cd = cCDs.getItemAt(sel_index);
		if(highlightcheck)
		{
			cd.drawHighLight(g);
		}
		else
		{
			cd.draw(g);
		}
	}
	
	public void keyPressed(char key)
	{
		System.out.println("The " + key + "was pressed.");
	}
	
	
	public void mouseClicked(int x, int y)
	{
		//System.out.println("x is " + x);
		//System.out.println("y is " + y);
		if(x > 100 && x < 300 && y > 100 && y < 300)
		{
			highlightcheck = true;
			repaint();
		}
		else
		{
			highlightcheck = false;
		}
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getActionCommand().equals("artist"))
		{
			cCDs.removeAllItems();
			int sel_index = cArtists.getSelectedIndex();
			Artist artist = cArtists.getItemAt(sel_index);
			java.util.Iterator<CD> iter = artist.iterator();
			while(iter.hasNext())
			{
				CD cd = iter.next();
				cCDs.addItem(cd);
			}
		}
		if (ae.getActionCommand().equals("cd"))
		{
			int sel_index = cCDs.getSelectedIndex();
			CD cd = cCDs.getItemAt(sel_index);
			if(cd != null)
			{
				listSongs.setCD(cd);
				repaint();
			}

		}
	}
	
	
	public void fillArtists(java.util.Iterator<Artist> iter)
	{
		while(iter.hasNext())
		{
			Artist artist = iter.next();
			cArtists.addItem(artist);
		}
	}
}