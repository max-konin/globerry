package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.log4j.Logger;
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
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.utils.PropertySegment;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class, ContextLoaderListener.class
})
public class CityDaoTest {
	// TODO

	@Autowired
	CityDao cityDao;
	@Autowired
	EventDao eventDao;
	@Autowired
	TagDao tagDao;
	@Autowired
	PropertyTypeDao propertyTypeDao;
	
	private final Logger logger = Logger.getLogger("black");

	/**
	 * Create a random string contains 8 characters.
	 *
	 * @return Random String
	 */
	private String getStringGenerator() {
		final int LENGHT = 8;
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LENGHT; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		return sb.toString();
	}

	@Before
	public void beforeTest() {
		
	}
	
	@Test
	@Rollback(true)
	@DirtiesContext
	public void test() throws MySqlException {
		List<Tag> tagList = new ArrayList<Tag>();
		Tag tag = new Tag();
		tagDao.addTag(tag);
		tagList.add(tag);

		City city = new City();
		city.setName("Berlin_temp");
		city.getTagList().add(tag);
		city.setLongitude(90);
		city.setLatitude(30);
		try {
			cityDao.addCity(city);
		} catch (MySqlException e) {
			e.printStackTrace(System.err);
		}
		Event event = new Event();
		event.setName("new year");
		eventDao.addEvent(event, city);

		Range range = new Range(80, 120, 10, 90);

		List<City> cities = cityDao.getCityList(new CityRequest(range, new ArrayList<PropertySegment>(), tagList, Month.APRIL));
		System.out.println(cities.iterator().next().getName());
	}

	@Test
	@Rollback(true)
	@DirtiesContext
	public void deleteTest() {
		City city = new City();
		city.setName(getStringGenerator());
		try {
			cityDao.addCity(city);
		} catch (MySqlException e) {
			e.printStackTrace(System.err);
		}
		int id = city.getId();
		cityDao.removeCity(id);
	}

	@Test
	@Rollback(true)
	@DirtiesContext
	public void deletePlusList() {
		City city1 = new City();
		city1.setName("Bobryjsk1");
		City city2 = new City();
		city2.setName("Bobryjsk2");
		try {
			cityDao.addCity(city1);
			cityDao.addCity(city2);
		} catch (MySqlException e) {
			e.printStackTrace(System.err);
		}
		Event ev = new Event();
		ev.setName("Disnayland");
		ev.getCities().add(city1);
		ev.getCities().add(city2);
		eventDao.addEvent(ev);
		cityDao.removeCity(city1);
		cityDao.getCityList();
	}

	@Test
	@Rollback(true)
	@DirtiesContext
	public void testList() {
		List<City> cityList = cityDao.getCityList();
		for (int i = 0; i < cityList.size(); i++) {
			System.out.println(cityList.get(i));
		}
	}

	@Test
	@Rollback(true)
	@DirtiesContext
	public void testDamagedList() {
		City city1 = new City();
		city1.setName("Murmansk");
		City city2 = new City();
		city2.setName("Kaliningrad");
		City city3 = new City();
		city3.setName("Moscow");
		city3.setMessage("error");
		try {
			cityDao.addCity(city1);
			cityDao.addCity(city2);
			cityDao.addCity(city3);
		} catch (MySqlException e) {
			e.printStackTrace(System.err);
		}
		List<City> cityDamagedList = cityDao.getDamagedCities();
		for (int i = 0; i < cityDamagedList.size(); i++) {
			System.out.println(cityDamagedList.get(i).getName());
		}
	}

	@Test
	@Rollback(true)
	@DirtiesContext
	public void deletePlusList2() {
		City city1 = new City();
		city1.setName("Bobryjsk1");
		City city2 = new City();
		city2.setName("Bobryjsk2");
		try {
			cityDao.addCity(city1);
			cityDao.addCity(city2);
		} catch (MySqlException e) {
			e.printStackTrace(System.err);
		}
		Event ev = new Event();
		ev.setName("Disnayland");
		eventDao.addEvent(ev, city1);
		System.out.println(ev.getId());
		eventDao.addEvent(ev, city2);

		cityDao.removeCity(city1.getId());
		cityDao.getCityList();

	}
	@Test(timeout = 100000)
	public void getCityList2test()
	{
	    List<PropertySegment> propSegmentList = new ArrayList<PropertySegment>();
	    PropertyType prop;
	    try
	    {
		//PropertySegment segment = new PropertySegment(propertyTypeDao.getById(1));
		PropertySegment segment2 = new PropertySegment(propertyTypeDao.getById(2));
		//segment.setMaxValue(35);
		//segment.setMaxValue(-35);
		//segment.setPropertyType(prop);
		//propSegmentList.add(segment);
		propSegmentList.add(segment2);
	    }
	    catch(Exception e)
	    {}
	    CityRequest request = new CityRequest(null, propSegmentList, null, null);
	    List<City> cityResult2 = cityDao.getCityList2(request);
	    List<City> cityResult = cityDao.getCityList(request);
	    //logger.info(cityResult2.size() + "<------MyCityCount");
	    for(City elem: cityResult2)
	    {
		logger.info(elem.getName());
                logger.info(elem.hashCode());
	    }//*/	    
	    
	}
	
}
