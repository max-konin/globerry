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

public class DependingMonthOptionsTest
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
	DependingMonthOptions dependingMonthOptions = new DependingMonthOptions();
	dependingMonthOptions.setMonth(month);
	dependingMonthOptions.setVal(val);
	PropertyType optionsType = new PropertyType();
	optionsType.setName(optionsTypeName);
	
	dependingMonthOptions.setOptionsType(optionsType);
	//add entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(dependingMonthOptions);
	    sessionFactory.getCurrentSession().save(optionsType);
	    tx.commit();
	    sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthOptions> listDependingMonthOptions =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthOptions.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("optionsType", optionsType))
		    .list();
	    tx.commit();
	    assertEquals((double)val, (double)listDependingMonthOptions.get(0).getVal(),0);
	    sessionFactory.close();
	}
	//update entity
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   dependingMonthOptions.setVal(valNew);
	   sessionFactory.getCurrentSession().update(dependingMonthOptions);
	   tx.commit();
	   sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthOptions> listDependingMonthOptions =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthOptions.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("optionsType", optionsType))
		    .list();
	    tx.commit();
	    assertEquals((double)valNew, (double)listDependingMonthOptions.get(0).getVal(),0);
	    sessionFactory.close();
	}
	//remove entity
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().delete(dependingMonthOptions);
	   tx.commit();
	   sessionFactory.close();
	}
	//check entity
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    List<DependingMonthOptions> listDependingMonthOptions =
		    sessionFactory.getCurrentSession().createCriteria(DependingMonthOptions.class)
		    .add(Restrictions.like("month", month))
		    .add(Restrictions.like("optionsType", optionsType))
		    .add(Restrictions.like("val", valNew))
		    .list();
	    tx.commit();
	    assertTrue(listDependingMonthOptions.isEmpty());
	    sessionFactory.close();
	}
    }

}
