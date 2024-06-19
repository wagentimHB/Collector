package de.wagentim.collector.sites.main;

import java.util.ArrayList;
import java.util.List;

import de.wagentim.collector.crawler.ICrawler;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class AbstractSite implements ISite
{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected abstract String getStartLink();
	protected abstract String getSiteName();

	protected void print(String s)
	{
		System.out.println(s);
	}
}
