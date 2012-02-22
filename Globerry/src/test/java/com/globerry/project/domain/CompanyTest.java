/*package com.globerry.project.domain;

import static org.junit.Assert.*;



import org.hibernate.SessionFactory;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:root-context.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
public class CompanyTest
{

    @Autowired
   	SessionFactory sessionFactory;
    @Test
    @Transactional
    public void test()
    {
	Company company = new Company();
	company.setName("name");
	company.setDescription("afdsdfasfd");
	company.setLogin("login");
	company.setEmail("email");
	company.setPassword("52345");
	//sessionFactory.getCurrentSession().beginTransaction();
	//sessionFactory.getCurrentSession().save(company);
	//sessionFactory.getCurrentSession().getTransaction().commit();
    }

}
*/