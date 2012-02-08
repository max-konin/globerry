package com.globerry.project.domain;

public class Event
{
    private int id;
    private String name;
    private String description;
    private float image;
    private Month month;
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
    public String getDescription()
    {
	return description;
    }
    public void setDescription(String description)
    {
	this.description = description;
    }
    public Month getMonth()
    {
	return month;
    }
    public void setMonth(Month month)
    {
	this.month = month;
    }
    public float getImage()
    {
	return image;
    }
    public void setImage(float image)
    {
	this.image = image;
    }
}
