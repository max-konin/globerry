package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tour;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})

public class EventDaoTest
{
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    EventDao eventDao;
    @Autowired
    CityDao cityDao;
    @Autowired
    CompanyDao companyDao;
    final float imageEvent = 5;
    final Month monthEvent = Month.DECEMBER;
    final String nameEvent = "new year";
    final String descriptionEvent = "adskjlfnh";
    final String nameCity = "magadan";
    final float imageEvent2 = 3;
    final Month monthEvent2 = Month.SEPTEMBER;
    final String nameEvent2 = "1st september";
    @Test
    public void initCityTest()
    {
	Event event = new Event();
	event.setImage(imageEvent);
	event.setMonth(monthEvent);
	event.setName(nameEvent);
	event.setDescription(descriptionEvent);

	City city = new City();
	city.setName(nameCity);

	City city2 = new City();
	city2.setName(nameCity);

	Event event2 = new Event();
	event2.setImage(imageEvent2);
	event2.setMonth(monthEvent2);
	event2.setName(nameEvent2);
	event2.setCities(event.getCities());
    }
    @Test
    public void addEventTest(){
	Event event = new Event();
	event.setImage(imageEvent);
	event.setMonth(monthEvent);
	event.setName(nameEvent);
	event.setDescription(descriptionEvent);
	City city = new City();
	city.setName(nameCity);
	
	try{
	    eventDao.addEvent(event, city);
	    fail("Создание евента с пустым городом");
	}catch (Exception e) {}
	
	cityDao.addCity(city);
	cityDao.addCity(city);
	eventDao.addEvent(event, city);
    }
    @Test
    public void removeEventTest(){
	Event event = new Event();
	event.setImage(imageEvent);
	event.setMonth(monthEvent);
	event.setName(nameEvent);
	event.setDescription(descriptionEvent);
	
	City city = new City();
	city.setName(nameCity);

	cityDao.addCity(city);
	eventDao.addEvent(event, city);
	eventDao.removeEvent(event);
    }
    @Test
    public void getEventListTest(){
	Event event = new Event();
	event.setImage(imageEvent);
	event.setMonth(monthEvent);
	event.setName(nameEvent);
	event.setDescription(descriptionEvent);
	
	City city = new City();
	city.setName(nameCity);

	cityDao.addCity(city);
	eventDao.addEvent(event, city);
	Set<Event> listEvents = eventDao.getEventList(city);
	assertTrue(listEvents.contains(event));
	//Iterator<Event> it = listEvents.iterator();
	//while(it.next().getDescription().compareTo(descriptionEvent) != 0){}
    }
    @Test
    public void updateEventTest(){
	Event event = new Event();
	event.setImage(imageEvent);
	event.setMonth(monthEvent);
	event.setName(nameEvent);
	event.setDescription(descriptionEvent);

	City city = new City();
	city.setName(nameCity);
	
	cityDao.addCity(city);
	eventDao.addEvent(event, city);

	Event event2 = new Event();
	event2.setImage(imageEvent2);
	event2.setMonth(monthEvent2);
	event2.setName(nameEvent2);
	event2.setCities(event.getCities());
	
	eventDao.updateEvent(event, event2);
    }
}
