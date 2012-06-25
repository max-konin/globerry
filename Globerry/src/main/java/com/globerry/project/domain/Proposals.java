package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
public class Proposals
{
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(
	    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
	    fetch = FetchType.LAZY,
	    mappedBy="proposals"
	    )
    private City city;
    
    @OneToMany(
	    	cascade=CascadeType.ALL,
	    	fetch=FetchType.EAGER
	    	)
    private Set<Tour> tourList = new HashSet<Tour>();
    
    public Set<Tour> getTourList()
    {
	return tourList;
    }

    public void setTourList(Set<Tour> tourList)
    {
	this.tourList = tourList;
    }
    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public City getCity()
    {
	return city;
    }

    public void setCity(City city)
    {
	this.city = city;
    }

}
