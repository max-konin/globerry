/**
 * 
 */
package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Tour;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
public class CompanyServiceTest
{
    @Autowired
    private CompanyService cmpService;

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
    
    @Test(timeout = 1000)
    public void ShowList()
    {
	try{
	Company cmp = new Company();
	cmp.setName(getStringGenerator());
	cmp.setEmail(getStringGenerator());
	Tour tour = new Tour();
	tour.setName(getStringGenerator());
	cmp.getTourList().add(tour);
	cmpService.addCompany(cmp);
	Set<Tour> lst = cmpService.getTourList(cmp);
	int count = 0;
	Iterator<Tour> it = lst.iterator();
	while(it.hasNext())
	{
	    count++;
	    it.next();
	}//*/
	if (count == 0) fail("Set is empty");
	}
	catch (MySqlException e)
	{
	    fail(e.getDescription());
	}
	
    }
    @Test
    public void TestMonth()
    {
	DependingMonthProperty  dmp = new DependingMonthProperty();
	dmp.setMonth(2);
	System.err.println(dmp.getMonth());
    
    }

}
