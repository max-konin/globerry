package com.globerry.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.mapping.Collection;
//import org.hibernate.mapping.List;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

@Entity
@Table(name = "Company")
public class Company
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "description")
    private String description;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
               name="CompanyTour",
               joinColumns = @JoinColumn( name="company_id"),
               inverseJoinColumns = @JoinColumn( name="tour_id")
       )
    private List<Tour> tourList = new ArrayList<Tour>();
   
    
    /*public Company(String name, String login, String password)
    {
	this.name     = name;
	this.login    = login;
	this.password = password;
    }*/
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
    public String getEmail()
    {
	return email;
    }
    public void setEmail(String email)
    {
	this.email = email;
    }
    public String getLogin()
    {
	return login;
    }
    public void setLogin(String login)
    {
	this.login = login;
    }
    public String getPassword()
    {
	return password;
    }
    public void setPassword(String password)
    {
	this.password = password;
    }
    public String getDescription()
    {
	return description;
    }
    public void setDescription(String description)
    {
	this.description = description;
    }
    public List<Tour> getTourList()
    {
	return tourList;
    }
    public void setTourList(List<Tour> tourList)
    {
	this.tourList = tourList;
    }

}
