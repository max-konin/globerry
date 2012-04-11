package com.globerry.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.CityRequest;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.Range;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.IUserCityService;

@Service("userCityService")
public class UserCityService implements IUserCityService
{
    @Autowired
    CityDao cityDao;
    
    @Autowired
    private PropertyTypeDao propertyTypeDao;
    
    private Range currentRange;
    private List<Property> currentProperties = new ArrayList<Property>();
    private List<Tag> currentWhoTags = new ArrayList<Tag>();
    private List<Tag> currentWhatTags = new ArrayList<Tag>();
    @Override
    public void clickOnPassiveCity()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void clickOnActiveCity()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void changeRange(Range newRange)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void sliderOnChangeHandler()
    {
	
	//TODO
    }

    @Override
    public Set<City> getCityList()
    {
	CityRequest request = CityRequest.CityRequestGenerate(currentRange, currentProperties);
	Set<City> resultRequest = cityDao.getCityList(request);
	return resultRequest;
    }

    @Override
    public void update(Observable o, Object arg)
    {
	if (arg.getClass() == EventUI.class){
	    EventUI eventUI = (EventUI) arg;
	    if (eventUI.getParent().getClass() == Slider.class){
		sliderOnChangeHandler();
	    }
	}
	
    }
    public int getPropertyDaoHash()
    {
	return this.propertyTypeDao.hashCode();
    }

}
