package de.wagentim.collector.sites.immobilien;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.wagentim.collector.persistance.objectdb.ObjectDBImmoHandler;
import de.wagentim.collector.entity.Immo;
import de.wagentim.collector.utils.IConstants;

public class EbayKleinAnzeigeGrundStück extends FirefoxSite
{

    private static final String SELECT_ACCEPT = "div#gdpr-banner button#gdpr-banner-accept";
    private static final String SELECT_CLOSE_LOGIN = "header section#site-header-top section#site-signin div.login-overlay--content a";
    private static final String SELECT_SEARCH_CONTENT = "header section#site-searchbar div#site-search-query-wrp input";
    private static final String SELECT_SEARCH_PLACE = "header section#site-searchbar div#site-search-area-wrp input";
    private static final String SELECT_FOUND_BUTTON = "header section#site-searchbar form#site-search-form button";
    private static final String SELECT_ITEM_LIST = "body div.position-relative li";
    private static final String SELECT_ITEM_LOCATION = "div.aditem-main div.aditem-main--top--left";
    private static final String SELECT_ITEM_RELEASE_TIME = "div.aditem-main div.aditem-main--top--right";
    private static final String SELECT_ITEM_TITLE = "div.aditem-main div.aditem-main--middle a";
    private static final String SELECT_ITEM_PRICE = "div.aditem-main div.aditem-main--middle p.aditem-main--middle--price";
    private static final String SELECT_ITEM_SIZE = "div.aditem-main div.aditem-main--bottom span";

    private ObjectDBImmoHandler dbHandler;
    private String uuid = IConstants.TXT_EMPTY_STRING;

    public EbayKleinAnzeigeGrundStück()
    {
        dbHandler = new ObjectDBImmoHandler("D:\\db\\ebay_gartengrundstueck.odb");
    }

    @Override
    public void execute()
    {
		uuid = dbHandler.createEntityManager();
		dbHandler.getAllImmoFromDB(uuid);

        // Step 1: open the entry page
        openPage(getStartLink(), false, false);

        // Step 2: login pupup

        // Step 3: remove term popup
        WebElement we = firefoxDriver.findElement(By.cssSelector(SELECT_ACCEPT));
        if(we != null)
        {
            we.click();
        }

        // Step 4: input search content
        we = firefoxDriver.findElement(By.cssSelector(SELECT_SEARCH_CONTENT));
        if(we != null)
        {
            we.sendKeys("gartengrundstück");
        }

        // Step 4: input search place
        we = firefoxDriver.findElement(By.cssSelector(SELECT_SEARCH_PLACE));
        if(we != null)
        {
            we.sendKeys("ludwigsburg");
        }

        handlePage(we);
        
		dbHandler.saveBackToDB(uuid);
		dbHandler.exit();
        //webDriver.quit();
    }

    private void handlePage(WebElement we) 
    {
        
        // Step 5: click the search button
        we = firefoxDriver.findElement(By.cssSelector(SELECT_FOUND_BUTTON));
        if(we != null)
        {
            we.click();
            WebDriverWait wait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(SELECT_ITEM_LIST)));
        }

        // Step 6: find item list
        List<WebElement> list = firefoxDriver.findElements(By.cssSelector(SELECT_ITEM_LIST));
        if(we != null && list.size() > 0)
        {
            for(WebElement w : list)
            {
                Immo im = new Immo();
                try
                {
                    we = w.findElement(By.cssSelector("article.aditem"));
                    String temp = we.findElement(By.cssSelector(SELECT_ITEM_LOCATION)).getText();
                    im.setLocation(temp);

                    temp = we.findElement(By.cssSelector(SELECT_ITEM_RELEASE_TIME)).getText(); 
                    im.setTime(temp);

                    temp = we.findElement(By.cssSelector(SELECT_ITEM_TITLE)).getText();
                    im.setTitle(temp);

                    temp = we.findElement(By.cssSelector(SELECT_ITEM_PRICE)).getText();
                    im.setPrice(temp);

                    temp = we.findElement(By.cssSelector(SELECT_ITEM_SIZE)).getText();
                    im.setSize(temp);

                    temp = we.findElement(By.cssSelector(SELECT_ITEM_TITLE)).getAttribute("href");
                    im.setLink(temp);
                }
                catch(Exception e)
                {
                    continue;
                }

                if(shouldAddImmo(im))
                {
                    if(!dbHandler.containImmo(im))
                        dbHandler.addImmo(im);
                }
            }
        }
    }

    private boolean shouldAddImmo(Immo im) 
    {
        if(im.getPrice().toLowerCase().contains("such"))
            return false;
        else if(im.getTitle().toLowerCase().contains("such"))
            return false;

        return true;
    }

    @Override
    protected String getStartLink() 
    {
        return "https://www.ebay-kleinanzeigen.de";
    }

    @Override
    protected String getSiteName() 
    {
        return "Ebay Kleinanzeigen";
    }
}
