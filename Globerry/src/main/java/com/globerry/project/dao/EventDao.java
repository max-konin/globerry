package com.globerry.project.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    public Set<Event> getEventList(City city)
    {
	return city.getEvents();
    }	

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
	if(!event.getCities().contains(city)&&!city.getEvents().contains(city)){
	    event.getCities().add(city);
	    city.getEvents().add(event);
	    try{
        	    Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
        	    
        	    sessionFactory.getCurrentSession().save(event);
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

}
