package com.globerry.project.domain;


import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import com.globerry.project.dao.ContextLoaderListener;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    final String optionsTypeName = "population";
    @Test(timeout = 1000)
    public void test()
    {
	DependingMonthOptions dependingMonthOptions = new DependingMonthOptions();
	dependingMonthOptions.setMonth(month);
	dependingMonthOptions.setVal(val);
	OptionsType optionsType = new OptionsType();
	optionsType.setName(optionsTypeName);
	
	dependingMonthOptions.setOptionsType(optionsType);
	
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().save(dependingMonthOptions);
	   tx.commit();
	   sessionFactory.close();
	//sessionFactory.getCurrentSession().
	//fail("Not yet implemented");
    }

}
