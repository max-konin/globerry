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
    final float val = (float) 123.321;
    final float valNew = (float) 321.123;
    final String optionsTypeName = "population";
    @Test(timeout = 1000)
    public void test()
    {
	DependingMonthProperty dependingMonthProperty = new DependingMonthProperty();
	dependingMonthProperty.setMonth(month);
	dependingMonthProperty.setVal(val);
	PropertyType propertyType = new PropertyType();
	propertyType.setName(optionsTypeName);
	
	dependingMonthProperty.setPropertysType(propertyType);
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
	    assertEquals((double)val, (double)listDependingMonthProperty.get(0).getVal(),0);
	    sessionFactory.close();
	}
	//update entity
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   dependingMonthProperty.setVal(valNew);
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
	    assertEquals((double)valNew, (double)listDependingMonthProperty.get(0).getVal(),0);
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
		    .add(Restrictions.like("val", valNew))
		    .list();
	    tx.commit();
	    assertTrue(listDependingMonthProperty.isEmpty());
	    sessionFactory.close();
	}
    }

}
