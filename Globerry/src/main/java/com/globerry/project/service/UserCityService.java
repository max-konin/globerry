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
        
        private boolean tagChanged = true;
        
        /*
         * Массив городов, удовлетворяющий условиям на теги
         */
        private List<City> cityList;
	
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
             return cityDao.getCityList();   
	}
        
        @Override
        public void onTagChangeHandler(){
            tagChanged = true;
        }
        
        /*
         * Сохраняет города, подходящие по тэгам в массив cityList. Самостоятельно фильтрут по параметрам.
         * @param appContext Контекст приложения
         * @throw IllegalArgumentException when appContext == null
         */
        @Override
        public List<City> getCityList(IApplicationContext appContext)
        {           
            if (appContext == null) throw new IllegalArgumentException("parameter 'appContext' cannot be null");
            
            if(tags == null)
            {
                tags = new HashMap<Integer, Tag> ();
                for(Tag tag: tagDao.getTagList())
                    tags.put(tag.getId(), tag);
            }
            
            List<City> resultRequest = new ArrayList<City>();
            if (tagChanged){
                 List<Tag> tagsToRequest = new ArrayList<Tag>();
                 tagsToRequest.add(tags.get(appContext.getWhatTag().getValue()));
                 tagsToRequest.add(tags.get(appContext.getWhoTag().getValue()));  
                 cityList = cityDao.getCityListByTagsOnly(tagsToRequest);
                 tagChanged = false;
            }
            boolean f;
            List<PropertySegment> sliderState = new ArrayList<PropertySegment>();
            for(City city: cityList)
            {
                f = true;
                for(String sliderName: appContext.getSliders().keySet())                
                {
                    PropertySegment prop = appContext.getSlidersByName(sliderName).getState();
                    sliderState.add(prop);
                    float val = city.getValueByPropertyType(prop.getPropertyType(), 
                                                            Month.values()[appContext.getWhenTag().getValue()]);
                    if (
                            (val > prop.getRightValue()) ||
                            (val < prop.getLeftValue())
                        )
                        f = false; 
                }
                if (f) resultRequest.add(city);
            }
            weightCalculation(resultRequest, sliderState, Month.values()[appContext.getWhenTag().getValue()]);      
            return resultRequest;
        }
       
        /*
         * Не сохроняет города. Каждый раз вне зависимости от того изменились тэги или нет делает запрос к DAO
         */
        @Override
        public List<City> getCityListWithoutSaveCity(IApplicationContext appContext){
            
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
            
            weightCalculation(resultRequest, sliderState, request.getMonth());
            return resultRequest;
        }	

	public int getPropertyDaoHash()
	{
		return this.propertyTypeDao.hashCode();
	}
	private void weightCalculation(List<City> result, List<PropertySegment> propRequest, Month month)
	{
		Iterator<City> itCity = result.iterator();
		while (itCity.hasNext()) {
	            City city = itCity.next();
	            city.setWeight(1);
	            Iterator<PropertySegment> itProperty = propRequest.iterator();
	            while (itProperty.hasNext()) {
	                PropertySegment propertyRequest = itProperty.next();
	                float propertyCity;
	                try 
	                {
	                    propertyCity = city.getValueByPropertyType(propertyRequest.getPropertyType(),
                                                                       month);

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


	                    if (propRequest.size() > 0) {
	                            city.setWeight((float) Math.pow(city.getWeight(), 
	                                           1 / ((double) propRequest.size()))
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
