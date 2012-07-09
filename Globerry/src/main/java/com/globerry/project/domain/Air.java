package com.globerry.project.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Air")
public class Air
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "cost")
    private float cost;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Date date;
    
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public float getCost()
    {
	return cost;
    }
    public void setCost(float cost)
    {
	this.cost = cost;
    }
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
    }
    public Date getDate()
    {
	return date;
    }
    public void setDate(Date date)
    {
	this.date = date;
    }
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Air)) return false;
	Air air = (Air) obj;
	if(!(air.getCost() == this.getCost())) return false;
	
	if(this.date == null ^ air.getDate() == null) return false;
	if(!((this.date == null && air.getDate() == null) || air.getDate().equals(this.getDate()))) return false;
	
	if(this.name == null ^ air.getName() == null) return false;
	if(!((this.name == null && air.getName() == null) || air.getName().equals(this.getName()))) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 4;
	result = 3*result + Float.floatToIntBits(cost);
	result = 3*result + (date == null ? 0 : date.hashCode());
	result = 3*result + (name == null ? 0 : name.hashCode());
	return result;
    }
}
