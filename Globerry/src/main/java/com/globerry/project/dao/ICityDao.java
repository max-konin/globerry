package com.globerry.project.dao;

import java.util.List;

import com.globerry.project.domain.City;
/**
 * @author Сергей Крупин
 *
 */
public interface ICityDao
{
    /**
     * Добавляет новый город
     * @param city Город
     */
    public void addCity(City city);
    /**
     * Удаляет город
     * @param city город
     */
    public void removeCity(City city);
    /**
     * Подбирает города по запросу
     * @param request запрос
     * @return списко городов
     */
    public List<City> getCityList(CityRequest request);
    /**
     * Обновляет парметры города
     * @param oldCity старый город
     * @param newCity город с новыми параметрами
     */
    public void updateCity(City oldCity, City newCity);
}