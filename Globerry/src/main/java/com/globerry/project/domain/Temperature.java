package com.globerry.project.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@IdClass(TemperatureId.class)
@Table(name="Temperature")
public class Temperature implements Serializable
{@PrimaryKeyJoinColumn
    //@ManyToOne
    @Id
    //private City city;
    private int cityId;
    @Column(name = "month")
    @Id
    @Enumerated(EnumType.ORDINAL)
    private Month month;
    @Column(name = "val")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private int val;
    public int getCityId()
    {
	return cityId;
    }
    public void setCityId(int cityId)
    {
	this.cityId = cityId;
    }
    public Month getMonth()
    {
	return month;
    }
    public void setMonth(Month month)
    {
	this.month = month;
    }
    public int getVal()
    {
	return val;
    }
    public void setVal(int val)
    {
	this.val = val;
    }
}
