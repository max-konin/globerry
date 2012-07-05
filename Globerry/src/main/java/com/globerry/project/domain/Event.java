package com.globerry.project.domain;

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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.hibernate.mapping.Collection;



@Entity
@Table
public class Event implements IRelationsQualifier
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String ru_name;
    @Column
    private String description;
    @Column
    private String ru_description;
    @Column
    private String image;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Month month;
    @JsonIgnore
    @ManyToMany(
	    fetch = FetchType.LAZY,
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH},
	        targetEntity = City.class
	    )
    @JoinTable(
	           name="CityEvent",
	        	   inverseJoinColumns = @JoinColumn( name="city_id"),
	        	   joinColumns = @JoinColumn( name="event_id")
		    )
    private Set<City> cityList = new HashSet<City>();
    
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public String getDescription()
    {
	return description;
    }
    public void setDescription(String description)
    {
	this.description = description;
    }
    public Month getMonth()
    {
	return month;
    }
    public void setMonth(Month month)
    {
	this.month = month;
    }
    public void setMonth(int monthNumber)
    {
	this.month = Month.values()[monthNumber];
    }
    
    public void setMonth(String month)
    {
	this.month = Month.valueOf(month);
    }
    public String getImage()
    {
	return image;
    }
    public void setImage(String image)
    {
	this.image = image;
    }
    @JsonIgnore
    public Set<City> getCities()
    {
	return cityList;
    }
    @JsonIgnore
    public void setCities(Set<City> cities)
    {
	this.cityList = cities;
    }
    public String getRu_description()
    {
	return ru_description;
    }
    public void setRu_description(String ru_description)
    {
	this.ru_description = ru_description;
    }
    public String getRu_name()
    {
	return ru_name;
    }
    public void setRu_name(String ru_name)
    {
	this.ru_name = ru_name;
    }
    public boolean equals(Event event)
    {
	if(this.getId() == event.getId() &&
		this.getDescription().equals(event.getDescription()) &&
		this.getName().equals(event.getName()) &&
		this.getImage().equals(event.getImage()) &&
		this.getMonth().equals(event.getMonth()) && 
		this.getRu_description().equals(event.getRu_description()) &&
		this.getRu_name().equals(event.getRu_name()))
	    return true;
	else return false;
    }
}
