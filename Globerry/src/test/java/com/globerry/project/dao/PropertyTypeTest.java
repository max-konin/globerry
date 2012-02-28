package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.ContextLoaderListener;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})

public class PropertyTypeTest
{
    @Autowired
    private IPropertyTypeDao PropertyTypeDao;
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
    @Test(timeout=10000)
    public void test() throws Exception
    {

	try
	{
	PropertyType test = new PropertyType();
	test.setName(getStringGenerator());
	PropertyTypeDao.addPropertyType(test);
	test = new PropertyType();
	test.setName(getStringGenerator());
	PropertyTypeDao.addPropertyType(test);
	//add + list test
	List<PropertyType> propertyTypeList = PropertyTypeDao.getPropertyTypeList();
	Iterator<PropertyType> it = propertyTypeList.iterator();
	while(it.hasNext())
	{
	    PropertyType test2 = it.next();
	    if(test.getId() == test2.getId())
		assertEquals(true,test2.equals(test));
	}
	//remove test
	PropertyTypeDao.removePropertyType(test);
	propertyTypeList = PropertyTypeDao.getPropertyTypeList();
	it = propertyTypeList.iterator();
	int count = 0;
	while(it.hasNext())
	{
	    PropertyType test2 = it.next();
	    if(test.equals(test2))
	    {
		++count;
	    }
	}
	assertEquals(count,0);
	//updateTest
	test = new PropertyType();
	test.setName(getStringGenerator());
	PropertyTypeDao.addPropertyType(test);
	test.setName(getStringGenerator());
	PropertyTypeDao.updatePropertyType(test);
	propertyTypeList = PropertyTypeDao.getPropertyTypeList();
	it = propertyTypeList.iterator();
	count = 0;
	while(it.hasNext())
	{
	    PropertyType test2 = it.next();
	    if(test.equals(test2))
	    {
		++count;
	    }
	}
	assertEquals(count,1);
	
	
    }
    catch(MySqlException e)
    {
	fail(e.getDescription());
    }
    
}
}