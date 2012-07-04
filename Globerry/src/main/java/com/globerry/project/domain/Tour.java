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
	// </Гетеры сеттеры>
}
