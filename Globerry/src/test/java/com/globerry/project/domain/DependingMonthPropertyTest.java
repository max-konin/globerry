package com.globerry.project.domain;


import java.util.List;
import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import com.globerry.project.dao.ContextLoaderListener;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.Company;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})

public class DependingMonthPropertyTest
{
    @Autowired
    SessionFactory sessionFactory;
    final Month month = Month.APRIL;
    final float value = (float) 123.321;
    final float valueNew = (float) 321.123;
    final String optionsTypeName = "population";
        
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
    @Test
    public void test()
    {
	DependingMonthProperty dependingMonthProperty = new DependingMonthProperty();
	dependingMonthProperty.setMonth(month);
	dependingMonthProperty.setValue(value);
	PropertyType propertyType = new PropertyType();
	propertyType.setName(getStringGenerator());
	propertyType.setMaxValue(123);
	propertyType.setMinValue(123);
	
	dependingMonthProperty.setPropertyType(propertyType);
	//add entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(dependingMonthProperty);
	    sessionFactory.getCurrentSession().save(propertyType);
	    tx.commit();
	    sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthProperty> listDependingMonthProperty =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthProperty.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("propertyType", propertyType))
		    .list();
	    tx.commit();
	    assertEquals((double)value, (double)listDependingMonthProperty.get(0).getValue(),0);
	    sessionFactory.close();
	}
	//update entity
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   dependingMonthProperty.setValue(valueNew);
	   sessionFactory.getCurrentSession().update(dependingMonthProperty);
	   tx.commit();
	   sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthProperty> listDependingMonthProperty =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthProperty.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("propertyType", propertyType))
		    .list();
	    tx.commit();
	    assertEquals((double)valueNew, (double)listDependingMonthProperty.get(0).getValue(),0);
	    sessionFactory.close();
	}
	//remove entity
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().delete(dependingMonthProperty);
	   tx.commit();
	   sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthProperty> listDependingMonthProperty =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthProperty.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("propertyType", propertyType))
		    .add(Restrictions.like("value", valueNew))
		    .list();
	    tx.commit();
	    assertTrue(listDependingMonthProperty.isEmpty());
	    sessionFactory.close();
	}
    }

}
