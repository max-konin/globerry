package com.globerry.project.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
public class PropertyType
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", unique = true)
    private String name;
    @Column
    private boolean dependingMonth;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="left", column = @Column(name="minimumValue") ),
            @AttributeOverride(name="right", column = @Column(name="maximumValue") )
    } )
    private Interval interval = new Interval();
    @Column
    private boolean betterWhenLess = true; 
    @Transient
    private Integer hashCode = null;
    
    public PropertyType()
    {
	
    }
    public PropertyType(String name, Interval interval, boolean dependingMonth, boolean betterWhenLess)
    {
	this.name = name;
	this.interval = interval;
	this.dependingMonth = dependingMonth;
	this.betterWhenLess = betterWhenLess;
    }
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

    public boolean isDependingMonth()
    {
	return dependingMonth;
    }
    public void setDependingMonth(boolean dependingMonth)
    {
	this.dependingMonth = dependingMonth;
    }
    public int getMaxValue()
    {
	return interval.getRight();
    }
    public void setMaxValue(int maxValue)
    {
	this.interval.setRight(maxValue);
    }
    public int getMinValue()
    {
	return interval.getLeft();
    }
    public void setMinValue(int minValue)
    {
	this.interval.setLeft(minValue);
    }
    public boolean isBetterWhenLess()
    {
	return betterWhenLess;
    }
    public void setBetterWhenLess(boolean betterWhenLess)
    {
	this.betterWhenLess = betterWhenLess;
    }
    public Interval getInterval()
    {
        return interval;
    }
    public void setInterval(Interval interval)
    {
        this.interval = interval;
    }
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof PropertyType)) return false;
	PropertyType propertyType = (PropertyType) obj;
	
	if(this.name == null ^ propertyType.getName() == null) return false;
	if(!((this.name == null && propertyType.getName() == null) || this.name.equals(propertyType.getName()))) return false; 
	
	if(!(this.dependingMonth == propertyType.isDependingMonth())) return false;
	if(!(this.getMaxValue() == propertyType.getMaxValue())) return false;
	if(!(this.getMinValue() == propertyType.getMinValue())) return false;
	if(!(this.betterWhenLess == propertyType.isBetterWhenLess())) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	if(hashCode != null) return hashCode;
	int result = 11;
	result = 3 * result + (name == null ? 0 : name.hashCode());
	result = 3 * result + (dependingMonth ? 0 : 1);
	result = 3 * result + (betterWhenLess ? 0 : 1);
	result = 3 * result + this.getMaxValue();
	result = 3 * result + this.getMinValue();
	return result;
    }
}
