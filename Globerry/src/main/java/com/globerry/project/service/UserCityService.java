package com.globerry.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.CityRequest;
import com.globerry.project.dao.PropertySegment;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.Range;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.IUserCityService;

@Service("userCityService")
@Scope("session")
public class UserCityService implements IUserCityService
{
    @Autowired
    private CityDao cityDao;
    @Autowired
    private PropertyTypeDao propertyTypeDao;
    @Autowired
    private Sliders sliders;
    @Autowired
    private Calendar calendar;
    @Autowired
    private BlockWho blockWho;
    @Autowired
    private BlockWhat blockWhat;
    
    
    private Range currentRange = new Range(-180, 180, -90, 90);
    //private List<Property> currentProperties = new ArrayList<Property>();
    //private List<Tag> currentWhoTags = new ArrayList<Tag>();
    //private List<Tag> currentWhatTags = new ArrayList<Tag>();
    
    //init
    private void init(){
	
    }
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
	this.currentRange = newRange;
    }

    @Override
    public void sliderOnChangeHandler()
    {
	
	//TODO
    }

    @Override
    public List<City> getCityList()
    {
	List<Tag> tags =  new ArrayList<Tag>();
	//tags.add(blockWho.getSelected().getTag());
	//tags.add(blockWhat.getSelected().getTag());
	CityRequest request = new CityRequest(
		currentRange,
		sliders.getProperties(),
		tags,
		calendar.getMonth());
	List<City> resultRequest = cityDao.getCityList(request);
	return resultRequest;
    }
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
