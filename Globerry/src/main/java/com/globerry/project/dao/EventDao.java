package com.globerry.project.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;

@Repository
public class EventDao implements IEventDao
{

    private SessionFactory sessionFactory;
    @Override
    public List<Event> getEventList(City city)
    {
	
	// TODO Auto-generated method stub 
	return sessionFactory.getCurrentSession().createQuery("from Event").list();
    }

    @Override
    public void removeEvent(Event event)
    {
	sessionFactory.getCurrentSession().delete(event);
    }

    @Override
    public void addEvent(Event event, City city)
    {
	
    }

    @Override
    public void updateEvent(Event oldEvent, Event newEvent)
    {
	// TODO Auto-generated method stub
	
    }

}
