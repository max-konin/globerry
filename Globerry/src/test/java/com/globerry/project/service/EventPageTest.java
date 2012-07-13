package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IEventDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.EventPage;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventPageTest
{
    @Mock
    private IEventDao mockEventDao;
    
    @InjectMocks
    private EventPage page = new EventPage();
    
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
	verify(mockEventDao).getEventList();
    }
    @Test
    public void testRemoveElem()
    {
	//when(mockCityDao.removeCity(1))
	page.removeElem(Mockito.anyInt());
	verify(mockEventDao).removeEvent(Mockito.anyInt());
    }
    @Test
    public void testGetElemById()
    {
	page.getElemById(map, Mockito.anyInt());
	verify(mockEventDao).getEventById(Mockito.anyInt());
    }
    @Test
    public void testUpdateCity()
    {
	page.updateElem(Mockito.anyObject());
	verify(mockEventDao).updateEvent((Event) Mockito.anyObject());
    }
    @Test
    public void testGetRelations()
    {
	City city = new City();
	Event event = new Event();
	event.getCities().add(city);
	when(mockEventDao.getEventById(Mockito.anyInt())).thenReturn(event);
	page.getRelation(map, Mockito.anyInt());
	verify(mockEventDao).getEventById(Mockito.anyInt());
	assertTrue(map.size() == 1);
	
    }

}
