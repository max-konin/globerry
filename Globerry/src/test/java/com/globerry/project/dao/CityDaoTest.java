package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;

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
    @Autowired
    TagDao tagDao;
    /**
     * ��������� ��������� ��������
     * @return ������
     */
    private String getStringGenerator()
    {  
	
      final int LENGHT = 8;  
      StringBuffer sb = new StringBuffer();  
      for (int x = 0; x <LENGHT; x++)  
      {  
        sb.append((char)((int)(Math.random()*26)+97));  
      }  
      return sb.toString();  
    } 
    
    @Test
    public void test() throws MySqlException
    {
	List<Tag> tagList = new ArrayList<Tag>();
	Tag tag = new Tag();
	tagDao.addTag(tag);
	tagList.add(tag);
	
	City city = new City();
	city.setName("Berlin");
	city.getTagList().add(tag);
	city.setLongitude(90);
	city.setLatitude(30);
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
	
	Range range = new Range();
	range.setMinX(80);
	range.setMaxX(120);
	range.setMinY(10);
	range.setMaxY(90);
	
	Set<City> cities = cityDao.getCityList(
		//CityRequest.CityRequestGenerate(new Range(), new ArrayList<Property>())
		new CityRequest(
			range, 
			new ArrayList<PropertySegment>(),
			tagList,
			Month.APRIL
		));
	System.out.println(cities.iterator().next().getName());
	//fail("Not yet implemented");
    }
    @Test
    public void deleteTest()
    {
	City city = new City();
	city.setName(getStringGenerator());
	try
	{
	    cityDao.addCity(city);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	int id = city.getId();
	cityDao.removeCity(id);
    }

}
