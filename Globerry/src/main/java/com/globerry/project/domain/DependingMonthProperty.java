package com.globerry.project.domain;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table
public class DependingMonthProperty
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Month month;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER )
    @JoinTable(name="DMonthPropertyPropertyType",
        joinColumns = @JoinColumn(name="DMonthProperty_id"),
        inverseJoinColumns = @JoinColumn(name="PropertyType_id")
    )
    private PropertyType propertyType;
    @Column
    private float value;
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public Month getMonth()
    {
	return month;
    }
    @JsonIgnore
    public void setMonth(Month month)
    {
	this.month = month;
    }
    @JsonIgnore
    public void setMonth(int monthNumber)
    {
	this.month = Month.values()[monthNumber];
    }
    public void setMonth(String month)
    {
	this.month = Month.valueOf(month);
    }
    public float getValue()
    {
	return value;
    }
    public void setValue(float value)
    {
	this.value = value;
    }
    public PropertyType getPropertyType()
    {
	return propertyType;
    }
    public void setPropertyType(PropertyType propertyType)
    {
	this.propertyType = propertyType;
    }
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof DependingMonthProperty)) return false;
	DependingMonthProperty dmp = (DependingMonthProperty) obj;
	
	if(this.month == null ^ dmp.getMonth() == null) return false;
	if(!((this.month == null && dmp.getMonth() == null) || this.month.equals(dmp.getMonth()))) return false;
	
	if(this.propertyType == null ^ dmp.getPropertyType() == null)
	if(!((this.propertyType == null && dmp.getPropertyType() == null) || this.propertyType.equals(dmp.getPropertyType()))) return false;
	if(!(this.value == dmp.getValue())) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 7;
	result = 3*result + (this.month == null ? 0 : this.month.hashCode());
	result = 3*result + (this.propertyType == null ? 0 : this.propertyType.hashCode());
	result = 3*result + Float.floatToIntBits(this.value);
	return result;
    }
   
}
