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

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tour;
import java.util.ArrayList;
import java.util.HashSet;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventDaoTest {

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	IEventDao eventDao;
	@Autowired
	ICityDao cityDao;
	@Autowired
	ICompanyDao companyDao;
	private static Event event1;
	private static Event event2;
	private static City city1 = new City();
	private static City city2 = new City();

	@BeforeClass
	public static void BeforeClassTestInitialize() {
		event1 = new Event();
		event2 = new Event();
		event1.setName(getGeneratedString());
		event1.setRu_name(getGeneratedString());
		event1.setMonth(Month.MARCH);
		event1.setDescription(getGeneratedString());
		event1.setRu_description(getGeneratedString());
		event1.setCities(new HashSet());
		event1.setImage(getGeneratedString());
		event2.setName(getGeneratedString());
		event2.setRu_name(getGeneratedString());
		event2.setMonth(Month.JANUARY);
		event2.setDescription(getGeneratedString());
		event2.setRu_description(getGeneratedString());
		event2.setCities(new HashSet());
		event2.setImage(getGeneratedString());
		city1.setName(getGeneratedString());
		city1.setRu_name(getGeneratedString());
		city1.setArea(50);
		city1.setEvents(new HashSet<Event>());
		city1.setIsValid(Boolean.TRUE);
		city1.setLatitude(5);
		city1.setLongitude(30);
		city1.setMessage(null);
		city2.setName(getGeneratedString());
		city2.setRu_name(getGeneratedString());
		city2.setArea(90);
		city2.setEvents(new HashSet<Event>());
		city2.setIsValid(Boolean.TRUE);
		city2.setLatitude(50);
		city2.setLongitude(10);
		city2.setMessage(getGeneratedString());
		event1.getCities().add(city1);
		event2.getCities().add(city1);
		event2.getCities().add(city2);
	}

	/**
	 * Create a random string contains 8 characters.
	 *
	 * @return Random String
	 */
	private static String getGeneratedString() {
		final int LENGHT = 8;
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LENGHT; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		return sb.toString();
	}

	@Test
	public void addEventTest() {
		int originalEventSize = sessionFactory.getCurrentSession().createQuery("from Event").list().size();
		eventDao.addEvent(event1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Event").list().size() - 1 == originalEventSize);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Event").list().contains(event1));
		assertTrue(sessionFactory.getCurrentSession().createQuery("from City").list().containsAll(event1.getCities()));
	}

	@Test
	@Transactional
	public void removeEventByEventTest() throws MySqlException {
		eventDao.addEvent(event1);
		int originalEventSize = sessionFactory.getCurrentSession().createQuery("from Event").list().size();
		eventDao.removeEvent(event1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Event").list().size() + 1 == originalEventSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Event").list().contains(event1));
	}

	@Test
	@Transactional
	public void removeEventByIdTest() throws MySqlException {
		//eventDao.addEvent(event1);
		Transaction tn = sessionFactory.getCurrentSession().beginTransaction();
		Iterator<City> IT = event1.getCities().iterator();
		while (IT.hasNext()) {
			City city = IT.next();
			sessionFactory.getCurrentSession().saveOrUpdate(city);
		}
		try {
			sessionFactory.getCurrentSession().save(event1);			
		} catch (Exception e) {
			tn.rollback();
		}
		tn.commit();
		int originalEventSize = sessionFactory.getCurrentSession().createQuery("from Event").list().size();
		eventDao.removeEvent(event1.getId());
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Event").list().size() + 1 == originalEventSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Event").list().contains(event1));
	}

	@Test
	public void getEventListTest() throws MySqlException {
		eventDao.addEvent(event1);
		eventDao.addEvent(event2);
		List<Event> eventList = new ArrayList();
		eventList.add(event1);
		eventList.add(event2);
		assertTrue(eventDao.getEventList().equals(eventList));
	}

	@Test
	public void updateEventTest() {
		eventDao.addEvent(event1);
		event1.setName(getGeneratedString());
		eventDao.updateEvent(event1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Event where id = " + event1.getId()).list().get(0).equals(event1));
	}

	@Test
	public void getEventByIdTest() {
		eventDao.addEvent(event1);
		assertTrue(eventDao.getEventById(event1.getId()).equals(event1));
		assertTrue(eventDao.getEventById(666) == null);
	}
}
