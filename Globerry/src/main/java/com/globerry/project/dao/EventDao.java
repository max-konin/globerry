package com.globerry.project.dao;

import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.domain.City;
import com.globerry.project.domain.DependingMonthProperty;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;

@Repository
public class EventDao implements IEventDao
{
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void removeEvent(Event event)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().delete(event);
	tx.commit();
	sessionFactory.close();
	
    }

    @Override
    public void addEvent(Event event, City city)
    {
	if(!event.getCities().contains(city)&&!city.getEvents().contains(event)){
	    event.getCities().add(city);
	    city.getEvents().add(event);
	    try{
        	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
        	    if (event.getId() == 0)
        		sessionFactory.getCurrentSession().save(event);
        	    else
        		sessionFactory.getCurrentSession().update(event);
        	    sessionFactory.getCurrentSession().update(city);
        	    //int id = ((Event)sessionFactory.getCurrentSession().merge(event)).getId();
        	    //event.setId(id);
        	    tx.commit();
        	    sessionFactory.close();
	    }catch (Exception e)
	    {
		event.getCities().remove(city);
		city.getEvents().remove(event);
		throw new RuntimeException(e);
	    }
	}
	else{
		throw new RuntimeException();	    
	}
    }
    
    @Override
    public void addEvent(Event event)
    {
	Iterator<City> IT = event.getCities().iterator();
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	while(IT.hasNext())
	{
	    City city = IT.next();
	    sessionFactory.getCurrentSession().saveOrUpdate(city);
	    
	}
	    try{    
        	    sessionFactory.getCurrentSession().save(event);
        	    tx.commit();
        	    sessionFactory.close();
	    }catch (Exception e)
	    {
		
	    }
    }

    @Override
    public void updateEvent(Event oldEvent, Event newEvent)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	newEvent.setId(oldEvent.getId());
	sessionFactory.getCurrentSession().update(newEvent);
	tx.commit();
	sessionFactory.close();
    }


    @Override
    public List<Event> getEventList(Month month, City city)
    {
	List<Event> result;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Event.class)
		.add(Restrictions.eq("month", month))
		.createCriteria("cityList")
			.add(Restrictions.eq("id", city.getId()));
	result = (List<Event>)criteria.list();
	tx.commit();
	return result;
    }
    @Override
    public List<Event> getEventList()
    {
	List<Event> result;
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	result = sessionFactory.getCurrentSession().createQuery("from Event")
	            .list();
	tx.commit();
	sessionFactory.close();
	return result;
    }

    @Override
    public void removeEvent(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	Event event = (Event) sessionFactory.getCurrentSession().load(Event.class,
		id);
	if (null != event)
	{
	    sessionFactory.getCurrentSession().delete(event);
	}
	tx.commit();
	sessionFactory.close();
	
    }

}
