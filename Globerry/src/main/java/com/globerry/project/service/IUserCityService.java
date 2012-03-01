package com.globerry.project.service;

import com.globerry.project.dao.Range;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IUserCityService
{
    public void clickOnPassiveCity();
    public void clickOnActiveCity();
    public void changeRange(Range newRange);
    public void sliderOnChangeHandler();
}
