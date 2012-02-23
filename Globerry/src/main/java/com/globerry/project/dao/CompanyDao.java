package com.globerry.project.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.apache.log4j.Logger;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class CompanyDao implements ICompanyDao {


    	@Autowired
    	private SessionFactory sessionFactory;
	
	@Override
	public void addCompany(Company company)
	{
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().save(company);
	   tx.commit();
	   sessionFactory.close();
	}

	@Override
	public void removeCompany(Company company)
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    // Retrieve session from Hibernate
	    Session session = sessionFactory.getCurrentSession();
	    // Delete person
	    session.delete(company);
	    tx.commit();
	    sessionFactory.close();
	}
	@Override
	public void removeCompany(int id)
	{
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    Company company = (Company) sessionFactory.getCurrentSession().load(
	                Company.class, id);
	    if (null != company) {
	            
	            sessionFactory.getCurrentSession().delete(company);
	           
	    }
	    tx.commit();
	    sessionFactory.close();
	}

	@Override
	public List<Company> getCompanyList()
	{
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void updateCompany(Company oldCompany, Company newCompany)
	{
	    // TODO Auto-generated method stub
	   
	}


	@Override
	public List<Tour> getCompanyTourList()
	{
	    return sessionFactory.getCurrentSession().createQuery("from Company")
	            .list();
	}

}
