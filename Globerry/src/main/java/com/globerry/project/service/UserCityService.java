package com.globerry.project.service;

import com.globerry.project.dao.*;
import com.globerry.project.domain.*;
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

import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.Slider;
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
public class UserCityService implements IUserCityService
{

    @Autowired
    private IDao<City> cityDao;
    @Autowired
    private IDao<PropertyType> propertyTypeDao;
    @Autowired
    private IDao<Tag> tagDao;
    @Autowired
    private QueryFactory queryFactory;

    private boolean tagChanged = true;

    /*
     * Массив городов, удовлетворяющий условиям на теги
     */
    private List<City> cityList;

    protected static final Logger logger = Logger.getLogger(UserCityService.class);
    /*
     * Затычка, чтобы тэги потстоянно из бд не дергать. Пока не решим, что
     * делать с SelectBox-ами
     */
    private HashMap<Integer, Tag> tags;

    // init
    private void init()
    {
    }

    @Override
    public void clickOnActiveCity()
    {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sliderOnChangeHandler()
    {
	// TODO
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onTagChangeHandler()
    {
	tagChanged = true;
    }

    /*
     * Сохраняет города, подходящие по тэгам в массив cityList. Самостоятельно
     * фильтрут по параметрам.
     * 
     * @param appContext Контекст приложения
     * 
     * @throw IllegalArgumentException when appContext == null
     */
    @Override
    public List<City> getCityList(IApplicationContext appContext)
    {
	if (appContext == null)
	    throw new IllegalArgumentException("parameter 'appContext' cannot be null");

	List<City> resultRequest = cityDao.getByQuery(queryFactory.createCityRequest(appContext));

	weightCalculation(resultRequest, null, Month.values()[appContext.getWhenTag().getValue()]);
	return resultRequest;
    }

    /*
     * private void weightCalculation(List<City> result, List<PropertySegment>
     * propRequest, Month month) { Iterator<City> itCity = result.iterator();
     * while (itCity.hasNext()) { City city = itCity.next(); city.setWeight(1);
     * Iterator<PropertySegment> itProperty = propRequest.iterator(); while
     * (itProperty.hasNext()) { PropertySegment propertyRequest =
     * itProperty.next(); float propertyCity; try { propertyCity =
     * city.getValueByPropertyType(propertyRequest.getPropertyType(), month);
     * 
     * float a, b, sizeBetween;
     * 
     * // Очередня быдло арифметика sizeBetween =
     * propertyRequest.getRightValue() - propertyRequest.getLeftValue(); if
     * (sizeBetween <= 0) { sizeBetween = (float) 0.1 * (propertyCity -
     * propertyCity); } if
     * (propertyRequest.getPropertyType().isBetterWhenLess()) { a = sizeBetween;
     * b = propertyCity - propertyRequest.getLeftValue(); } else { a =
     * sizeBetween / (float) 2.0; b = Math.abs((propertyCity -
     * propertyRequest.getLeftValue()) - a); } float k = b / a; if (k < 0.2) { k
     * = (float) 0.2; } city.setWeight(city.getWeight() * k);
     * 
     * 
     * if (propRequest.size() > 0) { city.setWeight((float)
     * Math.pow(city.getWeight(), 1 / ((double) propRequest.size())) ); } }
     * catch (IllegalArgumentException e) { //For release mode
     * //cityForRemove.add(city); //For debug mode // propertyCity =
     * (propertyRequest.getMaxValue() + propertyRequest.getMinValue() / 2); } }
     * } }
     */
    /**Функция расчета веса
     * @author MaxGurenko
     * @param IAppContext
     */
    private void weightCalculation(IApplicationContext appContext)
    {
	Month month = Month.values()[appContext.getWhenTag().getValue()];
	ISlider alcoholSlider = appContext.getSlidersByName(StringManager.alcoholPropertyTypeName);
	ISlider foodCostSlider = appContext.getSlidersByName(StringManager.foodCostPropertyTypeName);
	ISlider temperatureSlider = appContext.getSlidersByName(StringManager.temperaturePropertyTypeName);
	ISlider livingCostSlider = appContext.getSlidersByName(StringManager.livingCostPropertyTypeName);
	ISlider moodSlider = appContext.getSlidersByName(StringManager.moodPropertyTypeName);
	//ISlider securitySlider = appContext.getSlidersByName(StringManager.securityPropertyTypeName); не влияют на вес
	//ISlider sexSlider = appContext.getSlidersByName(StringManager.sexPropertyTypeName);
	
	int delta;
	float normDelta;
	for (City city : cityList)
	{
	    normDelta = 0;
	    
	    //alco
	    delta = delta(city.getAlcoCost().getRight(), (int)alcoholSlider.getRightValue(), city.getAlcoCost().getLeft(), (int)alcoholSlider.getLeftValue());
	    normDelta = normalization(delta, alcoholSlider.getPropertyType().getMaxValue() - alcoholSlider.getPropertyType().getMinValue());
	    //food
	    delta = delta(city.getFoodCost().getRight(), (int)foodCostSlider.getRightValue(), city.getFoodCost().getLeft(), (int)foodCostSlider.getLeftValue());
	    normDelta += normalization(delta, foodCostSlider.getPropertyType().getMaxValue() - foodCostSlider.getPropertyType().getMinValue());
	    //temp
	    delta = delta(city.getTemperature().getValue(month).getRight(), (int)temperatureSlider.getRightValue(), city.getTemperature().getValue(month).getLeft(), (int)temperatureSlider.getLeftValue());
	    normDelta += normalization(delta, temperatureSlider.getPropertyType().getMaxValue() - temperatureSlider.getPropertyType().getMinValue());
	    //livingcost
	    delta = delta(city.getLivingCost().getValue(month).getRight(), (int)livingCostSlider.getRightValue(), city.getLivingCost().getValue(month).getLeft(), (int)livingCostSlider.getLeftValue());
	    normDelta += normalization(delta, livingCostSlider.getPropertyType().getMaxValue() - livingCostSlider.getPropertyType().getMinValue());
	    //mood
	    delta = delta(city.getMood().getValue(month).getRight(), (int)moodSlider.getRightValue(), city.getMood().getValue(month).getLeft(), (int)moodSlider.getLeftValue());
	    normDelta += normalization(delta, moodSlider.getPropertyType().getMaxValue() - moodSlider.getPropertyType().getMinValue());
	    city.setWeight(normDelta);
	}
    }
    /**
     * Нормализация длины отрезка
     * @author MaxGurenko
     * 
     */
    private float normalization(int object,float norma)
    {
	return ((float)object)/norma;
    }
    /**
     * Вычисление длины пересечения двух отрезков
     * @author MaxGurenko
     * 
     */
    private int delta(int right1, int right2, int left1, int left2)
    {
	if (left1 < left2)
	    if (right1 > right2)
		return right2 - left2;
	    else
		return right1 - left2;
	else if (right1 > right2)
	    return right2 - left1;
	else
	    return right1 - left1;
    }

    @Override
    public List<City> getCityList()
    {
	return cityDao.getAll(City.class);
    }

    @Override
    public void clickOnPassiveCity()
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCity(City city)
    {
	// TODO Auto-generated method stub

    }
}
