package de.wagentim.collector.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Price
{
	private long time = 0l;
	private double price = 0.0;
	private double origPrice = 0.0;
	
	public long getTime()
	{
		return time;
	}
	public void setTime(long time)
	{
		this.time = time;
	}
	public double getPrice()
	{
		return price;
	}
	public void setPrice(double price)
	{
		this.price = price;
	}
	public double getOrigPrice()
	{
		return origPrice;
	}
	public void setOrigPrice(double origPrice)
	{
		this.origPrice = origPrice;
	}
	
	
	
}
