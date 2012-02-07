package com.globerry.project.domain;

import java.util.Date;

public class Air
{
    private int id;
    private float cost;
    private String name;
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
