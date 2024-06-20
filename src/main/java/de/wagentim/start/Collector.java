package de.wagentim.collector.start;

import java.util.ArrayList;
import java.util.List;

import de.wagentim.collector.sites.main.AbstractSite;

public abstract class Collector 
{

    protected List<AbstractSite> handlers = new ArrayList<AbstractSite>();

    public Collector()
    {
        addSites();
    }

    abstract void addSites();

    protected void run()
	{
		
		for(AbstractSite handler : handlers)
		{
           
            if(handler != null)
            {

            }
		
		}
	}
}
