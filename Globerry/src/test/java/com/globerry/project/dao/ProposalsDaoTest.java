package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Proposals;
import com.globerry.project.domain.Tour;
import org.junit.BeforeClass;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProposalsDaoTest {

	@Autowired
	private IProposalsDao proposalsDao;
	@Autowired
	private ICityDao cityDao;
	@Autowired
	private ITourDao tourDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	@BeforeClass
	public static void BeforeClassTest() {
		
	}
	
	@Test
	public void initTest() {
		Proposals proposals = new Proposals();
		City city = new City();
		City city2 = new City();
		city2.setName("123");
		proposals.setCity(city2);

		try {
			cityDao.addCity(city);
			cityDao.addCity(city2);
		} catch (MySqlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proposalsDao.addProposals(proposals);

		proposals = null;
		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		proposals = (Proposals) sessionFactory.getCurrentSession().get(Proposals.class, city2.getId());
		tx.commit();
		sessionFactory.close();
	}

	@Test
	public void addProposalsTest() throws MySqlException {
		Proposals proposals = new Proposals();
		Tour tour = new Tour();
		tour.setName("slaved");
		City city = new City();
		proposals.setCity(city);
		

		try {
			proposalsDao.addProposals(proposals);
			fail("добавление предложения без города");
		} catch (Exception e) {
		}

		tourDao.addTour(tour);
		cityDao.addCity(city);
		proposalsDao.addProposals(proposals);

		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		proposals = (Proposals) sessionFactory.getCurrentSession().get(Proposals.class, proposals.getId());
		tx.commit();
		
	}

	@Test
	public void removeProposalsTest() throws MySqlException {
		Proposals proposals = new Proposals();
		City city = new City();
		proposals.setCity(city);
		cityDao.addCity(city);

		proposalsDao.addProposals(proposals);
		proposalsDao.removeProposals(proposals);

		Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		proposals = (Proposals) sessionFactory.getCurrentSession().get(Proposals.class, proposals.getId());
		tx.commit();

		assertNull(proposals);
	}

	@Test
	public void updateProposalsTest() throws MySqlException {
		Proposals proposals = new Proposals();
		City city = new City();
		proposals.setCity(city);
		cityDao.addCity(city);

		proposalsDao.addProposals(proposals);
		proposalsDao.removeProposals(proposals);

		Proposals proposals2 = new Proposals();

		proposalsDao.updateProposals(proposals, proposals2);
	}
}
