/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import java.util.Date;

/**
 *
 * @author max
 */
public class Ticket
{
    private int targetCityId;
    private String name; 
    private Date depDate;
    private Date arrDate;
    private float cost;
    
    public Ticket(int targetCityId)
    {
        this.targetCityId = targetCityId;
    }
    public Ticket(int targetCityId, float cost)
    {
        this(targetCityId);
        this.cost = cost;
    }
    public Ticket(int targetCityId, Date depDate, Date arrDate)
    {
        this(targetCityId);
        this.depDate = depDate;
        this.arrDate = arrDate;
    }
    
    public Ticket(int targetCityId, float cost, Date depDate, Date arrDate)
    {
        this(targetCityId, cost);
        this.depDate = depDate;
        this.arrDate = arrDate;
    }

    /**
     * @return the targetCityId
     */
    public int getTargetCityId()
    {
        return targetCityId;
    }

    /**
     * @param targetCityId the targetCityId to set
     */
    public void setTargetCityId(int targetCityId)
    {
        this.targetCityId = targetCityId;
    }

    /**
     * @return the depDate
     */
    public Date getDepDate()
    {
        return depDate;
    }

    /**
     * @param depDate the depDate to set
     */
    public void setDepDate(Date depDate)
    {
        this.depDate = depDate;
    }

    /**
     * @return the arrDate
     */
    public Date getArrDate()
    {
        return arrDate;
    }

    /**
     * @param arrDate the arrDate to set
     */
    public void setArrDate(Date arrDate)
    {
        this.arrDate = arrDate;
    }

    /**
     * @return the cost
     */
    public float getCost()
    {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(float cost)
    {
        this.cost = cost;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    
}
