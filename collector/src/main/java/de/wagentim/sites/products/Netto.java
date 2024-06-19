package de.wagentim.collector.sites.products;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.sites.ProductSiteHandler;
import de.wagentim.collector.sites.main.AbstractSite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Netto extends AbstractSite {

    @Override
    public void execute() {

    }

    @Override
    protected String getStartLink() {
        return null;
    }

    @Override
    protected String getSiteName() {
        return "Netto Online";
    }
}
