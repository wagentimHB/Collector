package de.wagentim.collector.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.wagentim.collector.utils.IConstants;

@Entity
public class Product
{
	private String pName = IConstants.TXT_EMPTY_STRING;
	private String link = IConstants.TXT_EMPTY_STRING;
	@ElementCollection
	private List<Price> priceList = new ArrayList<Price>();
	private int amout = 0;
	private int productID = -1;
	
	private static final Logger logger = LoggerFactory.getLogger(Product.class);
	
	public int getProductID()
	{
		return productID;
	}
	public void setProductID(int productID)
	{
		this.productID = productID;
	}
	public String getProductName()
	{
		return pName;
	}
	public void setProductName(String p_name)
	{
		this.pName = p_name;
	}
	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public List<Price> getPriceList()
	{
		return priceList;
	}
	public void setPriceList(List<Price> priceList)
	{
		this.priceList = priceList;
	}
	public int getAmout()
	{
		return amout;
	}
	public void setAmout(int amout)
	{
		this.amout = amout;
	}
	
	public void addPrice(Price price)
	{
		priceList.add(price);
	}
	
	public void update(Product newProduct, Price newPrice)
	{
		if(!getProductName().equals(newProduct.getProductName()))
		{
			logger.info("Product Name Changed: [ " + getProductID() + ": " + getProductName() + " <-> " + newProduct.getProductName() + " ]");
			setProductName(newProduct.getProductName());
		}
		
		if(!getLink().equals(newProduct.getLink()))
		{
			logger.info("Product Link Changed: [ " + getProductID() + ": " + getProductName() + " ]");
			setLink(newProduct.getLink());
		}
		
		if(getAmout() != newProduct.getAmout())
		{
			logger.info("Product Amount Changed: [ " + getProductID() + ": " + getProductName() + " ]" + " [ " + getAmout() + " <-> " + newProduct.getAmout() + " ]");
			setAmout(newProduct.getAmout());
		}
		
		checkPrice(newPrice);
	}
	
	private void checkPrice(Price newPrice)
	{
		Price lastPrice = findLastPrice();
		if(lastPrice == null)
		{
			logger.info("By Update Product the old Price is not saved. Save with new Price: " + newPrice.getPrice());
			priceList.add(newPrice);
		}
		else
		{
			if(lastPrice.getPrice() != newPrice.getPrice())
			{
				logger.info("Product Price Changed: [ " + getProductID() + ": "  + getProductName() + " ]" + " [ " + lastPrice.getPrice() + " <-> " + newPrice.getPrice() + " ]");
				priceList.add(newPrice);
			}
			
			if(lastPrice.getOrigPrice() != newPrice.getOrigPrice())
			{
				logger.info("Product Original Price Changed: [ " + getProductID() + ": "  + getProductName() + " ]" + " [ " + lastPrice.getOrigPrice() + " <-> " + newPrice.getOrigPrice() + " ]");
				Price p = findLastPrice();
				if(p != null)
				{
					p.setOrigPrice(newPrice.getOrigPrice());
				}
			}
		}
	}
	
	public Price findLastPrice()
	{
		Price lastPrice = null;
		
		Iterator<Price> it = this.priceList.iterator();
		
		while(it.hasNext())
		{
			Price currPrice = it.next();
			
			if(lastPrice == null || lastPrice.getTime() < currPrice.getTime())
			{
				lastPrice = currPrice;
			}
		}

		return lastPrice;
	}
	
	public List<Price> findLast2Prices()
	{
		Price lastPrice = null;
		Price lastBefore = null;
		List<Price> result = new ArrayList<Price>();
		
		Iterator<Price> it = this.priceList.iterator();
		
		while(it.hasNext())
		{
			Price currPrice = it.next();
			
			if(lastPrice == null)
			{
				lastPrice = currPrice;
			}
			else if(currPrice.getTime() > lastPrice.getTime())
			{
				lastBefore = lastPrice;
				lastPrice = currPrice;
			}
			else
			{
				if(lastBefore == null || currPrice.getTime() > lastBefore.getTime())
				{
					lastBefore = currPrice;
				}
				
			}
		}
		
		if(lastBefore != null && lastPrice != null)
		{
			result.add(lastPrice);
			result.add(lastBefore);
		}
		
		return result;
	}
}
