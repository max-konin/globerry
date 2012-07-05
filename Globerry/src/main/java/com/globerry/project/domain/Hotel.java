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
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Hotel)) return false;
	Hotel hotel = (Hotel) obj;
	if(!((this.name == null && hotel.getName() == null) || this.name.equals(hotel.getName()))) return false;
	if(!(this.cost == hotel.getCost())) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 9;
	result = 3 * result + (name == null ? 0 : name.hashCode());
	result = 3 * result + Float.floatToIntBits(cost);
	return result;
    }
}
