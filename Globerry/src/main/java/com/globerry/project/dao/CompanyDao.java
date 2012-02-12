package com.globerry.project.dao;


import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.Company;

@Repository
public class CompanyDao implements ICompanyDao
{
    @Autowired
    private SessionFactory sessionFactory;
    
    public void addCompany(Company company){
	sessionFactory.getCurrentSession().save(company);
    }
    
    public List<Company> getCompanyList(){
	return sessionFactory.getCurrentSession().createSQLQuery("from Companies").list();
    }
    
    public void removeContact(Company company){
	if (null != company) {
	    sessionFactory.getCurrentSession().delete(company);
	}
    }

    
    public void removeCompany(Company company)
    {
	// TODO Auto-generated method stub
	
    }

    
    public void updateCompany(Company oldCompany, Company newCompany)
    {
	// TODO Auto-generated method stub
	
    }

}
