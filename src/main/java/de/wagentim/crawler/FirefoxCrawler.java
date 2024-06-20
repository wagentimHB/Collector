package de.wagentim.collector.crawler;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.entity.Task;
import de.wagentim.collector.sites.main.SeleniumSite;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxCrawler extends SeleniumCrawler {
    private FirefoxOption currOption;

    public FirefoxCrawler(Task task) {
        setTask(task);
    }

    private void updateFirefoxDriver() {
        if(currOption == null) {
            currOption = new FirefoxOption();
            updateOption();
        }
        else if(!currOption.isSame(task)) {
            updateOption();
        }
        
        if(webDriver == null) {
            createDriver();
        }
    }

    private void createDriver() {

        FirefoxOptions ops = new FirefoxOptions();
        ops.setHeadless(currOption.isHeadless());
        if(currOption.isTrace()) {
            ops.setLogLevel(FirefoxDriverLogLevel.TRACE);
        }
        webDriver = new FirefoxDriver(ops);
        ngWebDriver = new NgWebDriver((JavascriptExecutor) webDriver);

    }

    private void updateOption() {
        currOption.setHeadless(task.isHeadless());
        currOption.setTrace(task.isTrace());
        createDriver();
    }
}
