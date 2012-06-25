package com.globerry.project.service.interfaces;

import com.globerry.project.domain.City;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IAdminCityService  extends ICityService
{
    public void addCity(City city);
    
    public void removeCity(City city);
    
    public void updateCity(City city);
}
