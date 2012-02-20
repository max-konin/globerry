package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.Query;

@Service("tourDao")
@Transactional
public class TourDao implements ITourDao
{

    protected static Logger logger = Logger.getLogger("service");
    
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;
    
    @Override
    public void addTour(Tour tour)
    {
	  logger.debug("Adding new credit card");
	   
	  // Retrieve session from Hibernate
	  Session session = sessionFactory.getCurrentSession();
	  
	  // Persists to db
	  session.save(tour);
	
    }

    @Override
    public Tour getTour()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Tour> getTourList(Company company)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Tour> getTourList(City city)
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
	// TODO Auto-generated method stub
	
    }

}
