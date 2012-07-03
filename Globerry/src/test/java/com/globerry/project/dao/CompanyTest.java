package com.globerry.project.dao;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ContextLoaderListener;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.RunWith;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/WEB-INF/configuration/test/daoTestContext.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
//*/
public class CompanyTest
{
    final Logger logger = Logger.getRootLogger();
    @Autowired
    private CompanyDao companyDao;
    Company company = new Company();
    /**
     * Рандомный генератор стрингов
     * @return стринг
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
    
    @Test(timeout=1000)
    public void AddTest() throws Exception
    {
	company.setDescription("Its yandex");
	company.setEmail(getStringGenerator());
	company.setLogin(getStringGenerator());
	try
	{
	    companyDao.addCompany(company);
	}
	catch(MySqlException e)
	{
	    fail(e.getDescription());
	}
	
    }
    @Test(timeout=1000)
    public void removeCompany()
    {
	Company cmpn1 = new Company();
	try
	{
	    companyDao.addCompany(cmpn1);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	companyDao.removeCompany(cmpn1);
    }
    @Test(timeout=1000)
    public void updateCompany()
    {
	Company company = new Company();
	try
	{
	    companyDao.addCompany(company);
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Company cmpn1 = new Company();
	cmpn1.setDescription("shokoladnie batonchiky snikers");
	cmpn1.setName("Snikers");
	cmpn1.setEmail("Sinkers@snikers.ru");
	cmpn1.setLogin("Shaurma");
	cmpn1.setPassword("Kotiki");
	try
	{
	    companyDao.updateCompany(company, cmpn1);
	}
	catch(MySqlException e)
	{
	    logger.info(e.getDescription());
	    
	}
	catch (Exception e)
	{
	    
	}
	
    }
    @Test
    public void getCompanyByLogin()
    {
	Company newCompany = new Company();
	newCompany.setLogin(getStringGenerator());
	newCompany.setEmail(getStringGenerator());
	newCompany.setDescription(getStringGenerator());
	newCompany.setPassword(getStringGenerator());
	
	try
	{
	    companyDao.addCompany(newCompany);
	} catch (MySqlException e)
	{
	    e.printStackTrace();
	}
	
	String login = newCompany.getLogin();
	logger.info(login);//*/
	Company company = companyDao.getCompanyByLogin(login);
	if(company != null)
	{
	    logger.info(company.getDescription());
	}
    }
}
