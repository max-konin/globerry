package com.globerry.project.domain;

public class Temperature
{
    private City city;
    private Month month;
    private int val;
    public City getCity()
    {
	return city;
    }
    public void setCity(City city)
    {
	this.city = city;
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
