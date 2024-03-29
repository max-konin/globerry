package com.globerry.project.service;

import com.globerry.project.dao.IDao;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Matchers;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.IEntityCreator;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class CityPageTest
{
    @Mock 
    private IDao<City> mockCityDao;
    
    @InjectMocks
    private CityPage page = new CityPage();
    
    private Map<String, Object> map = new HashMap<String, Object>();
    @Before
    public void init()
    {
	MockitoAnnotations.initMocks(this);
    }
    @Test
    public void setListTest()
    {
	page.setList(map);
	verify(mockCityDao).getAll(City.class);
    }
  
    @Test
    public void testGetElemById()
    {
	page.getElemById(Mockito.anyMap(), Mockito.anyInt());
	verify(mockCityDao).getById((Class)Mockito.any(), Mockito.anyInt());
    }
    @Test
    public void testUpdateCity()
    {
	page.updateElem(Mockito.anyObject());
	verify(mockCityDao).update((City) Mockito.anyObject());
    }
/*    @Test
    public void testGetRelations()
    {
	City city = new City();
	Event event = new Event();
	Tag tag = new Tag();
	Property property = new Property();
	DependingMonthProperty dmp = new DependingMonthProperty();
	city.getEvents().add(event);
	city.getTagList().add(tag);
	city.getPropertyList().add(property);
	city.getDmpList().add(dmp);
	when(mockCityDao.getCityById(Mockito.anyInt())).thenReturn(city);
	page.getRelation(map, Mockito.anyInt());
	verify(mockCityDao).getCityById(Mockito.anyInt());
	assertTrue(map.size() == 4);
	
    }*/


}