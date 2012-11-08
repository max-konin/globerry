package com.globerry.project.service.interfaces;

import java.util.Observer;


import com.globerry.project.domain.City;
import com.globerry.project.domain.CityShort;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.List;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IUserCityService extends ICityService 
{
    public void clickOnPassiveCity();
    
    public void clickOnActiveCity();
      
    public void sliderOnChangeHandler();
    
    public List<CityShort> getCityList(IApplicationContext appContext);
    
    public void onTagChangeHandler();

}
