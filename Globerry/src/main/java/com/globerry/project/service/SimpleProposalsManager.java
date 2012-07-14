/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Ticket;
import com.globerry.project.domain.Tour;
import com.globerry.project.service.interfaces.IProposalsManager;
import java.util.*;

/**
 * Простой мэнеджер. Хранит все в хэш таблицах.
 * @author max
 */
public class SimpleProposalsManager implements IProposalsManager
{
    // ключ - id города.
    protected Map<Integer, Set<Hotel>> hotels = new HashMap<Integer, Set<Hotel>>();
    protected Map<Integer, Set<Ticket>> tickets = new HashMap<Integer, Set<Ticket>>();
    protected Map<Integer, Set<Tour>> tours = new HashMap<Integer, Set<Tour>>();
    
    @Override
    public Set<Hotel> getHotelsByCity(City city)
    {
        return hotels.get(city.getId());
    }

    @Override
    public Set<Ticket> getTicketByCity(City city)
    {
        return tickets.get(city.getId());
    }

    @Override
    public Set<Tour> getTourByCity(City city)
    {
        return tours.get(city.getId());
    }

    @Override
    public boolean addHotel(Hotel hotel)
    {
        Set<Hotel> set = hotels.get(hotel.getCityId());
        if (set != null)
            return set.add(hotel);
        set = new HashSet<Hotel>();
        set.add(hotel);
        hotels.put(hotel.getCityId(), set);
        return true;
    }

    @Override
    public boolean addHotelRange(Collection<Hotel> hotels)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addTicket(Ticket ticket)
    {
        Set<Ticket> set = tickets.get(ticket.getTargetCityId());
        if (set != null)
            return set.add(ticket);
        set = new HashSet<Ticket>();
        set.add(ticket);
        tickets.put(ticket.getTargetCityId(), set);
        return true;
    }

    @Override
    public boolean addTicketRange(Collection<Ticket> tickets)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addTour(Tour tour)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addTourRange(Collection<Tour> tours)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
