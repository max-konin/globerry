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
public class DependingMonthProperty implements IRelationsQualifier
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
   
}
