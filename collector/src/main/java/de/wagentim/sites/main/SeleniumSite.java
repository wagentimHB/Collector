package de.wagentim.collector.sites.main;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.constants.IStringConstants;
import de.wagentim.collector.crawler.DummySeleniumCrawler;
import de.wagentim.collector.crawler.ICrawler;
import de.wagentim.collector.crawler.SeleniumCrawler;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class SeleniumSite extends AbstractSite {

    protected SeleniumCrawler seleniumCrawler;

    @Override
    public void setCrawler(ICrawler crawler) {
        if(crawler instanceof SeleniumCrawler) {
            this.seleniumCrawler = seleniumCrawler;
        }

        logger.error("Assign the wrong type: {}", SeleniumCrawler.class.getCanonicalName());
        this.seleniumCrawler = new DummySeleniumCrawler();
    }


}
