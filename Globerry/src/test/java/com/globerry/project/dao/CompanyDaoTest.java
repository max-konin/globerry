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

	/**
	 * Рандомный генератор стрингов
	 *
	 * @return стринг
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
	public void addCompanyTest() {
		Company company = new Company();
		company.setName(getGeneratedString());
		company.setDescription(getGeneratedString());
		company.setEmail(getGeneratedString());
		company.setLogin(getGeneratedString());
		company.setPassword(getGeneratedString());
		company.setTourList(new HashSet<Tour>());
		try {
			companyDao.addCompany(company);
		} catch (MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		assertTrue(companyDao.getCompanyList().size() == 1);
		assertTrue(companyDao.getCompanyList().contains(company));
	}
	
	@Test
	public void removeCompanyByCompany() {
		Company company = new Company();
		company.setName(getGeneratedString());
		try {
			companyDao.addCompany(company);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		companyDao.removeCompany(company);
		assertTrue(companyDao.getCompanyList().isEmpty());
	}
	
	@Test
	public void removeCompanyById() {
		Company company = new Company();
		company.setName(getGeneratedString());
		try {
			companyDao.addCompany(company);
		} catch(MySqlException mse) {
			mse.printStackTrace(System.err);
		}
		companyDao.removeCompany(company.getId());
		assertTrue(companyDao.getCompanyList().isEmpty());
	}
	
	@Test
	public void getCompanyListTest() {
		Company company1 = new Company();
		Company company2 = new Company();
		company1.setName(getGeneratedString());
		company2.setName(getGeneratedString());
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

}
