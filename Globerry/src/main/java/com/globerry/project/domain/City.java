package com.globerry.project.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.mapping.Collection;

@Entity
@Table(name = "City")
public class City implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "ru_name")
    private String ru_name;
    @Column
    private float area;
    @Column
    private int population;
    @Column
    private float longitude;
    @Column
    private float latitude;
    @Column 
    private Boolean isValid;
    @Column
    private String message;
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(
	    fetch = FetchType.LAZY,
	    cascade = CascadeType.ALL
	    )
    @PrimaryKeyJoinColumn
    private Proposals proposals;
    //-------------------------------------------------------
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(
	    	fetch = FetchType.EAGER,
	    	cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
	    	targetEntity = Event.class,
	    	mappedBy = "cityList"
	    )
    private Set<Event> eventList = new HashSet<Event>();
    //-------------------------------------------------------
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(
	    fetch = FetchType.EAGER,
	    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
	    targetEntity = Tag.class
	    )
    private Set<Tag> tagList = new HashSet<Tag>();
    //-------------------------------------------------------
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
	    	cascade=CascadeType.ALL,
	    	fetch=FetchType.EAGER
	    	)
    @JoinTable(
           name="CityDependingMonthProperty",
           joinColumns = @JoinColumn( name="city_id"),
           inverseJoinColumns = @JoinColumn( name="dmp_id")
	    )
    private Set<DependingMonthProperty> dmpList = new HashSet<DependingMonthProperty>();
    //-------------------------------------------------------
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
	    	cascade=CascadeType.ALL,
	    	fetch=FetchType.EAGER
	    	)
    @JoinTable(
       name="CityProperty",
       joinColumns = @JoinColumn( name="city_id"),
       inverseJoinColumns = @JoinColumn( name="property_id")
	    )
    private Set<Property> propertyList = new HashSet<Property>();
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    

    /*public Proposals getProposals()
    {
	return proposals;
    }
    public void setProposals(Proposals proposals)
    {
	this.proposals = proposals;
    }
    public Option getOption()
    {
	return option;
    }
    public void setOption(Option option)
    {
	this.option = option;
    }*/
    public Set<Tag> getTagList()
    {
	return tagList;
    }
    public void setTagList(Set<Tag> tagList)
    {
	this.tagList = tagList;
    }
    public Set<Event> getEvents()
    {
	return eventList;
    }
    public void setEvents(Set<Event> events)
    {
	this.eventList = events;
    }
    public Proposals getProposals()
    {
	return proposals;
    }
    public void setProposals(Proposals proposals)
    {
	this.proposals = proposals;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public String getRu_name()
    {
	return ru_name;
    }
    public void setRu_name(String ru_name)
    {
	this.ru_name = ru_name;
    }
    public Set<DependingMonthProperty> getDmpList()
    {
	return dmpList;
    }
    public void setDmpList(Set<DependingMonthProperty> dmpList)
    {
	this.dmpList = dmpList;
    }
    public Set<Property> getPropertyList()
    {
	return propertyList;
    }
    public void setPropertyList(Set<Property> propertyList)
    {
	this.propertyList = propertyList;
    }
    public float getLongitude()
    {
	return longitude;
    }
    public void setLongitude(float longitude)
    {
	this.longitude = longitude;
    }
    public float getLatitude()
    {
	return latitude;
    }
    public void setLatitude(int i)
    {
	this.latitude = i;
    }
    public int getPopulation()
    {
	return population;
    }
    public void setPopulation(int population)
    {
	this.population = population;
    }
    public float getArea()
    {
	return area;
    }
    public void setArea(float area)
    {
	this.area = area;
    }
    public Boolean getIsValid()
    {
	return isValid;
    }
    public void setIsValid(Boolean isValid)
    {
	this.isValid = isValid;
    }
    public String getMessage()
    {
	return message;
    }
    public void setMessage(String message)
    {
	this.message = message;
    }
    public boolean equals(City city)
    {
	if(this.getId() == city.getId() &&
		this.getName().equals(city.getName()) &&
		this.getRu_name().equals(city.getRu_name()) &&
		this.getArea() == city.getArea() &&
		this.getPopulation() == city.getPopulation() &&
		this.getLatitude() == city.getLatitude() &&
		this.getLongitude() == city.getLongitude())
	    return true;
	else return false;
    }

}
