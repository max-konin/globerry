package com.globerry.project.dao;


import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import com.globerry.project.dao.ContextLoaderListener;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
//*/
public class CompanyTest
{
    @Autowired
    private CompanyDao companyDao;
    Company company = new Company();
    @Test(timeout=1000)
    public void AddTest() throws Exception
    {
	company.setDescription("Its yandex");
	company.setEmail("yandex@yandex.ru");
	company.setLogin("hello");
	companyDao.addCompany(company);
	
    }
    @Test(timeout=1000)
    public void removeCompany()
    {
	Company cmpn1 = new Company();
	companyDao.addCompany(cmpn1);
	companyDao.removeCompany(cmpn1);
    }
    @Test(timeout=1000)
    public void updateCompany()
    {
	Company company = new Company();
	companyDao.addCompany(company);
	Company cmpn1 = new Company();
	cmpn1.setDescription("shokoladnie batonchiky snikers");
	cmpn1.setName("Snikers");
	cmpn1.setEmail("Sinkers@snikers.ru");
	cmpn1.setLogin("Shaurma");
	cmpn1.setPassword("Kotiki");
	companyDao.updateCompany(company, cmpn1);
	
    }
}
