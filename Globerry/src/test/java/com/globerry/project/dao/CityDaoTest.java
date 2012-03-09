package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})

public class CityDaoTest
{
    //TODO
    @Autowired
    CityDao cityDao;
    @Autowired
    EventDao eventDao;
    @Test
    public void test()
    {
	City city = new City();
	city.setName("Berlin");
	try
	{
	    cityDao.addCity(city);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Event event = new Event();
	event.setName("new year");
	eventDao.addEvent(event, city);
	
	Set<City> cities = cityDao.getCityList(null);
	System.out.println(cities.iterator().next().getName());
	//fail("Not yet implemented");
    }

}
