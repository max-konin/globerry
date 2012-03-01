package com.globerry.project.service;

import java.util.Set;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IEventService
{
    public Set<Event> getEventLis();
    public Set<Event> getEventLis(City city);
    public Set<Event> getEventLis(Month month);
    public void addEvent(Event event);
    public void removeEvent(Event event);
}
