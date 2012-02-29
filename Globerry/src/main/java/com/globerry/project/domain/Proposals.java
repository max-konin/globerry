package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Proposals
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private int cityId;
    //@OneToMany
    private Set<Tour> tourList = new HashSet<Tour>();
    
    private Set<Air> airList = new HashSet<Air>();
    private Set<Hotel> hotelList = new HashSet<Hotel>();
    private Set<Auto> autoList = new HashSet<Auto>();

    public Set<Tour> getTourList()
    {
	return tourList;
    }

    public void setTourList(Set<Tour> tourList)
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

    public Set<Air> getAirList()
    {
	return airList;
    }

    public void setAirList(Set<Air> airList)
    {
	this.airList = airList;
    }

    public Set<Hotel> getHotelList()
    {
	return hotelList;
    }

    public void setHotelList(Set<Hotel> hotelList)
    {
	this.hotelList = hotelList;
    }

    public Set<Auto> getAutoList()
    {
	return autoList;
    }

    public void setAutoList(Set<Auto> autoList)
    {
	this.autoList = autoList;
    }

}
