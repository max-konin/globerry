package com.globerry.project.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;

@Repository
public class CityDao implements ICityDao
{
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void addCity(City city) throws MySqlException
    {
	Transaction tx = null;
	try
	{
	    tx = sessionFactory.getCurrentSession().beginTransaction();
	    sessionFactory.getCurrentSession().save(city);
	    tx.commit();
	    sessionFactory.close();
	}
	catch (Exception e) {
	    if(tx !=null)
		tx.rollback();
	    MySqlException mySqlExc = new MySqlException();
	       mySqlExc.setMyClass(city);
	       mySqlExc.setDescription("unexpected error");
	       throw mySqlExc;
	}

    }

    @Override
    public void removeCity(City city)
    {
	 Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	 Iterator<Event> it = city.getEvents().iterator();
	 while(it.hasNext()){
	     Event event = it.next();
	     if(event.getCities().size() < 2){
		 sessionFactory.getCurrentSession().delete(event);
	     }
	 }
         sessionFactory.getCurrentSession().delete(city);
         tx.commit();
         sessionFactory.close();
    }
    @Override
    public void removeCity(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	City city = (City) sessionFactory.getCurrentSession().load(City.class,
		id);
	if (null != city)
	{
	    sessionFactory.getCurrentSession().delete(city);
	}
	tx.commit();
	sessionFactory.close();

    }

    @Override
    public Set<City> getCityList(CityRequest request)
    {
	Set<City> result;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(City.class);
	//отсечение по tag
	List<Integer> tagIdList = new ArrayList<Integer>();
	Iterator<Tag> it = request.getTags().iterator();
	while(it.hasNext()){
	    tagIdList.add(it.next().getId());
	}
	criteria.createCriteria("tagList")
		.add(Restrictions.in("id", tagIdList));
	//отсечение по property
	//отсечение по range TODO
	result = new HashSet<City>(criteria.list());
	tx.commit();
	// TODO пока без учета локации
	return result;
    }


    @Override
    public void updateCity(City city)
    {
	   Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   
/*	   City excistingCity = (City) sessionFactory.getCurrentSession().get(City.class, city.getId());
	   excistingCity.setDmpList(city.getDmpList());
	   excistingCity.setName(city.getName());
	   excistingCity.setPropertyList(city.getPropertyList());
	   excistingCity.setProposals(city.getProposals());
	   excistingCity.setRu_name(city.getRu_name());
	   excistingCity.setTagList(city.getTagList());
	   excistingCity.setEvents(city.getEvents());*/
	   sessionFactory.getCurrentSession().update(city);
	   tx.commit();
	   sessionFactory.close();
	
    }

    @Override
    public City getCityById(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	City city = (City)sessionFactory.getCurrentSession().get(City.class, id);
	tx.commit();
	sessionFactory.close();
	return city;
    }

}
