package com.globerry.project.domain;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Tour")
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "cost")
	private float cost;
	@Column(name = "description")
	private String description;
	@Column(name = "dateStart")
	private Date dateStart;
	@Column(name = "dateEnd")
	private Date dateEnd;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},
	fetch = FetchType.EAGER, targetEntity = Company.class)
	@JoinTable(name = "CompanyTour",
	joinColumns =
	@JoinColumn(name = "tour_id"),
	inverseJoinColumns =
	@JoinColumn(name = "company_id"))
	private Company company;

	// <Гетеры сеттеры>
	public Company getCompany() {
		return company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	@Override
	public boolean equals(Object obj)
	{
	    if(obj == null) return false;
	    if(!(obj instanceof Tour)) return false;
	    Tour tour = (Tour) obj;
	    if(!((this.name == null && tour.getName() == null) || this.name.equals(tour.getName()))) return false; 
	    if(!(tour.getCost() == this.getCost())) return false;
	    if(!((this.description == null && tour.getDescription() == null) || this.description.equals(tour.getDescription()))) return false;
	    if(!((this.dateStart == null && tour.getDateStart() == null) || this.dateStart.equals(tour.getDateStart()))) return false;
	    if(!((this.dateEnd == null && tour.getDateEnd() == null) || this.dateEnd.equals(tour.getDateEnd()))) return false;
	    if(!((this.company == null && tour.getClass() == null) || this.company.equals(tour.getCompany()))) return false;
	    return true;
	}
	@Override
	public int hashCode()
	{
	    int result = 14;
	    result = 3*result + (name == null ? 0 : name.hashCode());
	    result = 3*result + Float.floatToIntBits(cost);
	    result = 3*result + (description == null ? 0 : description.hashCode());
	    result = 3*result + (dateStart == null ? 0 : dateStart.hashCode());
	    result = 3*result + (dateEnd == null ? 0 : dateEnd.hashCode());
	    result = 3*result + (company == null ? 0 : company.hashCode());
	    return result;
	}
}
