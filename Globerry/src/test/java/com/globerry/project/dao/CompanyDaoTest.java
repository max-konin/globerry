package com.globerry.project.dao;

import static org.junit.Assert.*;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.h2.engine.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/daoTestContext.xml"})
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CompanyDaoTest {

	private final Logger logger = Logger.getRootLogger();
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private SessionFactory sessionFactory; 

	private static Company company1;
	private static Company company2;
	
	@BeforeClass
	public static void BeforeClassTestInitialize() {
		company1 = new Company();
		company2 = new Company();
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
	 * Рандомный генератор стрингов
	 *
	 * @return стринг
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
	public void addCompanyTest() throws MySqlException {
		int originalCityListSize = sessionFactory.getCurrentSession().createQuery("from Company").list().size();
		companyDao.addCompany(company1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Company").list().size() - 1 == originalCityListSize);
		assertTrue(companyDao.getCompanyList().contains(company1));
	}
	
	@Test
	public void removeCompanyByCompany() throws MySqlException {
		companyDao.addCompany(company1);
		int originalCityListSize = sessionFactory.getCurrentSession().createQuery("from Company").list().size();
		companyDao.removeCompany(company1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Company").list().size() + 1 == originalCityListSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Company").list().contains(company1));
	}
	
	@Test
	public void removeCompanyById() throws MySqlException {
		companyDao.addCompany(company1);
		int originalCityListSize = sessionFactory.getCurrentSession().createQuery("from Company").list().size();
		companyDao.removeCompany(company1.getId());
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Company").list().size() + 1 == originalCityListSize);
		assertFalse(sessionFactory.getCurrentSession().createQuery("from Company").list().contains(company1));
	}
	
	@Test
	public void getCompanyListTest() throws MySqlException {
		companyDao.addCompany(company1);
		companyDao.addCompany(company2);
		List<Company> companyList = new ArrayList();
		companyList.add(company1);
		companyList.add(company2);
		assertTrue(companyDao.getCompanyList().equals(companyList));
	}
	
	@Test
	public void updateCompanyTest() throws MySqlException {
		companyDao.addCompany(company1);
		company1.setName(getGeneratedString());
		companyDao.updateCompany(company1);
		assertTrue(sessionFactory.getCurrentSession().createQuery("from Company where id = " + company1.getId()).list().get(0).equals(company1));
	}
	
	@Test
	public void getCompanyByLoginTest() throws MySqlException {
		companyDao.addCompany(company1);
		assertTrue(companyDao.getCompanyByLogin(company1.getLogin()).equals(company1));
		assertTrue(companyDao.getCompanyByLogin("UnexistedLogin") == null);
	}
	
	@Test
	public void getCompanyByEmailTest() throws MySqlException {
		companyDao.addCompany(company1);
		assertTrue(companyDao.getCompanyByEmail(company1.getEmail()).equals(company1));
		assertTrue(companyDao.getCompanyByEmail("UnexistedEmail") == null);
	}
	
	@Test
	public void getCompanyByIdTest() throws MySqlException {
		companyDao.addCompany(company1);
		assertTrue(companyDao.getCompanyById(company1.getId()).equals(company1));
		assertTrue(companyDao.getCompanyById(666) == null);
	}

}
