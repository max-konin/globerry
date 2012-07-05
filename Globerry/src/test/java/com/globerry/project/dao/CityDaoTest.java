package com.globerry.project.dao;

import static org.junit.Assert.*;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.*;
import com.globerry.project.utils.PropertySegment;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class, ContextLoaderListener.class
})
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CityDaoTest {
	// TODO

    @Autowired
    ICityDao cityDao;
    @Autowired
    IEventDao eventDao;
    @Autowired
    ITagDao tagDao;
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    @Autowired
    SessionFactory sessionFactory;

    private final Logger logger = Logger.getLogger("black");
	/**
	 * Create a random string contains 8 characters.
	 *
	 * @return Random String
	 */
	private String getGeneratedString() {
		final int LENGHT = 8;
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LENGHT; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		return sb.toString();
	}
	
	@Test
	public void addCityTest() {
		City city = new City();
		city.setName(getGeneratedString());
		city.setRu_name(getGeneratedString());
		try {
			cityDao.addCity(city);
		} catch (MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertFalse(cityDao.getCityList().isEmpty());
		assertTrue(cityDao.getCityById(city.getId()).getName().equals(city.getName()));
		assertTrue(cityDao.getCityList().contains(city));
	}
	
	@Test
	public void removeCityByCityTest() {
		City city = new City();
		city.setName(getGeneratedString());
		try {
			cityDao.addCity(city);
		} catch (MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		cityDao.removeCity(city);
		assertTrue(cityDao.getCityList().isEmpty());
	}
	
	@Test
	public void removeCityByIdTest() {
		City city = new City();
		city.setName(getGeneratedString());
		try {
			cityDao.addCity(city);
		} catch (MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		int id = city.getId();
		cityDao.removeCity(id);
		assertTrue(cityDao.getCityList().isEmpty());
	}
	
	@Test
	public void getCityListTest() {
		City city1 = new City();
		City city2 = new City();
		city1.setName(getGeneratedString());
		city2.setName(getGeneratedString());
		city1.setRu_name(getGeneratedString());
		city2.setRu_name(getGeneratedString());
		List<City> cityList = new ArrayList();
		cityList.add(city1);
		cityList.add(city2);
		try {
			cityDao.addCity(city1);
			cityDao.addCity(city2);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(cityDao.getCityList().size() == 2);
		List<City> cityDaoList = cityDao.getCityList();
		assertTrue(cityDaoList.equals(cityList));	
	}
	
	@Test
	public void getCityListByTagsOnlyTest() {
		City city = new City();
		city.setName(getGeneratedString());
		city.setLatitude(10);
		city.setLongitude(25);
		List<Tag> listOfTags = new ArrayList();
		Tag tag1 = new Tag();
		tag1.setTagsType(TagsType.WHO);
		tag1.setImg("null");
		tag1.setName(getGeneratedString());
		Tag tag2 = new Tag();
		tag2.setTagsType(TagsType.WHERE);
		tag2.setImg("null");
		tag2.setName(getGeneratedString());
		city.getTagList().add(tag1);
		listOfTags.add(tag1);
		try {
			tagDao.addTag(tag1);
			tagDao.addTag(tag2);
			cityDao.addCity(city);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(cityDao.getCityListByTagsOnly(listOfTags).size() == 1);
		assertTrue(cityDao.getCityListByTagsOnly(listOfTags).get(0).getName().equals(city.getName()));
		listOfTags.add(tag2);
		assertFalse(cityDao.getCityListByTagsOnly(listOfTags).size() >= 1);
		
	}
	
	@Test
	public void updateCityTest() {
		City city = new City();
		city.setName(getGeneratedString());
		try {
			cityDao.addCity(city);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		city.setName("ChangedName");
		cityDao.updateCity(city);
		assertTrue(cityDao.getCityById(city.getId()).getName().equals(city.getName()));
	}
	
	@Test
	public void getCityByIdTest() {
		City city = new City();
		city.setName(getGeneratedString());
		try {
			cityDao.addCity(city);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(cityDao.getCityById(city.getId()).getName().equals(city.getName()));
		assertTrue(cityDao.getCityById(32) == null);
	}

    @Test
    @Transactional(readOnly = false)
    public void LazyTest()
    {
	City city1 = new City();
	city1.setName("Bobryjsk1");
	try
	{
	    cityDao.addCity(city1);
	} catch (MySqlException e)
	{
	    e.printStackTrace(System.err);
	}
	Event ev = new Event();
	ev.setName("Disnayland");
	eventDao.addEvent(ev, city1);
	ev = new Event();
	ev.setName("Disnayland5");
	eventDao.addEvent(ev, city1);
	System.err.println("1");
	city1 = cityDao.getCityById(city1.getId());
	System.err.println("2");
	System.err.println(Hibernate.isInitialized(city1.getEvents()));
	Iterator<Event> it = city1.getEvents().iterator();
	System.err.println("3");
	ev = it.next();
	System.err.println(ev.getName());
	ev = new Event();
	ev.setName("Disnayland55");
	eventDao.addEvent(ev, city1);
    }

}
