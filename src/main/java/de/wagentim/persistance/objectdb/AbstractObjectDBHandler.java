package de.wagentim.collector.persistance.objectdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wagentim.collector.utils.GeneralUtils;
import de.wagentim.collector.utils.IConstants;

public abstract class AbstractObjectDBHandler
{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected String internalUUID = IConstants.TXT_EMPTY_STRING;
	protected EntityManagerFactory emf = null;
	protected Map<String, EntityManager> entityManagers = null;
	
	public AbstractObjectDBHandler(String dbLocation)
	{
		emf = Persistence.createEntityManagerFactory(dbLocation);
		entityManagers = new HashMap<String, EntityManager>();
	}
	
	public String createEntityManager()
	{
		String uuid = GeneralUtils.getUUID();
		entityManagers.put(uuid, emf.createEntityManager());	
		return uuid;
	}
	
	public EntityManager getEntityManager(String uuid)
	{
		if (uuid == null || uuid.isEmpty())
		{
			logger.error("Input UUID is NULL");
			return null;
		}

		EntityManager em = entityManagers.get(uuid);
		if (em == null)
		{
			logger.error("Cannot find Entity Manager with UUID: " + uuid);
			return null;
		}

		return em;
	}
	
	public void exit()
	{
		Iterator<EntityManager> it = entityManagers.values().iterator();

		while (it.hasNext())
		{
			EntityManager em = it.next();
			em.close();
		}

		entityManagers.clear();
		entityManagers = null;

		emf.close();
		emf = null;
	}
}
