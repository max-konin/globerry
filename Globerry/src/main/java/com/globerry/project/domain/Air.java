package com.globerry.project.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Air")
public class Air
{
    @Id
    private int id;
    @Column
    private float cost;
    @Column
    private String name;
    @Column
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
}
