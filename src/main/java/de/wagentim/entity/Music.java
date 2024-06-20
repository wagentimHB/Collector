package de.wagentim.collector.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import de.wagentim.collector.utils.IConstants;

@Entity
public class Music
{
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	private String songName = IConstants.TXT_EMPTY_STRING;
	private String singer = IConstants.TXT_EMPTY_STRING;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getSongName() {
		return songName;
	}

	public String getSinger() {
		return singer;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	@Override
	public String toString() {
		return "Music{" +
				"songName='" + songName + '\'' +
				", singer='" + singer + '\'' +
				'}';
	}

	public String getMusicNameMP3()
	{
		return this.singer + " - " + this.songName + ".mp3";
	}

    public boolean comp(Music music)
	{
		if(this.songName.equalsIgnoreCase(music.getSongName()) && this.singer.equalsIgnoreCase(music.getSinger()))
		{
			return true;
		}

		return false;
    }
}
