package com.globerry.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import javax.persistence.CascadeType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "Company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "login", unique = true)
	private String login;
	@Column(name = "password")
	private String password;
	@Column(name = "description")
	private String description;
	// @NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL,
	fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinTable(name = "CompanyTour",
	joinColumns =
	@JoinColumn(name = "company_id"),
	inverseJoinColumns =
	@JoinColumn(name = "tour_id"))
	private Set<Tour> tourList = new HashSet<Tour>();
	private Integer access;
	@Transient
	private Integer hashCode = null;

	public Integer getAccess() {
		return access;
	}

	public void setAccess(Integer access) {
		this.access = access;
	}
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null) return false;
	if(!(obj instanceof Company)) return false;
	Company company = (Company) obj;
	
	if(this.name == null ^ company.getName() == null) return false;
	if(!((this.name == null && company.getName() == null) || this.name.equals(company.getName()))) return false;
	
	if(this.description == null ^ company.getDescription() == null) return false;
	if(!((this.description == null && company.getDescription() == null) || this.description.equals(company.getDescription()))) return false;
	
	if(this.email == null ^ company.getEmail() == null) return false;
	if(!((this.email == null && company.getEmail() == null) || this.email.equals(company.getEmail()))) return false;
	
	if(this.login == null ^ company.getLogin() == null) return false;
	if(!((this.login == null && company.getLogin() == null) || this.login.equals(company.getLogin()))) return false;
	
	if(this.password == null ^ company.getPassword() == null) return false;
	if(!((this.password == null && company.getPassword() == null) || this.password.equals(company.getPassword()))) return false;
	
	if(this.access == null ^ company.getAccess() == null) return false;
	if(!((this.access == null && company.getAccess() == null) || this.getAccess().equals(company.getAccess()))) return false;
	
/*	if(this.tourList == null ^ company.getTourList() == null) return false;
	if(!((this.tourList == null && company.getTourList() == null) || this.tourList.equals(company.getTourList()))) return false;*/
	return true;
    }
    @Override
    public int hashCode()
    {
	if(hashCode != null) return hashCode;
	int result = 6;
	result = 3*result + (name == null ? 0 : name.hashCode());
	result = 3*result + (description == null ? 0 : description.hashCode());
	result = 3*result + (email == null ? 0 : email.hashCode());
	result = 3*result + (login == null ? 0 : login.hashCode());
	result = 3*result + (password == null ? 0 : password.hashCode());
	result = 3*result + (access == null ? 0 :access.hashCode());
/*	for(Tour tour: tourList)
	{
	    result = result + tour.hashCode();
	}*/
	return result;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Tour> getTourList() {
		return tourList;
	}

	public void setTourList(Set<Tour> tourList) {
		this.tourList = tourList;
	}

	
	
}
