package de.wagentim.collector.sites.main;

import de.wagentim.collector.crawler.ICrawler;

public interface ISite
{
	void execute();
	void setCrawler(ICrawler crawler);
}
