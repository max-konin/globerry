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
import javax.persistence.Transient;

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
    private boolean dependingMonth;
    @Column(name = "maximumValue")
    private float maxValue;
    @Column(name = "minimumValue")
    private float minValue;
    @Column
    private boolean betterWhenLess = true; 
    @Transient
    private Integer hashCode = null;
    
    
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
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof PropertyType)) return false;
	PropertyType propertyType = (PropertyType) obj;
	
	if(this.name == null ^ propertyType.getName() == null) return false;
	if(!((this.name == null && propertyType.getName() == null) || this.name.equals(propertyType.getName()))) return false; 
	
	if(!(this.dependingMonth == propertyType.isDependingMonth())) return false;
	if(!(this.maxValue == propertyType.getMaxValue())) return false;
	if(!(this.minValue == propertyType.getMinValue())) return false;
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
	result = 3 * result + Float.floatToIntBits(maxValue);
	result = 3 * result + Float.floatToIntBits(minValue);
	return result;
    }
}
