package com.globerry.project.domain;

public class Auto 
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
	if(!(obj instanceof Auto)) return false;
	Auto auto = (Auto) obj;
	if(!(auto.getCost() == this.getCost())) return false;
	
	if(this.name == null ^ auto.getName() == null) return false;
	if(!((this.name == null && auto.getName() == null) || auto.getName().equals(this.getName()))) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	int result = 5;
	result = 3*result + Float.floatToIntBits(cost);
	result = 3*result + (name == null ? 0 : name.hashCode());
	return result;
    }

}
