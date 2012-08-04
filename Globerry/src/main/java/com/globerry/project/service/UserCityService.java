package com.globerry.project.service;

import com.globerry.project.dao.*;
import com.globerry.project.domain.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import nl.cloudfarming.client.lib.geotools.GeometryTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.utils.PropertySegment;

import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.IApplicationContext;
import com.globerry.project.utils.ExecuteQueryTimer;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * 
 * @author
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
	{
	    throw new IllegalArgumentException("parameter 'appContext' cannot be null");
	}
	ExecuteQueryTimer inst = ExecuteQueryTimer.getInstanse();
	inst.start();
	cityList = cityDao.getByQuery(queryFactory.createCityRequest(appContext));
	inst.stop();
	weightCalculation(appContext);
	return cityList;
    }

    /**
     * Функция расчета веса
     * 
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
	// ISlider securitySlider =
	// appContext.getSlidersByName(StringManager.securityPropertyTypeName);
	// не влияют на вес
	// ISlider sexSlider =
	// appContext.getSlidersByName(StringManager.sexPropertyTypeName);

	int delta;
	float normDelta;
	for (City city : cityList)
	{
	    normDelta = 0;

	    // alco
	    delta = delta(city.getAlcoCost().getRight(), (int) alcoholSlider.getRightValue(), city.getAlcoCost().getLeft(),
		    (int) alcoholSlider.getLeftValue());
	    normDelta = normalization(delta, alcoholSlider.getPropertyType().getMaxValue() - alcoholSlider.getPropertyType().getMinValue());
	    // food
	    delta = delta(city.getFoodCost().getRight(), (int) foodCostSlider.getRightValue(), city.getFoodCost().getLeft(),
		    (int) foodCostSlider.getLeftValue());
	    normDelta += normalization(delta, foodCostSlider.getPropertyType().getMaxValue()
		    - foodCostSlider.getPropertyType().getMinValue());
	    // temp
	    delta = delta(city.getTemperature().getValue(month).getRight(), (int) temperatureSlider.getRightValue(), city.getTemperature()
		    .getValue(month).getLeft(), (int) temperatureSlider.getLeftValue());
	    normDelta += normalization(delta, temperatureSlider.getPropertyType().getMaxValue()
		    - temperatureSlider.getPropertyType().getMinValue());
	    // livingcost
	    delta = delta(city.getLivingCost().getValue(month).getRight(), (int) livingCostSlider.getRightValue(), city.getLivingCost()
		    .getValue(month).getLeft(), (int) livingCostSlider.getLeftValue());
	    normDelta += normalization(delta, livingCostSlider.getPropertyType().getMaxValue()
		    - livingCostSlider.getPropertyType().getMinValue());
	    // mood
	    delta = delta(city.getMood().getValue(month).getRight(), (int) moodSlider.getRightValue(), city.getMood().getValue(month)
		    .getLeft(), (int) moodSlider.getLeftValue());
	    normDelta += normalization(delta, moodSlider.getPropertyType().getMaxValue() - moodSlider.getPropertyType().getMinValue());
	    city.setWeight(normDelta + 1);// +1 костыль, изза сильно большой
					  // разницы в весе между городами
	}
    }

    /**
     * Нормализация длины отрезка
     * 
     * @author MaxGurenko
     * 
     */
    private float normalization(int object, float norma)
    {
	return ((float) object) / norma;
    }

    /**
     * Вычисление длины пересечения двух отрезков
     * 
     * @author MaxGurenko
     * 
     */
    private int delta(int right1, int right2, int left1, int left2)
    {
	if (left1 < left2)
	{
	    if (right1 > right2)
	    {
		return right2 - left2;
	    } else
	    {
		return right1 - left2;
	    }
	} else if (right1 > right2)
	{
	    return right2 - left1;
	} else
	{
	    return right1 - left1;
	}
    }
    /**
     * Вычисление потенциала городов
     * 
     * @author MaxGurenko
     * 
     */
    public void potentialCalculation()
    {
	int i = 0;
	for (City city : cityList)
	{
	    for (int j = i + 1; j < cityList.size(); j++)
	    {
		City c = cityList.get(j);
		double D = 1/geographicalDistance(city.getLatitude(), city.getLongitude(), c.getLatitude(), c.getLongitude());
		city.addPotential(c.getWeight()*D);
		c.addPotential(city.getWeight()*D);
	    }	
	    i++;
	}
    }

    /**
     * Вычисление расстояния между двумя точками на карте
     * Ellipsoidal Earth projected to a plane
     * http://en.wikipedia.org/wiki/Geographical_distance
     * 
     * @author MaxGurenko
     * 
     */
    public double geographicalDistance(double lat1, double long1, double lat2, double long2)
    {
	return GeometryTools.getDistance(lat1, long1, lat2, long2);
    }

    @Override
    public City[][] getGroupsCity(IApplicationContext appContext, float zlevel)
    {
	getCityList(appContext);
	weightCalculation(appContext);
	return sliceCities(zlevel);
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

    /**
     * Делит города на группы, так чтоб на прямой от города 1 до города 2
     * уровень функции падал ниже zlevel - тогда города будут в разных группах.
     * Функция строится по городам и реализована в функции z(float x, float y).
     * 
     * @param zlevel
     *            уровень по которому будем делить города на группы
     * @return массив групп
     */
    private City[][] sliceCities(float zlevel)
    {
	List<City> groupCities = new ArrayList();
	for (City city : cityList)
	{
	    if (city.getWeight() < 0.08)
	    {
		groupCities.add(city);
	    }
	}
	cityList.removeAll(groupCities);
	groupCities.clear();
	List<City[]> slicedCities = new ArrayList();
	List<City> copyCities = new ArrayList(cityList);
	for (City cityL : cityList)
	{
	    if (!copyCities.remove(cityL))
	    {
		continue;
	    }
	    groupCities.add(cityL);
	    for (City cityC : copyCities)
	    {
		if (isAboveZlevel(cityL, cityC, zlevel))
		{
		    groupCities.add(cityC);
		}
	    }
	    slicedCities.add(groupCities.toArray(new City[0]));
	    copyCities.removeAll(groupCities);
	    groupCities.clear();
	}
	return slicedCities.toArray(new City[0][0]);
    }

    /**
     * Функция для определения попадает ли функция ниже zlevel, на прямой от
     * city1 до city2.
     * 
     * @param city1
     *            первый город
     * @param city2
     *            второй город
     * @param zlevel
     *            уровень сравнения
     * @return false - если ниже уровня zlevel
     * @author ashubin
     */
    private boolean isAboveZlevel(City city1, City city2, float zlevel)
    {
	float infelicity = .5f;
	float point1[] =
	{ city1.getLongitude(), city1.getLatitude() };
	float point2[] =
	{ city2.getLongitude(), city2.getLatitude() };
	float[][] interval =
	{ point1, point2 };
	float middle[] =
	{ (point1[0] + point2[0]) / 2, (point1[1] + point2[1]) / 2 };
	Queue<float[][]> queueInervals = new LinkedList<float[][]>();
	queueInervals.add(interval);
	while (!queueInervals.isEmpty())
	{
	    if (z(middle[0], middle[1]) - zlevel < 0)
		return false;
	    interval = queueInervals.remove();
	    point1[0] = interval[0][0];
	    point1[1] = interval[0][1];
	    middle[0] = (interval[0][0] + interval[1][0]) / 2;
	    middle[1] = (interval[0][1] + interval[1][1]) / 2;
	    point2[0] = interval[1][0];
	    point2[1] = interval[1][1];
	    interval[0] = point1;
	    interval[1] = middle;
	    if (sizeOfInterval(interval) > infelicity)
	    {
		queueInervals.add(interval);
	    }
	    interval[0] = middle;
	    interval[1] = point2;
	    if (sizeOfInterval(interval) > infelicity)
	    {
		queueInervals.add(interval);
	    }
	}
	return true;
    }

    /**
     * Функция для расчета кривулин. Сами кривулины - это сечение этой функции.
     * 
     * @param x
     * @param y
     * @return значение функции
     */
    private float z(float x, float y/* , City[] cities */)
    {
	float ret = 0;
	for (int i = 0, l = cityList.size(); i < l; i++)
	{
	    City point = cityList.get(i);
	    ret += point.getWeight()
		    / (Math.sqrt((point.getLongitude() - x) * (point.getLongitude() - x) + (point.getLatitude() - y)
			    * (point.getLatitude() - y)));
	}
	return ret;
    }

    /**
     * Функция считающая норму в метрическом двухмерном пространстве.
     * 
     * @param interval
     *            хранит две точки, начала и конца(В двухмерном пространстве).
     * @return норму интрервала
     */
    private double sizeOfInterval(float[][] interval)
    {
	return Math.sqrt((interval[0][0] - interval[1][0]) * (interval[0][0] - interval[1][0]) + (interval[0][1] - interval[1][1])
		* (interval[0][1] - interval[1][1]));
    }
}
