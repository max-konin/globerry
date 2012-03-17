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
    public void setMonth(Month month)
    {
	this.month = month;
    }
    public void setMonth(int monthNumber)
    {
	this.month = Month.values()[monthNumber];
    }
    public float getValue()
    {
	return value;
    }
    public void setValue(float value)
    {
	this.value = value;
    }
    public PropertyType getPropertysType()
    {
	return propertyType;
    }
    public void setPropertysType(PropertyType propertyType)
    {
	this.propertyType = propertyType;
    }
}
