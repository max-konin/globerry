package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
import com.globerry.project.domain.Month;
/**
 * 
 * @author Сергей
 * Интерфейс событий
 */
public interface IEventDao
{
    /**
     * Удалить событие
     * @param event Событие
     */
    public void removeEvent(Event event);
    /**
     * Добавить событие для города
     * @param event Событие
     * @param city Город
     */
    public void removeEvent(int id);
    public void addEvent(Event event, City city);
    /**
     * Изменить событие
     * @param oldEvent старое событие
     * @param newEvent новое событие
     */
    public void updateEvent(Event oldEvent, Event newEvent);
    /**
     * Возвращает лист событий по месяцу
     * @param month месяц
     */
    public List<Event> getEventList(Month month, City city);
    public List<Event> getEventList();
    void addEvent(Event event);
}
