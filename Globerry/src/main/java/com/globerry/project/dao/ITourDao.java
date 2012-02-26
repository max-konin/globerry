package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.Tour;
/**
 * 
 * @author Сергей Крупин
 *
 */
public interface ITourDao
{
    /**
     * Добавить тур
     * @param tour Тур
     */
    public void addTour(Tour tour);
    public Tour getTour(int id);//TODO
    /**
     * Список туров конкретной компании
     * @param company Компания
     * @return Список туров
     */
    public List<Tour> getTourList(Company company);
    /**
     * Список туров для конкретного города
     * @param city Город
     * @return Список туров
     */
    public List<Tour> getTourList(City city);
    /**
     * Обновляет тур
     * @param oldTour старый тур
     * @param newTour новый тур
     */
    public void updateTour(Tour oldTour, Tour newTour);
    /**
     * Удаляет tour
     * @param tour тур
     */
    public void removeTour(Tour tour);
    public void removeTour(int id);
}
