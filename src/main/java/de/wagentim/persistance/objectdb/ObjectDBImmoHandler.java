package de.wagentim.collector.persistance.objectdb;

import de.wagentim.collector.entity.Immo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

public class ObjectDBImmoHandler extends AbstractObjectDBHandler
{
	
	protected List<Immo> immos = null;

	// DB Query
	private static final String QUERY_GET_ALL_Music = "SELECT P FROM Immo p";

	public ObjectDBImmoHandler(String dbLocation)
	{
		super(dbLocation);
	}

	public List<Immo> getAllMusic()
	{
		return immos;
	}

	public void getAllImmoFromDB(String uuid)
	{
		EntityManager em = getEntityManager(uuid);

		if (em == null)
		{
			return;
		}

		TypedQuery<Immo> query = em.createQuery(QUERY_GET_ALL_Music, Immo.class);
		immos = query.getResultList();

	}

	public boolean containImmo(Immo immo)
	{
		Iterator<Immo> it = immos.iterator();
		while(it.hasNext())
		{
			Immo im = it.next();
			if(im.comp(immo))
			{
				im.update(immo);
				return true;
			}
		}

		return false;
	}

	public void addImmo(Immo immo)
	{

		logger.info("Add New Immo -> [ " + immo.getLocation() + " - " + immo.getPrice() +  " ]");
		immos.add(immo);
	}

	public void saveBackToDB(String uuid)
	{
		logger.info("=== Save to DB ===");
		EntityManager em = getEntityManager(uuid);
		em.getTransaction().begin();
		Iterator<Immo> it = immos.iterator();
		while (it.hasNext())
		{
			Immo p = it.next();
			em.persist(p);
		}
		em.getTransaction().commit();

		logger.info("=== Save to DB Finished ===");
	}

}
