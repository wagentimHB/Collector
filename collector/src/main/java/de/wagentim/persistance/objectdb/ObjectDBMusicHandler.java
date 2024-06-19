package de.wagentim.collector.persistance.objectdb;

import de.wagentim.collector.entity.Music;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

public class ObjectDBMusicHandler extends AbstractObjectDBHandler
{
	
	protected List<Music> musics = null;

	// DB Query
	private static final String QUERY_GET_ALL_Music = "SELECT P FROM Music p";

	public ObjectDBMusicHandler(String dbLocation)
	{
		super(dbLocation);
	}

	public List<Music> getAllMusic()
	{
		return musics;
	}

	public void getAllMusicFromDB(String uuid)
	{
		EntityManager em = getEntityManager(uuid);

		if (em == null)
		{
			return;
		}

		TypedQuery<Music> query = em.createQuery(QUERY_GET_ALL_Music, Music.class);
		musics = query.getResultList();

	}

	public boolean containMusic(Music music)
	{
		Iterator<Music> it = musics.iterator();
		while(it.hasNext())
		{
			Music tm = it.next();
			if(tm.comp(music))
			{
				return true;
			}
		}

		return false;
	}

	public void addMusic(Music music)
	{

		logger.info("Add New Music -> [ " + music.getSinger() + " - " + music.getSongName() +  " ]");
		musics.add(music);
	}

	public void saveBackToDB(String uuid)
	{
		logger.info("=== Save to DB ===");
		EntityManager em = getEntityManager(uuid);
		em.getTransaction().begin();
		Iterator<Music> it = musics.iterator();
		while (it.hasNext())
		{
			Music p = it.next();
			em.persist(p);
		}
		em.getTransaction().commit();

		logger.info("=== Save to DB Finished ===");
	}

}
