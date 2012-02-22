package com.globerry.project.dao;


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
import com.globerry.project.domain.Company;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
//*/
public class CompanyTest
{
    @Autowired
    private ICompanyDao companyDao;
    
    @Test(timeout=100)
    public void test() throws Exception
    {
	//ICompanyDao companyDao = new CompanyDao();
	Company test = new Company();
	test.setName("Google");
	test.setEmail("google@gmail.com");
	test.setLogin("Google");
	test.setPassword("asfdsadf");
	test.setDescription("Its google");
	companyDao.addCompany(test);//*/
	//System.out.print(test.getName());
	//rangeTest.setMaxX(1);
	//wait(2);
	//assertEquals(1, rangeTest.getMaxX());
	//CompanyDao dao = new CompanyDao();
	//dao.addCompany(test);
	//fail("Not yet implemented");
	//failNotEquals("qqqq", 1, 2);
    }

}
