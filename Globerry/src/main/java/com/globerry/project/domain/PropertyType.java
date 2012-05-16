package com.globerry.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PropertyType")
public class PropertyType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", unique = true)
    private String name;
    @Column
    private boolean DependingMonth;
    @Column(name = "maximumValue")
    private float maxValue;
    @Column(name = "minimumValue")
    private float minValue;
    @Column
    private boolean betterWhenLess = true; 
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
    public boolean equals(PropertyType propertyType)
    {
	if(
	(this.getId() == propertyType.getId())&&
	(this.getName().equals(propertyType.getName())))
	    return true;
	else 
	    return false;
    }
    public boolean isDependingMonth()
    {
	return DependingMonth;
    }
    public void setDependingMonth(boolean dependingMonth)
    {
	DependingMonth = dependingMonth;
    }
    public float getMaxValue()
    {
	return maxValue;
    }
    public void setMaxValue(float maxValue)
    {
	this.maxValue = maxValue;
    }
    public float getMinValue()
    {
	return minValue;
    }
    public void setMinValue(float minValue)
    {
	this.minValue = minValue;
    }
    public boolean isBetterWhenLess()
    {
	return betterWhenLess;
    }
    public void setBetterWhenLess(boolean betterWhenLess)
    {
	this.betterWhenLess = betterWhenLess;
    }
}
