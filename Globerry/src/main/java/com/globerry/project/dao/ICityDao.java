package com.globerry.project.dao;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.dao.CityRequest;
import com.globerry.project.domain.ICityRequest;
import com.globerry.project.domain.Tag;
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
    public List<City> getCityList(CityRequest request);
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
    
    public List<City> getCityListByTagsOnly(ICityRequest cityRequest);
}
