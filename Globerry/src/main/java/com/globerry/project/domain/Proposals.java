package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.List;

public class Proposals
{
    private int id;
    private int cityId;
    private List<Tour> tourList = new ArrayList<Tour>();
    private List<Air> airList = new ArrayList<Air>();
    private List<Hotel> hotelList = new ArrayList<Hotel>();
    private List<Auto> autoList = new ArrayList<Auto>();

    public List<Tour> getTourList()
    {
	return tourList;
    }

    public void setTourList(List<Tour> tourList)
    {
	this.tourList = tourList;
    }

    public int getCityId()
    {
	return cityId;
    }

    public void setCityId(int cityId)
    {
	this.cityId = cityId;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public List<Air> getAirList()
    {
	return airList;
    }

    public void setAirList(List<Air> airList)
    {
	this.airList = airList;
    }

    public List<Hotel> getHotelList()
    {
	return hotelList;
    }

    public void setHotelList(List<Hotel> hotelList)
    {
	this.hotelList = hotelList;
    }

    public List<Auto> getAutoList()
    {
	return autoList;
    }

    public void setAutoList(List<Auto> autoList)
    {
	this.autoList = autoList;
    }

}
