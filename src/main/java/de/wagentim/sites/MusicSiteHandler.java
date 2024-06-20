package de.wagentim.collector.sites;

import de.wagentim.collector.entity.Music;
import de.wagentim.collector.sites.main.AbstractSite;

import java.util.Iterator;
import java.util.List;

public abstract class MusicSiteHandler extends AbstractSite
{
	public static final String MUSIC_SOURCE_DIR = "F:\\DownloadMusics\\";
	public static final String MUSIC_TARGET_DIR = "F:\\Musics\\updated";

	protected void printMusics(List<Music> musics)
	{
		Iterator<Music> it = musics.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().toString());
		}
	}
}
