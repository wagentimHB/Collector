package de.wagentim.collector.crawler;

import com.paulhammant.ngwebdriver.NgWebDriver;
import lombok.Setter;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Setter
public abstract class SeleniumCrawler extends AbstractCrawler {

    protected WebDriver webDriver;
    protected NgWebDriver ngWebDriver;

    public WebElement getShadowRoot(WebElement shadowHost) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        WebElement we = null;

        if(webDriver instanceof FirefoxDriver) {
            List<WebElement> results = (List<WebElement>) js.executeScript("return arguments[0].shadowRoot.children", shadowHost);
            we = results.get(0);
        }
        else {
            we = (WebElement) js.executeScript("return arguments[0].shadowRoot", shadowHost);
        }
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfAllElements(we));
        return we;
    }

    public WebElement getWebElement(WebElement we, String selector, boolean shouldWait) {

        if(shouldWait) {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
        }
        if(we == null) {
            return webDriver.findElement(By.cssSelector(selector));
        }

        return we.findElement(By.cssSelector(selector));
    }

    public List<WebElement> getWebElements(WebElement we, String selector, boolean shouldWait) {

        if(shouldWait) {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
        }
        if(we == null) {
            return webDriver.findElements(By.cssSelector(selector));
        }

        return we.findElements(By.cssSelector(selector));
    }

    public void openPage(String link, boolean ngWait, boolean newTab)
    {
        if(link == null || link.isEmpty())
        {
            logger.error("The input link is empty");
            return;
        }

        webDriver.get(link);

        if(ngWait)
        {
            ngWebDriver.waitForAngularRequestsToFinish();
        }
    }

    public void printElementText(List<WebElement> list)
    {
        for(WebElement we : list)
        {
            System.out.println(we.getText());
        }
    }

    public void printElementHTML(List<WebElement> list)
    {
        for(WebElement we : list)
        {
            System.out.println(we.isDisplayed());
        }
    }

    protected void openTab(WebElement webElement, String link)
    {
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
        webElement.sendKeys(selectLinkOpeninNewTab);
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1));
        webDriver.get(link);
    }

}
