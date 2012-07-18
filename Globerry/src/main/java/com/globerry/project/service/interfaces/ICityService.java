package com.globerry.project.service.interfaces;

import java.util.List;

import com.globerry.project.domain.City;
/**
 * 
 * @author Сергей Крупин 
 *
 */
public interface ICityService
{
    List<City> getCityList();

    void addCity(City city);
}
