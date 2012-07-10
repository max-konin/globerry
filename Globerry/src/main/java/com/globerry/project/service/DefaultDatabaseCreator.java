package com.globerry.project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IDatabaseManager;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import org.apache.log4j.Logger;

@Service
public class DefaultDatabaseCreator
{
    @Autowired
    ITagDao tagDao;
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    @Autowired
    ICityDao cityDao;
    @Autowired
    private IDatabaseManager databaseManager;
    
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
	
        try
	{
            Iterator<Tag> tagIterator = tagList.iterator();
            while (tagIterator.hasNext()){
        	tagDao.addTag(tagIterator.next());
            }
	} catch (MySqlException e)
	{
	    logger.error("Tag add error");
	    //e.printStackTrace();
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
	
	createPropertyType("cost", 0, 30, false, true);
	createPropertyType("alcohol", 0, 30, false, true);
	createPropertyType("russian", 0, 1, false, true);
	createPropertyType("visa", 0, 1, false, true);
	createPropertyType("sex", 0, 3, false, true);
	createPropertyType("security", 0, 3, false, true);
	createPropertyType("livingCost", 0, 300, true, true);
	createPropertyType("mood", 0, 300, true, true);
	createPropertyType("temperature", -35, 35, true, true);	
	
    }
    
    public void initCities(){
	
	LinkedList<City> citiesList = new LinkedList<City>();
	List<Tag> tagList = tagDao.getTagList();
	List<PropertyType> propertyList = propertyTypeDao.getPropertyTypeList();
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
	
        try
	{
            Iterator<City> cityIterator = citiesList.iterator();
            while (cityIterator.hasNext()){
        	cityDao.addCity(cityIterator.next());
            }
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    public City generateCityWithPsevdoRandomProperties(String name, float longitude, float latitude)
    {
	City city = new City();
	city.setName(name);
	city.setLongitude(longitude);
	city.setLatitude(latitude);
	int hash = Math.abs(((Float)longitude).hashCode()*((Float)latitude).hashCode()*name.hashCode());

	Set<Tag> tagList = new HashSet<Tag>();
	if (hash % 2 == 0)
	    tagList.add(tagDao.getTagById(1));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getTagById(2));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getTagById(3));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getTagById(4));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getTagById(5));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getTagById(6));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getTagById(7));
	if (hash % 2 == 1)
	    tagList.add(tagDao.getTagById(8));
	if (hash % 2 == 0)
	    tagList.add(tagDao.getTagById(9));
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
	
    }
    public void clearDatabase()
    {
	databaseManager.cleanDatabase();
    }
    private void createPropertyType(String name, int minValue, int maxValue, Boolean dependingMonth, Boolean betterWhenLess)
    {
	if(propertyTypeDao.getPropertyTypeByName(name) != null) return;//строчка бажит если в базе 
	PropertyType propertyType = new PropertyType();
	propertyType.setDependingMonth(dependingMonth);
	propertyType.setBetterWhenLess(betterWhenLess);
	propertyType.setMinValue(minValue);
	propertyType.setMaxValue(maxValue);
	propertyType.setName(name);
	try
	{
	    propertyTypeDao.addPropertyType(propertyType);
	} catch (MySqlException e)
	{
	    e.printStackTrace();
	}
	
    }
}
