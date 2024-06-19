package de.wagentim.collector.start;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.sites.musics.Flitting;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class MusicCollector extends Collector
{

	@Override
	void addSites() 
	{
		handlers.add(new Flitting());
	}
	
}
