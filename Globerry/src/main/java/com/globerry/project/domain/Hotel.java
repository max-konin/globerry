package com.globerry.project.domain;

import javax.persistence.Transient;

public class Hotel
{    
   
    private float cost;
    private int cityId;
    private String name;
    @Transient
    private Integer hashCode = null;
    private String description;
    
    public Hotel()
    {
        
    }
    
    public Hotel(String name, int cityId)
    {
        this.name = name;
        this.cityId = cityId;
    }
    public Hotel(String name, int cityId, float cost)
    {
        this(name, cityId);
        this.cost = cost;        
    }
    public Hotel(String name, int cityId, String description)
    {
        this(name, cityId);
        this.description = description;
    }
    
    public Hotel(String name, int cityId, float cost, String description)
    {
        this(name, cityId, cost);
        this.description = description;
    }
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
   
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Hotel)) return false;
	Hotel hotel = (Hotel) obj;
	
	if(this.name == null ^ hotel.getName() == null) return false;
	if(!((this.name == null && hotel.getName() == null) || this.name.equals(hotel.getName()))) return false;
        if(!((this.description == null && hotel.getDescription() == null) || 
              this.description.equals(hotel.getDescription()))) return false;
	if(!(this.cost == hotel.getCost())) return false;
        if(!(this.cityId == hotel.getCityId())) return false;
	return true;
    }
    @Override
    public int hashCode()
    {
	if(hashCode != null) return hashCode;
	int result = 9;
	result = 3 * result + (name == null ? 0 : name.hashCode());
        result = 3 * result + (description == null ? 0 : description.hashCode());
	result = 3 * result + Float.floatToIntBits(cost);
        result = 3 * result + cityId;
	return result;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the cityId
     */
    public int getCityId()
    {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }
}
