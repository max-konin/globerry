package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import org.hibernate.Transaction;


@Repository
public class TourDao implements ITourDao
{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void addTour(Tour tour)
    { 
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(tour);
	    tx.commit();
	    sessionFactory.close();
    }

    @Override
    public Tour getTour(int id)
    {	
	
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Tour tour = (Tour)sessionFactory.getCurrentSession().get(Tour.class, id);
	tx.commit();
	sessionFactory.close();
	return tour;
    }

    @Override
    public Set<Tour> getTourList(Company company)
    {

	return null;
    }

    @Override
    public Set<Tour> getTourList(City city)
    {
	 /* logger.debug("Retrieving all credit cards");
	   
	  // Retrieve session from Hibernate
	  Session session = sessionFactory.getCurrentSession();
	   
	  // Create a Hibernate query (HQL)
	  Query query = session.createQuery("FROM Tour");
	   
	  // Retrieve all//*/
	  return  null;//query.list();
    }

    @Override
    public void updateTour(Tour oldTour, Tour newTour)
    {
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    // Retrieve session from Hibernate
	    Session session = sessionFactory.getCurrentSession();
	   /* Company newCompany = (Company) session.get(Company.class, newCompany.getId());*/
	    oldTour.setCost(newTour.getCost());
	    oldTour.setDateEnd(newTour.getDateEnd());
	    oldTour.setDateStart(newTour.getDateStart());
	    oldTour.setName(newTour.getName());
	    oldTour.setDescription(newTour.getDescription());
	 
  	    session.update(oldTour);
	    tx.commit();
	   // session.close();
	
    }
    @Override
    public void updateTour(Tour tour)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Session session = sessionFactory.getCurrentSession();
	session.update(tour);
	tx.commit();
    }

    @Override
    public void removeTour(Tour tour)
    {
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();    
	    sessionFactory.getCurrentSession().delete(tour);
	    tx.commit();
	    sessionFactory.close();
	
    }

    @Override
    public void removeTour(int id)
    {
	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	    Tour tour = (Tour) sessionFactory.getCurrentSession().load(
	                Tour.class, id);
	    if (null != tour) {
	            
	            sessionFactory.getCurrentSession().delete(tour);
	           
	    }
	    tx.commit();
	    sessionFactory.close();
	
    }

}
