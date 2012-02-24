package com.globerry.project.dao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanyList()
	{
	    List<Company> companiesList;
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    companiesList = sessionFactory.getCurrentSession().createQuery("from Company")
	            .list();
	    tx.commit();
	    sessionFactory.close();
	    return companiesList;
	}

	@Override
	public void updateCompany(Company oldCompany, Company newCompany)
	{
	    oldCompany.setDescription(newCompany.getDescription());
	    oldCompany.setEmail(newCompany.getEmail());
	    oldCompany.setLogin(newCompany.getLogin());
	    oldCompany.setName(newCompany.getName());
	    oldCompany.setPassword(newCompany.getPassword());
	    oldCompany.setTourList(newCompany.getTourList());
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    Session session = sessionFactory.getCurrentSession();
    	    session.update(oldCompany);
	    tx.commit();
	    //ПОЧЕМУ У МЕНЯ ОНА РУГАЕТСЯ ЧТО СЕССИЯ ЗАКРЫТА КОГДА Я ЕЁ НЕ ЗАКРЫЛ?!?!?!?!
	}


	@Override
	public List<Tour> getCompanyTourList(Company company)
	{
	   /* Session session = sessionFactory.getCurrentSession();

	    Query query = session.createQuery("FROM Person as p WHERE p.id="+personId);
	     
	    Person person = (Person) query.uniqueResult();
	     
	    // Retrieve all
	    return  new ArrayList<CreditCard>(person.getCreditCards());*/
	    return null;
	}

}
