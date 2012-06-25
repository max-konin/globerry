package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.CityRequest;
import com.globerry.project.dao.ICityDao;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.utils.PropertySegment;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.dao.Range;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.ICalendar;
import com.globerry.project.service.interfaces.ISliders;
import com.globerry.project.service.interfaces.IUserCityService;

/**
 *
 * @author Sergey Krupin
 *
 */
@Service("userCityService")
@Scope("session")
public class UserCityService implements IUserCityService {

	@Autowired
	private ICityDao cityDao;
	@Autowired
	private IPropertyTypeDao propertyTypeDao;
	@Autowired
	private ISliders sliders;
	@Autowired
	private ICalendar calendar;
	@Autowired
	private BlockWho blockWho;
	@Autowired
	private BlockWhat blockWhat;
	private Range currentRange = new Range(-180, 180, -90, 90);

	//init
	private void init() {
	}

	@Override
	public void clickOnPassiveCity() {
		// TODO Auto-generated method stub
	}

	@Override
	public void clickOnActiveCity() {
		// TODO Auto-generated method stub
	}

	@Override
	public void changeRange(Range newRange) {
		//this.currentRange = newRange;
	}
	

	@Override
	public void sliderOnChangeHandler() {
		//TODO
	}

	@Override
	public List<City> getCityList() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(blockWho.getSelected());
		tags.add(blockWhat.getSelected());
		CityRequest request = new CityRequest(
				currentRange,
				sliders.getProperties(),
				tags,
				calendar.getMonth());
		List<City> resultRequest = cityDao.getCityList(request);
		weightCalculation(resultRequest, request);
		return resultRequest;
	}

	public void update(Observable o, Object arg) {
		if (arg.getClass() == EventUI.class) {
			EventUI eventUI = (EventUI) arg;
			if (eventUI.getParent().getClass() == Slider.class) {
				sliderOnChangeHandler();
			}
		}

	}

	public int getPropertyDaoHash()
	{
		return this.propertyTypeDao.hashCode();
	}
	private void weightCalculation(List<City> result, CityRequest request)
	{
		Iterator<City> itCity = result.iterator();
		while (itCity.hasNext()) {
	            City city = itCity.next();
	            city.setWeight(1);
	            Iterator<PropertySegment> itProperty = request.getOption().iterator();
	            while (itProperty.hasNext()) {
	                PropertySegment propertyRequest = itProperty.next();
	                float propertyCity;
	                try 
	                {
	                    propertyCity = city.getValueByPropertyType(propertyRequest.getPropertyType());

	                    float a, b, sizeBetween;

	                    // Очередня быдло арифметика                    
	                    sizeBetween = propertyRequest.getMaxValue() - propertyRequest.getMinValue();
	                    if (sizeBetween <= 0) {
	                            sizeBetween = (float) 0.1 * (propertyCity - propertyCity);
	                    }
	                    if (propertyRequest.getPropertyType().isBetterWhenLess()) {
	                            a = sizeBetween;
	                            b = propertyCity - propertyRequest.getMinValue();
	                    } else {
	                            a = sizeBetween / (float) 2.0;
	                            b = Math.abs((propertyCity - propertyRequest.getMinValue()) - a);
	                    }
	                    float k = b / a;
	                    if (k < 0.2) {
	                            k = (float) 0.2;
	                    }
	                    city.setWeight(city.getWeight() * k);


	                    if (request.getOption().size() > 0) {
	                            city.setWeight((float) Math.pow(city.getWeight(), 
	                                           1 / ((double) request.getOption().size()))
	                                           );
	                    }
	                }
	                catch (IllegalArgumentException e) 
	                {
	                        //For release mode
	                        //cityForRemove.add(city);     
	                        //For debug mode
	                        // propertyCity = (propertyRequest.getMaxValue() + propertyRequest.getMinValue() / 2);
	                }
	            }
		}
	}
}
