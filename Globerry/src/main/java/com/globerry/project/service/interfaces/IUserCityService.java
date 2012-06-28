package com.globerry.project.service.interfaces;

import java.util.Observer;

import com.globerry.project.dao.Range;
import com.globerry.project.domain.City;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.List;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IUserCityService extends ICityService, Observer
{
    public void clickOnPassiveCity();
    
    public void clickOnActiveCity();
    
    public void changeRange(Range newRange);
    
    public void sliderOnChangeHandler();

    public int getPropertyDaoHash();
    
    public List<City> getCityList(IApplicationContext appContext);
}
