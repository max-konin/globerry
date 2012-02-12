package com.globerry.project.domain;

public class Hotel
{
    private int id;
    private float cost;
    private String name;
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
}
