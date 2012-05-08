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
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;

@Service
public class DefaultDatabaseCreator
{
    @Autowired
    ITagDao tagDao;
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    @Autowired
    ICityDao cityDao;

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
	    e.printStackTrace();
	}
    }
    public void initPropertyType(){
	if (isPropertyTypeInit)
	    return;
	isPropertyTypeInit = true;
	LinkedList<PropertyType> propertyTypeList = new LinkedList<PropertyType>();
	//temperature
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(1);
	propertyTypeList.getLast().setDependingMonth(true);
	propertyTypeList.getLast().setBetterWhenLess(false);
	propertyTypeList.getLast().setMinValue(-35);
	propertyTypeList.getLast().setMaxValue(+35);
	propertyTypeList.getLast().setName("temperature");
	//travel time
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(2);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(true);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(300);
	propertyTypeList.getLast().setName("travel time");
	//cost of living
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(3);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(true);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(10);
	propertyTypeList.getLast().setName("cost of living");
	//food
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(4);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(true);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(100);
	propertyTypeList.getLast().setName("food");
	//alcohol
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(5);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(true);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(30);
	propertyTypeList.getLast().setName("alcohol");
	//mood
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(6);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(false);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(10);
	propertyTypeList.getLast().setName("mood");
	//security
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(7);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(false);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(10);
	propertyTypeList.getLast().setName("security");
	//sex
	propertyTypeList.add(new PropertyType());
	propertyTypeList.getLast().setId(8);
	propertyTypeList.getLast().setDependingMonth(false);
	propertyTypeList.getLast().setBetterWhenLess(false);
	propertyTypeList.getLast().setMinValue(0);
	propertyTypeList.getLast().setMaxValue(10);
	propertyTypeList.getLast().setName("sex");
	
        try
	{
            Iterator<PropertyType> propertyTypeIterator = propertyTypeList.iterator();
            while (propertyTypeIterator.hasNext()){
        	propertyTypeDao.addPropertyType(propertyTypeIterator.next());
            }
	} catch (MySqlException e)
	{
	    e.printStackTrace();
	}
    }
    
    public void initCities(){
	if (isCitiesInit)
	    return;
	isCitiesInit = true;
	
	LinkedList<City> citiesList = new LinkedList<City>();
	
	citiesList.add(new City());
	citiesList.getLast().setName("London");
	citiesList.getLast().setLatitude((float)51.508);
	citiesList.getLast().setLongitude((float)-0.12);
	Set<Tag> tagList = new HashSet<Tag>();
	tagList.add(tagDao.getTagById(1));
	tagList.add(tagDao.getTagById(2));
	tagList.add(tagDao.getTagById(6));
	citiesList.getLast().setTagList(tagList);
	/*
        City city = new City();
        city.setName("New York ");
        city.setId(1);
        city.setLatitude((float)51.508);
        city.setLongitude((float)-0.12);
        City city1 = new City();
        city1.setName("London ");
        city1.setId(2);
        city1.setLatitude((float)51.505);
        city1.setLongitude((float)-0.09);
        City city2 = new City();
        city2.setName("Novosibirsk ");
        city2.setId(3);
        city2.setLatitude((float)60.505);
        city2.setLongitude((float)-5.09);
        */    
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
}
