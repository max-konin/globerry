
package com.globerry.project.service.interfaces;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Hotel;
import com.globerry.project.domain.Ticket;
import com.globerry.project.domain.Tour;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Интерфейс менеджера предложений. Позволяет получать данные по отелям, билетам и турам в городе
 * @author max
 */
public interface IProposalsManager
{
    /**
     * Возвращает список отелей в городе
     * @param city город
     * @return Список отелей
     */
    Set<Hotel> getHotelsByCity(City city);
    
    /**
     * Возвращает список отелей в городах
     * @param cities город
     * @return Список отелей
     */
    Set<Hotel> getHotelsByCities(Collection<City> cities);
    
    /**
     * Возвращает авиабилетов отелей в городе
     * @param city город
     * @return Список авиабилетов
     */
    Set<Ticket> getTicketsByCity(City city);
    
    /**
     * Возвращает список билетов до городов
     * @param cities город
     * @return Список отелей
     */
    Set<Ticket> getTicketsByCities(Collection<City> cities);
    
    /**
     * Возвращает список туров в городе
     * @param city город
     * @return Список туров
     */
    Set<Tour> getToursByCity(City city);
    
    /**
     * Возвращает список туров в города
     * @param cities город
     * @return Список туров
     */
    Set<Tour> getToursByCities(Collection<City> cities);
    
    /**
     * Добавляет отель
     * @param hotel отель
     * @return true если такой отель уже есть
     */
    boolean addHotel(Hotel hotel);
    
     /**
     * Добавляет отели
     * @param hotels отели
     * @return true если такие отельи уже есть
     */
    boolean addHotelRange(Collection<Hotel> hotels);
    
    /**
     * Добавляет авиабилет
     * @param ticket авиабилет
     * @return true если такой авиабилет уже есть
     */ 
    boolean addTicket(Ticket ticket);
    
    /**
     * Добавляет билеты
     * @param tickets билеты
     * @return true если такие билеты уже есть
     */
    boolean addTicketRange(Collection<Ticket> tickets);
    
    /**
     * Добавляет тур
     * @param tour тур
     * @return true если такой тур уже есть
     */
    boolean addTour(Tour tour);
    
    /**
     * Добавляет туры
     * @param tours туры
     * @return true если такие туры уже есть
     */
    boolean addTourRange(Collection<Tour> tours);
    
    
    
    
}
