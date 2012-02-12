package com.globerry.project.domain;

import java.sql.Date;

public class Tour
{
    private int id;
    private int companyId;
    private String name;
    private float cost;
    private String description;
    private Date dateStart;
    private Date dateEnd;

    // <Гетеры сеттеры>
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

    public float getCost()
    {
	return cost;
    }

    public void setCost(float cost)
    {
	this.cost = cost;
    }

    public String getDescription()
    {
	return description;
    }

    public void setDescription(String description)
    {
	this.description = description;
    }

    public Date getDateStart()
    {
	return dateStart;
    }

    public void setDateStart(Date dateStart)
    {
	this.dateStart = dateStart;
    }

    public Date getDateEnd()
    {
	return dateEnd;
    }

    public void setDateEnd(Date dateEnd)
    {
	this.dateEnd = dateEnd;
    }
    

    public int getCompanyId()
    {
	return companyId;
    }

    public void setCompanyId(int companyId)
    {
	this.companyId = companyId;
    }
 // </Гетеры сеттеры>

}
