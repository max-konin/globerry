/**
 * 
 */
package com.globerry.project.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.EventDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
import com.globerry.project.service.interfaces.IEventService;

/**
 * @author Artem

 */
@Service("eventService")
public class EventService implements IEventService
{
    @Autowired
    private EventDao eventDao;

    /* (non-Javadoc)
     * @see com.globerry.project.service.IEventService#getEventList()
     */
    @Override
    public List<Event> getEventList()
    {
	
	return eventDao.getEventList();
    }


    @Override
    public Set<Event> getEventList(Month month)
    {
	// TODO Auto-generated method stub
	return null;
    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IEventService#addEvent(com.globerry.project.domain.Event)
     */
    @Override
    public void addEvent(Event event,City city)
    {
	eventDao.addEvent(event, city);

    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IEventService#removeEvent(com.globerry.project.domain.Event)
     */
    @Override
    public void removeEvent(Event event)
    {
	eventDao.removeEvent(event);

    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IEventService#removeEvent(int)
     */
    @Override
    public void removeEvent(int id)
    {
	// TODO Auto-generated method stub

    }


    @Override
    public Set<Event> getEventList(City city)
    {
	// TODO Auto-generated method stub
	return null;
    }

}
