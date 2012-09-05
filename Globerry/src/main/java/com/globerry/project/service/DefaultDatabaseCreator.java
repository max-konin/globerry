package com.globerry.project.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;

import com.globerry.project.dao.IDao;
import com.globerry.project.dao.IDatabaseManager;
import com.globerry.project.dao.IDao;
import com.globerry.project.service.StringManager;

import com.globerry.project.domain.*;
import com.globerry.project.service.interfaces.IProposalsManager;

import com.globerry.project.domain.Tour;
import com.globerry.project.service.interfaces.IProposalsManager;

import org.apache.log4j.Logger;

@Service
public class DefaultDatabaseCreator
{
    @Autowired
    IDao<Tag> tagDao;
    @Autowired
    IDao<City> cityDao;
    @Autowired
    IDao<PropertyType> propertyTypeDao;
    @Autowired
    private IDatabaseManager databaseManager;
    @Autowired
    private IProposalsManager proposalsManager;
        
    protected static Logger logger = Logger.getLogger(DefaultDatabaseCreator.class);
    private boolean isTagInit = false;
    private boolean isPropertyTypeInit = false;
    private boolean isCitiesInit = false;
    
    
    public void initTags(){
	if (isTagInit)
	    return;
	isTagInit = true;
	LinkedList<Tag> tagList = new LinkedList<Tag>();

	tagList.addLast(new Tag());
	tagList.getLast().setName("alone");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHO);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("with friends");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHO);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("with family");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHO);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("double");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHO);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("sunbathe");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHERE);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("ski");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHERE);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("looking");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHERE);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("shopping");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHERE);
	
	tagList.addLast(new Tag());
	tagList.getLast().setName("cruise");
	tagList.getLast().setImg("");
	tagList.getLast().setTagsType(TagsType.WHERE);
	
       
            Iterator<Tag> tagIterator = tagList.iterator();
            while (tagIterator.hasNext()){
        	tagDao.add(tagIterator.next());
            }
	
    }
   
      public void initPropertyType(){

	/*id	DependingMonth	betterWhenLess	maximumValue	minimumValue	name
	1		0		1		20		0	cost
	2		0		1		20		0	alcohol
	3		0		1		20		0	russian
	4		0		1		20		0	visa
	5		0		1		20		0	sex
	6		0		1		20		0	security
	7		1		1		300		0	livingCost
	8		1		1		300		0	mood
	9		1		1		35		-35	temperature*/
	if (isPropertyTypeInit)
	      return;
	isPropertyTypeInit = true;
	createPropertyType(StringManager.foodCostPropertyTypeName, 0, 30, false, true);
	createPropertyType(StringManager.alcoholPropertyTypeName, 0, 30, false, true);
	createPropertyType(StringManager.russianPropertyTypeName, 0, 1, false, true);
	createPropertyType(StringManager.visaPropertyTypeName, 0, 1, false, true);
	createPropertyType(StringManager.sexPropertyTypeName, 0, 3, false, true);
	createPropertyType(StringManager.securityPropertyTypeName, 0, 3, false, true);
	createPropertyType(StringManager.livingCostPropertyTypeName, 0, 999, true, true);
	createPropertyType(StringManager.moodPropertyTypeName, 0, 3, true, true);
	createPropertyType(StringManager.temperaturePropertyTypeName, -35, 35, true, true);
	
    }
    
    /*
    public void initCities(){
	
	LinkedList<City> citiesList = new LinkedList<City>();
	List<Tag> tagList = tagDao.getAll(Tag.class);
	List<PropertyType> propertyList = propertyTypeDao.getAll(PropertyType.class);
	if(tagList.isEmpty()) initTags();
	if(propertyList.isEmpty()) initPropertyType();
	
	
	citiesList.add(generateCityWithPsevdoRandomProperties("London", (float)-0.12, (float)51.508));
	citiesList.add(generateCityWithPsevdoRandomProperties("Paris", (float)2.19, (float)48.56));
	citiesList.add(generateCityWithPsevdoRandomProperties("Praha", (float)14.40, (float)50.8));
	citiesList.add(generateCityWithPsevdoRandomProperties("Brussel", (float)4.35, (float)50.80));
	citiesList.add(generateCityWithPsevdoRandomProperties("Amsterdam", (float)4.90, (float)52.35));
	citiesList.add(generateCityWithPsevdoRandomProperties("Berlin", (float)13.40, (float)52.50));
	citiesList.add(generateCityWithPsevdoRandomProperties("Roma", (float)12.50, (float)41.90));
	citiesList.add(generateCityWithPsevdoRandomProperties("Torino", (float)7.70, (float)45.09));
	citiesList.add(generateCityWithPsevdoRandomProperties("Munchen", (float)11.55, (float)48.15));
	citiesList.add(generateCityWithPsevdoRandomProperties("Wien", (float)16.35, (float)48.20));
	citiesList.add(generateCityWithPsevdoRandomProperties("Warszawa", (float)21.05, (float)52.20));
	citiesList.add(generateCityWithPsevdoRandomProperties("Budapest", (float)19.08, (float)47.50));
	citiesList.add(generateCityWithPsevdoRandomProperties("Kopengagen", (float)12.58, (float)55.65));
	citiesList.add(generateCityWithPsevdoRandomProperties("Madrid", (float)-3.65, (float)40.35));
	citiesList.add(generateCityWithPsevdoRandomProperties("Barcelona", (float)2.15, (float)41.35));
	citiesList.add(generateCityWithPsevdoRandomProperties("Buharest", (float)26.08, (float)44.38));
	
       
            Iterator<City> cityIterator = citiesList.iterator();
            while (cityIterator.hasNext()){
        	cityDao.add(cityIterator.next());
            }
	
    }
    public City generateCityWithPsevdoRandomProperties(String name, float longitude, float latitude)
    {
	/*City city = new City();
	city.setName(name);
	city.setLongitude(longitude);
	city.setLatitude(latitude);
	int hash = Math.abs(((Float)longitude).hashCode()*((Float)latitude).hashCode()*name.hashCode());

	Set<Tag> tagList = new HashSet<Tag>();
	if (hash % 2 == 0)
	    tagList.add(tagDao.getById(Tag.class,1));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getById(Tag.class,2));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getById(Tag.class,3));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getById(Tag.class,4));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getById(Tag.class,5));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getById(Tag.class,6));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getById(Tag.class,7));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getById(Tag.class,8));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getById(Tag.class,9));
	city.setTagList(tagList);
	
	Set<Property> propertyList = new HashSet<Property>();
	Set<DependingMonthProperty> dmpList = new HashSet<DependingMonthProperty>();
	Property property;
	DependingMonthProperty dmProperty;
	
	for(int i = 1; i < 8; i++){
	    PropertyType propertyType = propertyTypeDao.getById(i);// БЫДЛЯТИНА 8 обращений к базе, нужно вытаскивать лист и с ним работать
	    if (propertyType != null){
		if (!propertyType.isDependingMonth()){
		    property = new Property();
		    property.setPropertyType(propertyType);
		    property.setValue(hash%(propertyType.getMaxValue()-propertyType.getMinValue()));
		    propertyList.add(property);
		}else{
		    for(int monthId = 0; monthId < 12; monthId++){
        		    dmProperty = new DependingMonthProperty();
        		    dmProperty.setPropertyType(propertyType);
        		    dmProperty.setMonth(monthId);
        		    dmProperty.setValue(hash%(propertyType.getMaxValue()-propertyType.getMinValue()));
        		    dmpList.add(dmProperty);
		    }
		}
	    }
	}
	city.setPropertyList(propertyList);
	city.setDmpList(dmpList);
	return city;
        throw new UnsupportedOperationException("Not supported yet.");	
    }*/
    public void initTours()
    {
	for(int i = 0; i < 20; i++)
	{
	    proposalsManager.addTour(generateTour());
	}
	//System.err.println(tour.getName() + "\n " + tour.getDescription() + "\n" + tour.getTargetCityId() + "\n" + tour.getDateEnd().toString());
    }
    public void initTickets()
    {
	for(int i = 0; i < 20; i++)
	{
	    proposalsManager.addTicket(generateTicket());
	}
    }
    public void initHotels()
    {
	String[] namesOfHotels = {"Hotel Villa Ducale", "La Fuitina", "The Levin", "The Montague on The Gardens",
				  "Schlosshotel Im Grunewald", "Pullman Schweizerhof", "Hotel Primero Primera", "Patrick Hotel",
				  "Ammos Hotel" , "Niriis Hotel", "Sun City Studios","Hotel Ostria Beach","Galaxy Hotel Iraklio",
				  "Ca's Curial", "Hai Au", "Hoang Ngoc Resort", "Bamboo Village","Nitya Resort", "Neelams the Grand",
				  "Don Hill Beach Resort"};
	for(int i = 0; i < 20; i++)
	{
	    proposalsManager.addHotel(generateHotel(namesOfHotels[i]));
	}
    }
    public void clearDatabase()
    {
	databaseManager.cleanDatabase();
    }
    public Tour generateTour()
    {
	String[] descriptionArr = {"cheap", "expensive", "near sea", "with monsters", "with oil", "hot", "cold", "with skies", "with guns"};
	Tour tour = new Tour();
	Random rand = new Random();
	try
	{
	    tour.setDescription(descriptionArr[rand.nextInt(descriptionArr.length)] + "," + descriptionArr[rand.nextInt(descriptionArr.length)]);
	    tour.setCost(rand.nextInt(10000)+1000);
	    int cityCount = cityDao.getAll(City.class).size();
	    tour.setTargetCityId(rand.nextInt(cityCount) + 1);
	    City city = cityDao.getById(City.class, tour.getTargetCityId());
	    tour.setName(city.getName());
	    tour.setDateStart(new Date(rand.nextLong())); //блять артем
	    tour.setDateEnd(new Date(rand.nextLong()));
	    return tour;
	}
	catch(IllegalArgumentException e)
	{
	    throw new IllegalStateException("В базе нет городов, не к чему привязать Tour", e);
	}
    }
    public Ticket generateTicket()
    {
	Random rand = new Random();
	int cityCount = cityDao.getAll(City.class).size();
	try
	{
	    Ticket ticket = new Ticket(rand.nextInt(cityCount) + 1);
	    ticket.setCost(rand.nextInt(3000)+300);
	    City city = cityDao.getById(City.class, ticket.getTargetCityId());
	    ticket.setName(city.getName());
	    return ticket;
	}
	catch(IllegalArgumentException e)
	{
	    throw new IllegalStateException("В базе нет городов, не к чему привязать Ticket", e);
	}
    }
    public Hotel generateHotel(String name)
    {
	String[] descriptionArr = {"one star", "two stars", "three stars", "four stars", "five stars", "with swimming pool", "near sea", "with Russians",
		"big houses" , "with eat", "all inclusive"};
	
	int cityCount = cityDao.getAll(City.class).size();
	Random rand = new Random();
	Hotel hotel = new Hotel();
	try
	{
	    hotel.setCityId(rand.nextInt(cityCount) + 1);
	    City city = cityDao.getById(City.class, hotel.getCityId());
	    hotel.setCityName(city.getName());
	    hotel.setName(name);
	    hotel.setCost(rand.nextInt(1000)+100);
	    hotel.setDescription(descriptionArr[rand.nextInt(descriptionArr.length)] + "," + descriptionArr[rand.nextInt(descriptionArr.length)]);
	    return hotel;
	}
	catch(IllegalArgumentException e)
	{
	    throw new IllegalStateException("В базе нет городов, не к чему привязать Hotel", e);
	}
    }
    private void createPropertyType(String name, int minValue, int maxValue, Boolean isDependingMonth, Boolean isBetterWhenLess)
    {
	Interval interval = new Interval(minValue, maxValue);
	PropertyType propertyType = new PropertyType(name, interval, isDependingMonth, isBetterWhenLess);
/*	PropertyType propertyType = new PropertyType();
	propertyType.setName(name);
	propertyType.setMinValue(minValue);
	propertyType.setMaxValue(maxValue);
	propertyType.setDependingMonth(isDependingMonth);
	propertyType.setBetterWhenLess(isBetterWhenLess);*/
	
	propertyTypeDao.add(propertyType);
    }
}
