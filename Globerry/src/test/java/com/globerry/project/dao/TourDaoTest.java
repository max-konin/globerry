/**
 *
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

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
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Tour;
import java.sql.Date;
import java.util.HashSet;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourDaoTest {

	final Logger logger = Logger.getRootLogger();
	@Autowired
	private ITourDao tourDao;
	@Autowired
	private ICompanyDao companyDao;
	@Autowired
	private ICityDao cityDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Tour tour1 = new Tour();
	private static Tour tour2 = new Tour();
	private static City city1 = new City();
	private static City city2 = new City();
	private static Company company1 = new Company();
	private static Company company2 = new Company();

	@BeforeClass
	public static void BeforeTestClass() {
		tour1.setCost(250);
		tour1.setDateEnd(new Date(323425));
		tour1.setDateStart(new Date(43453254));
		tour1.setDescription(getGeneratedString());
		tour1.setName(getGeneratedString());
		tour2.setCost(5000);
		tour2.setDateEnd(new Date(3234225));
		tour2.setDateStart(new Date(43453434));
		tour2.setDescription(getGeneratedString());
		tour2.setName(getGeneratedString());
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
		city2.setArea(50);
		city2.setEvents(new HashSet<Event>());
		city2.setIsValid(Boolean.TRUE);
		city2.setLatitude(50);
		city2.setLongitude(10);
		city2.setMessage(getGeneratedString());
		company1.setName(getGeneratedString());
		company1.setDescription(getGeneratedString());
		company1.setEmail(getGeneratedString());
		company1.setLogin(getGeneratedString());
		company1.setPassword(getGeneratedString());
		company1.setTourList(new HashSet<Tour>());
		company2.setName(getGeneratedString());
		company2.setDescription(getGeneratedString());
		company2.setEmail(getGeneratedString());
		company2.setLogin(getGeneratedString());
		company2.setPassword(getGeneratedString());
		company2.setTourList(new HashSet<Tour>());
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

	@Test(timeout = 1000)
	public void addTourTest() throws MySqlException {
		int originalTourSize = sessionFactory.getCurrentSession().createQuery("from Tour").list().size();
		tourDao.addTour(tour1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tour").list().size() - 1 == 
				originalTourSize);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tour").list().contains(tour1));
	}

	@Test(timeout = 1000)
	@Transactional
	public void removeTourByTourTest() {
		tourDao.addTour(tour1);
		int originalTourSize = sessionFactory.getCurrentSession().createQuery("from Tour").list().size();
		tourDao.removeTour(tour1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tour").list().size() + 1 == 
				originalTourSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Tour").list().contains(tour1));
	}

	@Test(timeout = 1000)
	public void updateTourTest() {		
		tourDao.addTour(tour1);
		tour1.setDescription(getGeneratedString());
		tourDao.updateTour(tour1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tour where id = " + tour1.getId()).list().get(0).equals(tour1));
	}
	
	@Test(timeout = 1000)
	public void getTourByIdTest() {
		tourDao.addTour(tour1);
		assertTrue(tourDao.getTour(tour1.getId()).equals(tour1));
		assertTrue(tourDao.getTour(666) == null);
	}
	
	@Test(timeout = 1000)
	@Transactional
	public void removeTourByIdTest() {
		tourDao.addTour(tour1);
		int originalTourSize = sessionFactory.getCurrentSession().createQuery("from Tour").list().size();
		tourDao.removeTour(tour1.getId());
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Tour").list().size() + 1 == 
				originalTourSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Tour").list().contains(tour1));
	}
	
}
