package de.wagentim.collector.start;

import java.util.Iterator;
import java.util.List;

import com.paulhammant.ngwebdriver.NgWebDriver;
import de.wagentim.collector.entity.Price;
import de.wagentim.collector.entity.Product;
import de.wagentim.collector.sites.products.DaJiangYou;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class ProductsCollector extends Collector
{

	public void decreasePrice()
	{
		DaJiangYou djy = new DaJiangYou();
		List<Product> lowProds = djy.getPriceDecreaseProducts();
		
		Iterator<Product> it = lowProds.iterator();
		
		while(it.hasNext())
		{
			Product p = it.next();
			List<Price> ps = p.findLast2Prices();
			double last = ps.get(1).getPrice();
			double before = ps.get(0).getPrice();
			System.out.println(p.getProductName() + "[ " + last + " ]" + " <-> [ " + before + " ]" + " = " + String.format("%.2f", before / last) + "\n" + p.getLink());
		}
	}

	@Override
	protected void addSites() 
	{
		handlers.add(new DaJiangYou());
	}

}
