package de.wagentim.collector.entity;

import javax.persistence.Entity;

import de.wagentim.collector.utils.IConstants;

@Entity
public class Immo 
{
    private String location = IConstants.TXT_EMPTY_STRING;
    private String time = IConstants.TXT_EMPTY_STRING;
    private String title = IConstants.TXT_EMPTY_STRING;
    private String price = IConstants.TXT_EMPTY_STRING;
    private String size = IConstants.TXT_EMPTY_STRING;
    private String link = IConstants.TXT_EMPTY_STRING;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean comp(Immo immo) {
        if(getLink().equals(immo.getLink()) && getSize().equals(immo.getSize()) && getLocation().equals(immo.getLocation()))
            return true;

        return false;
    }

    public void update(Immo immo)
    {
        if(!getTitle().equals(immo.getTitle()))
        {
            System.out.println("Title Changed: " + immo.getTitle());
            setTitle(immo.getTitle());
        }

        if(!getPrice().equals(immo.getPrice()))
        {
            System.out.println("Price Changed: " + immo.getPrice());
            setPrice(immo.getPrice());
        }

        if(!getTime().equals(immo.getTime()))
        {
            System.out.println("Time Changed: " + immo.getTime());
            setTime(immo.getTime());
        }
    }

    public String getLink() 
    {
        return link;
    }

    public void setLink(String link) 
    {
        this.link = link;
    }
}
