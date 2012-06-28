package com.globerry.project.service;

import com.globerry.project.dao.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.utils.PropertySegment;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.interfaces.ICalendar;
import com.globerry.project.service.interfaces.ISliders;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.*;
import org.apache.log4j.Logger;

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
	private ITagDao tagDao;
	@Autowired
	private ISliders sliders;
	@Autowired
	private ICalendar calendar;
	@Autowired
	private BlockWho blockWho;
	@Autowired
	private BlockWhat blockWhat;
	private Range currentRange = new Range(-180, 180, -90, 90);
        
        protected static final Logger logger = Logger.getLogger(UserCityService.class);
        /*
         * Затычка, чтобы тэги потстоянно из бд не дергать. Пока не решим, что делать с SelectBox-ами
         */
        private HashMap<Integer, Tag> tags;

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
            List<Tag> tags_l = new ArrayList<Tag>();
            tags_l.add(blockWho.getSelected());
            tags_l.add(blockWhat.getSelected());
            CityRequest request = new CityRequest(
                            currentRange,
                            sliders.getProperties(),
                            tags_l,
                            calendar.getMonth());
            List<City> resultRequest = cityDao.getCityList(request);
            weightCalculation(resultRequest, request);
            return resultRequest;
	}
        @Override
        public List<City> getCityList(IApplicationContext appContext){
            
            if(tags == null)
            {
                tags = new HashMap<Integer, Tag> ();
                for(Tag tag: tagDao.getTagList())
                    tags.put(tag.getId(), tag);
            }
           // tags.add(appContext.getWhoTag().getCurrentIndex());
            //tags.add(blockWhat.getSelected());
            List<Tag> tagsToRequest = new ArrayList<Tag>();
            tagsToRequest.add(tags.get(appContext.getWhatTag().getValue()));
            tagsToRequest.add(tags.get(appContext.getWhoTag().getValue()));
            
            List<PropertySegment> sliderState = new ArrayList<PropertySegment>();
            for(String sliderName: appContext.getSliders().keySet())
            {
                sliderState.add(appContext.getSlidersByName(sliderName).getState());
            }
            CityRequest request = new CityRequest(
                            currentRange,
                            sliderState,
                            tagsToRequest,
                            Month.values()[appContext.getWhenTag().getValue()]); 
         
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
	                    propertyCity = city.getValueByPropertyType(propertyRequest.getPropertyType(),
                                                                       request.getMonth());

	                    float a, b, sizeBetween;

	                    // Очередня быдло арифметика                    
	                    sizeBetween = propertyRequest.getRightValue() - propertyRequest.getLeftValue();
	                    if (sizeBetween <= 0) {
	                            sizeBetween = (float) 0.1 * (propertyCity - propertyCity);
	                    }
	                    if (propertyRequest.getPropertyType().isBetterWhenLess()) {
	                            a = sizeBetween;
	                            b = propertyCity - propertyRequest.getLeftValue();
	                    } else {
	                            a = sizeBetween / (float) 2.0;
	                            b = Math.abs((propertyCity - propertyRequest.getLeftValue()) - a);
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
