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
	public void addCompanyTest() {
		try {
			companyDao.addCompany(company1);
		} catch (MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(companyDao.getCompanyList().size() == 1);
		assertTrue(companyDao.getCompanyList().contains(company1));
	}
	
	@Test
	public void removeCompanyByCompany() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		companyDao.removeCompany(company1);
		assertTrue(companyDao.getCompanyList().isEmpty());
	}
	
	@Test
	public void removeCompanyById() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		companyDao.removeCompany(company1.getId());
		assertTrue(companyDao.getCompanyList().isEmpty());
	}
	
	@Test
	public void getCompanyListTest() {
		try {
			companyDao.addCompany(company1);
			companyDao.addCompany(company2);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		List<Company> companyList = new ArrayList();
		companyList.add(company1);
		companyList.add(company2);
		assertTrue(companyDao.getCompanyList().size() == 2);
		assertTrue(companyDao.getCompanyList().equals(companyList));
	}
	
	@Test
	public void updateCompanyTest() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		company1.setName(getGeneratedString());
		companyDao.updateCompany(company1);
		assertTrue(companyDao.getCompanyList().contains(company1));
	}
	
	@Test
	public void getCompanyByLoginTest() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(companyDao.getCompanyByLogin(company1.getLogin()).equals(company1));
		assertTrue(companyDao.getCompanyByLogin("UnexistedLogin") == null);
	}
	
	@Test
	public void getCompanyByEmailTest() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(companyDao.getCompanyByEmail(company1.getEmail()).equals(company1));
		assertTrue(companyDao.getCompanyByEmail("UnexistedEmail") == null);
	}
	
	@Test
	public void getCompanyByIdTest() {
		try {
			companyDao.addCompany(company1);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(companyDao.getCompanyById(company1.getId()).equals(company1));
		assertTrue(companyDao.getCompanyById(999) == null);
	}

}
