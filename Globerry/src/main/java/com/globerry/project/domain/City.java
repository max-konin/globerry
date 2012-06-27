package com.globerry.project.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.mapping.Collection;

import com.globerry.project.utils.PropertySegment;
import java.util.*;

@Entity
@Table(name = "City")
public class City implements Serializable, IRelationsQualifier
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "ru_name")
    private String ru_name;
    @Column
    private float area;
    @Column
    private int population;
    @Column
    private float longitude;
    @Column
    private float latitude;
    @Column 
    private Boolean isValid;
    @Column
    private String message;
    
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(
	    fetch = FetchType.EAGER,
	    cascade = CascadeType.ALL
	    )
    @PrimaryKeyJoinColumn
    private Proposals proposals;
    //-------------------------------------------------------
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(
	    	fetch = FetchType.EAGER,
	    	cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
	    	targetEntity = Event.class,
	    	mappedBy = "cityList"
	    )
   /* @JoinTable(
	           name="CityEvent",
	           joinColumns = @JoinColumn( name="city_id"),
	           inverseJoinColumns = @JoinColumn( name="event_id")
		    )*/
    
    private Set<Event> eventList = new HashSet<Event>();
    //-------------------------------------------------------
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(
	    fetch = FetchType.EAGER,
	    cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
	    targetEntity = Tag.class
	    )
    private Set<Tag> tagList = new HashSet<Tag>();
    //-------------------------------------------------------
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
	    	cascade=CascadeType.ALL,
	    	fetch=FetchType.EAGER
	    	)
    @JoinTable(
           name="CityDependingMonthProperty",
           joinColumns = @JoinColumn( name="city_id"),
           inverseJoinColumns = @JoinColumn( name="dmp_id")
	    )
    private Set<DependingMonthProperty> dmpList = new HashSet<DependingMonthProperty>();
    //-------------------------------------------------------
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
	    	cascade=CascadeType.ALL,
	    	fetch=FetchType.EAGER
	    	)
    @JoinTable(
       name="CityProperty",
       joinColumns = @JoinColumn( name="city_id"),
       inverseJoinColumns = @JoinColumn( name="property_id")
	    )
    private Set<Property> propertyList = new HashSet<Property>();
    @Transient
    private float weight;
    public int getId()
    {
	return id;
    }
    public void setId(int id)
    {
	this.id = id;
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
    @JsonIgnore
    public Set<Tag> getTagList()
    {
	return tagList;
    }
    @JsonIgnore
    public void setTagList(Set<Tag> tagList)
    {
	this.tagList = tagList;
    }
    @JsonIgnore
    public Set<Event> getEvents()
    {
	return eventList;
    }
    @JsonIgnore
    public void setEvents(Set<Event> events)
    {
	this.eventList = events;
    }
    @JsonIgnore
    public Proposals getProposals()
    {
	return proposals;
    }
    @JsonIgnore
    public void setProposals(Proposals proposals)
    {
	this.proposals = proposals;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public String getRu_name()
    {
	return ru_name;
    }
    public void setRu_name(String ru_name)
    {
	this.ru_name = ru_name;
    }
    @JsonIgnore
    public Set<DependingMonthProperty> getDmpList()
    {
	return dmpList;
    }
    @JsonIgnore
    public void setDmpList(Set<DependingMonthProperty> dmpList)
    {
	this.dmpList = dmpList;
    }
    @JsonIgnore
    public Set<Property> getPropertyList()
    {
	return propertyList;
    }
    @JsonIgnore
    public void setPropertyList(Set<Property> propertyList)
    {
	this.propertyList = propertyList;
    }
    public float getLongitude()
    {
	return longitude;
    }
    public void setLongitude(float longitude)
    {
	this.longitude = longitude;
    }
    public float getLatitude()
    {
	return latitude;
    }
    public void setLatitude(float latitude)
    {
	this.latitude = latitude;
    }
    public int getPopulation()
    {
	return population;
    }
    public void setPopulation(int population)
    {
	this.population = population;
    }
    public float getArea()
    {
	return area;
    }
    public void setArea(float area)
    {
	this.area = area;
    }
    public Boolean getIsValid()
    {
	return isValid;
    }
    public void setIsValid(Boolean isValid)
    {
	this.isValid = isValid;
    }
    public String getMessage()
    {
	return message;
    }
    public void setMessage(String message)
    {
	this.message = message;
    }
    public boolean equals(City city)
    {
	if(this.getId() == city.getId() &&
		this.getName().equals(city.getName()) &&
		this.getRu_name().equals(city.getRu_name()) &&
		this.getArea() == city.getArea() &&
		this.getPopulation() == city.getPopulation() &&
		this.getLatitude() == city.getLatitude() &&
		this.getLongitude() == city.getLongitude())
	    return true;
	else return false;
    }
    public float getWeight()
    {
	return weight;
    }
    public void setWeight(float weight)
    {
	this.weight = weight;
    }
    /*
     * @throws IllegalArgumentException если не найдено сваоство заданного типа.
     */
    public float getValueByPropertyType(PropertyType type)  
    {
	
	if (type.isDependingMonth()){
	    Iterator<DependingMonthProperty> it = dmpList.iterator();
	    while (it.hasNext()){
		DependingMonthProperty property = it.next();
		if (property.getPropertyType().getId() == type.getId()){
		    return property.getValue();
		}
	    }
	}else{
	    Iterator<Property> it = propertyList.iterator();
	    while (it.hasNext()){
		Property property = it.next();
		if (property.getPropertyType().getId() == type.getId()){
		     return property.getValue();
		}
	    }
	}
        throw new IllegalArgumentException("City with id = " + 
                                            this.id + 
                                            "hasn't property of property type" + 
                                            type.getName());
	
    }
    public String toString(){
        String str = new String();
        str = String.format("City:  %s-----------------------------------------------------------------------------\n"+
                            "       id:         %d\n" +
                            "       area:       %f\n" +
                            "       longitude:  %f\n" + 
                            "       message:    %s\n" +
                            "       population: %d\n" +
                            "       latitude:   %f\n" +
                            "       isValid:    %s\n", 
                            name, id, area, longitude, message, population,latitude, isValid.toString());
        
        str += "       Tags:\n";
        for(Tag tag: tagList)
            str += "            " + tag.toString();
        
        str += "       Property:\n";      
        
        for(Property prop: propertyList)
        {
            str += "            " + prop.toString();
        } 
        HashMap<String, HashMap<Month, DependingMonthProperty>> propertyTable = 
                                                       new  HashMap<String, HashMap<Month, DependingMonthProperty>>(); 
        for(DependingMonthProperty prop: dmpList)
        {
            if (!propertyTable.containsKey(prop.getPropertyType().getName()))
                propertyTable.put(prop.getPropertyType().getName(), new HashMap<Month, DependingMonthProperty>());
            propertyTable.get(prop.getPropertyType().getName()).put(prop.getMonth(), prop);           
        }
        
        str += "       Depending month property:\n";   
            str += "            |propName            |JANUARY  |FEBRUARY |MARCH    |APRIL    |MAY      |JUNE     |JULY     "
                                                                              + "|AUGUST   |SEPTEMBER|"
                                                                               + "OCTOBER  |NOVEMBER |DECEMBER \n";
        for(String propName: propertyTable.keySet())
        {
            str +=   String.format("            |%20s|%9.1f|%9.1f|%9f.1|%9.1f|%9.1f|%9.1f"
                                                                            + "|%9.1f|%9.1f|%9.1f|%9.1f|%9.1f|%9.1f|\n", 
                                           propName,
                                           propertyTable.get(propName).get(Month.JANUARY).getValue(),
                                           propertyTable.get(propName).get(Month.FEBRUARY).getValue(),
                                           propertyTable.get(propName).get(Month.MARCH).getValue(),
                                           propertyTable.get(propName).get(Month.APRIL).getValue(),
                                           propertyTable.get(propName).get(Month.MAY).getValue(),
                                           propertyTable.get(propName).get(Month.JUNE).getValue(),
                                           propertyTable.get(propName).get(Month.JULY).getValue(),
                                           propertyTable.get(propName).get(Month.AUGUST).getValue(),
                                           propertyTable.get(propName).get(Month.SEPTEMBER).getValue(),
                                           propertyTable.get(propName).get(Month.OCTOBER).getValue(),
                                           propertyTable.get(propName).get(Month.NOVEMBER).getValue(),
                                           propertyTable.get(propName).get(Month.DECEMBER).getValue()
                                );
          
        }
        return str + "END CITY-----------------------------------------------------------------------------\n";                
              
    }

}
