package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import com.globerry.project.MySqlException;
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
    public void addCity(City city) throws MySqlException;
    /**
     * Удаляет город по id
     * @param id
     */
    public void removeCity(int id);
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
    public Set<City> getCityList(CityRequest request);
    /**
     * Обновляет парметры города
     * @param oldCity старый город
     * @param newCity город с новыми параметрами
     */
    void updateCity(City city);
    public City getCityById(int id);
    public List<City> getCityList();
    /**
     * Функция извлекающая все города где есть message с ошибкой
     * @return list<City> городов
     */
    public List<City> getDamagedCities();
}
