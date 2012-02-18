package com.globerry.project.dao;


import java.util.List;

import javax.annotation.Resource;





import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.Company;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class CompanyDao implements ICompanyDao {

	
    	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public void addCompany(Company company)
	{
	    	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
		sessionFactory.getCurrentSession().save(company);
		tx.commit();
		sessionFactory.getCurrentSession().close();
	}

	@Override
	public void removeCompany(Company company)
	{
	    // TODO Auto-generated method stub
	    
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

}
