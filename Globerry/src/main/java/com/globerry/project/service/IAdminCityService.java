package com.globerry.project.service;

import com.globerry.project.domain.City;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IAdminCityService
{
    public void addCity(City city);
    public void removeCity(City city);
    public void updateCity(City city);
}
