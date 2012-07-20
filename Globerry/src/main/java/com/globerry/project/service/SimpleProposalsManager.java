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
import javax.inject.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Простой мэнеджер. Хранит все в хэш таблицах.
 * @author max
 */
@Service
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
    public Set<Ticket> getTicketsByCity(City city)
    {
        return tickets.get(city.getId());
    }

    @Override
    public Set<Tour> getToursByCity(City city)
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
        boolean f = true;
        for(Hotel hotel: hotels)
            f |= addHotel(hotel);
        return f;
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
        boolean f = true;
        for(Ticket ticket: tickets)
            f |= addTicket(ticket);
        return f;
    }

    @Override
    public boolean addTour(Tour tour)
    {
        Set<Tour> set = tours.get(tour.getTargetCityId());
        if (set != null)
            return set.add(tour);
        set = new HashSet<Tour>();
        set.add(tour);
        tours.put(tour.getTargetCityId(), set);
        return true;
    }

    @Override
    public boolean addTourRange(Collection<Tour> tours)
    {
        boolean f = true;
        for(Tour tour: tours)
            f |= addTour(tour);
        return f;
    }

    @Override
    public Set<Hotel> getHotelsByCities(Collection<City> cities)
    {
        Set<Hotel> hotelSet = new HashSet<Hotel>();
        for(City city: cities)
        {
            Set<Hotel> hotels = getHotelsByCity(city);
            if (hotels != null) hotelSet.addAll(hotels);
        }
        return hotelSet;
    }

    @Override
    public Set<Ticket> getTicketsByCities(Collection<City> cities)
    {
        Set<Ticket> ticketSet = new HashSet<Ticket>();
        for(City city: cities)
        {
            Set<Ticket> tickets =  getTicketsByCity(city);
            if (tickets != null) ticketSet.addAll(tickets);
        }
        return ticketSet;
    }

    @Override
    public Set<Tour> getToursByCities(Collection<City> cities)
    {
        Set<Tour> tourSet = new HashSet<Tour>();
        for(City city: cities)
        {
            Set<Tour> tours = getToursByCity(city);
            if (tours != null) tourSet.addAll(tours);
        }
        return tourSet;
    }
    
}
