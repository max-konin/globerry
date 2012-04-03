/**
 * 
 */
package com.globerry.project.service;

import java.util.List;
import java.util.Set;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;

/**
 * @author Artem
 *
 */
public interface IEventService
{
    public Set<Event> getEventList();
    public Set<Event> getEventList(Month month);
    public void addEvent(Event event,City city);
    public void removeEvent(Event event);
    public void removeEvent(int id);
}
