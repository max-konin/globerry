package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;
/**
 * 
 * @author Сергей
 * Интерфейс событий
 */
public interface IEventDao
{
    /**
     * Получить список событий для города
     * @param city Город
     * @return Список событий
     */
    public Set<Event> getEventList(City city);
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
    public void addEvent(Event event, City city);
    /**
     * Изменить событие
     * @param oldEvent старое событие
     * @param newEvent новое событие
     */
    public void updateEvent(Event oldEvent, Event newEvent);
}
