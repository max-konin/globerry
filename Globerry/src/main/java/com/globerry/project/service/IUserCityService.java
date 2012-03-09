package com.globerry.project.service;

import java.util.Observer;

import com.globerry.project.dao.Range;

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
}
