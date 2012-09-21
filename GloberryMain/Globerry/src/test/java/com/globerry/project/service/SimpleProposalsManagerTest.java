/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;


import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Ticket;
import com.globerry.project.domain.Tour;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anySet;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;

/**
 *
 * @author max
 */
public class SimpleProposalsManagerTest
{
    @Spy
    private Map<Integer, Set<Hotel>> hotels = new HashMap<Integer, Set<Hotel>>();
    
    @Spy
    private Map<Integer, Set<Ticket>> tickets = new HashMap<Integer, Set<Ticket>>();
    
    @Spy
    private Map<Integer, Set<Tour>> tours = new HashMap<Integer, Set<Tour>>();
    
    @InjectMocks
    SimpleProposalsManager manager = new SimpleProposalsManager();
    
    @Autowired
    private DefaultDatabaseCreator ddc;
    
    @Before
    public void setUp() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
    {
        MockitoAnnotations.initMocks(this);
        manager.getClass().getDeclaredField("hotels").set(manager, hotels);
        manager.getClass().getDeclaredField("tickets").set(manager, tickets);
        manager.getClass().getDeclaredField("tours").set(manager, tours);
       
    }
    
    /**
     * Test of addHotel method, of class SimpleProposalsManager.
     */
    @Test
    public void testAddHotel()
    {
        
        Hotel hotel = new Hotel("Золотая долина",1);
        Hotel hotel2 = new Hotel("Три версты",1);
        
        int startHotelCount;
        if (hotels.get(1) == null) startHotelCount = 0;
        else startHotelCount = hotels.get(1).size();
        
        assertTrue(manager.addHotel(hotel));
        verify(hotels).put(eq(1), anySet());
        
        assertFalse(manager.addHotel(hotel));
        
        assertTrue(manager.addHotel(hotel2));
        assertTrue((hotels.get(1).size() - startHotelCount) == 2);
        
    }
   

    /**
     * Test of addTicket method, of class SimpleProposalsManager.
     */
    @Test
    public void testAddTicket()
    {
        Ticket ticket = new Ticket(1,123);
        Ticket ticket2 = new Ticket(1,654);
        
        int startTicketCount;
        if (tickets.get(1) == null) startTicketCount = 0;
        else startTicketCount = tickets.get(1).size();
        
        assertTrue(manager.addTicket(ticket));
        verify(tickets).put(eq(1), anySet());
        
        assertFalse(manager.addTicket(ticket));
        
        assertTrue(manager.addTicket(ticket2));
        assertTrue((tickets.get(1).size() - startTicketCount) == 2);
    }  
    
    @Test
    public void testAddTour()
    {
        Tour tour1 = new Tour();
        Tour tour2 = new Tour();
        tour1.setTargetCityId(1);
        tour1.setCost(1);
        tour2.setTargetCityId(1);
        tour2.setCost(2);
        
        int startTourCount;
        if (tours.get(1) == null) startTourCount = 0;
        else startTourCount = tours.get(1).size();
        
        assertTrue(manager.addTour(tour1));
        verify(tours).put(eq(1), anySet());
        
        assertFalse(manager.addTour(tour1));
        
        assertTrue(manager.addTour(tour2));
        assertTrue((tours.get(1).size() - startTourCount) == 2);
    }  

    
    
    /**
     * Test of getHotelsByCity method, of class SimpleProposalsManager.
     */
    @Test
    public void testGetHotelsByCity()
    {
        City city = mock(City.class);
        when(city.getId()).thenReturn(1);
        
        manager.getHotelsByCity(city);
        verify(hotels).get(city.getId());
    }

    /**
     * Test of getTicketByCity method, of class SimpleProposalsManager.
     */
    @Test
    public void testGetTicketByCity()
    {
        City city = mock(City.class);
        when(city.getId()).thenReturn(1);
        
        //manager.getTicketByCity(city);
        verify(tickets).get(city.getId());
    }   
    
    @Test
    public void testGetTourByCity()
    {
        City city = mock(City.class);
        when(city.getId()).thenReturn(1);
        
        //manager.getTourByCity(city);
        verify(tours).get(city.getId());
    }   
    

    
}
