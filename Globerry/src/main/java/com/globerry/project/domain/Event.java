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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import org.hibernate.mapping.Collection;

@Entity
@Table
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private float image;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Month month;
    @ManyToMany(
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH},
	        targetEntity = City.class
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
    public float getImage()
    {
	return image;
    }
    public void setImage(float image)
    {
	this.image = image;
    }
    public Set<City> getCities()
    {
	return cityList;
    }
    public void setCities(Set<City> cities)
    {
	this.cityList = cities;
    }
}
