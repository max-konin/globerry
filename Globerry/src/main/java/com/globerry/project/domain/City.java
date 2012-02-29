package com.globerry.project.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.mapping.Collection;

@Entity
@Table(name = "City")
public class City implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    //private Proposals proposals;
    //private Option option;
    @ManyToMany(
	    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
	    targetEntity = Event.class,
	    	    mappedBy = "cityList"
	    )
    private Set<Event> eventList = new HashSet<Event>();
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
	    name = "CityTag",
	    joinColumns = { @JoinColumn(name = "city_id") },
	    inverseJoinColumns = { @JoinColumn(name = "tag_id") }
	    )
    private List<Tag> tagList = new ArrayList<Tag>();
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
    /*public Proposals getProposals()
    {
	return proposals;
    }
    public void setProposals(Proposals proposals)
    {
	this.proposals = proposals;
    }
    public Option getOption()
    {
	return option;
    }
    public void setOption(Option option)
    {
	this.option = option;
    }*/
    public List<Tag> getTagList()
    {
	return tagList;
    }
    public void setTagList(List<Tag> tagList)
    {
	this.tagList = tagList;
    }
    public Set<Event> getEvents()
    {
	return eventList;
    }
    public void setEvents(Set<Event> events)
    {
	this.eventList = events;
    }
}
