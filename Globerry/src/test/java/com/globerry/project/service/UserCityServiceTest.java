package com.globerry.project.service;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.dao.DatabaseManager;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Property;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.GloberryGuiContext;
import com.globerry.project.service.service_classes.IApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Max Gurenko
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/daoTestContext.xml")
@TestExecutionListeners({
    WebContextTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class
})
public class UserCityServiceTest
{
    @Autowired
    UserCityService userCityService;
    @Autowired
    DefaultDatabaseCreator databaseCreator;
    @Autowired
    DatabaseManager databaseManager;
    @Test
    public void initTest(){
	//TODO
    }
    @Test
    public void clickOnActiveCityTest(){
	//TODO
    }
    @Test
    public void changeRangeTest(){
	//TODO
    }
    @Test
    public void sliderOnChangeHandlerTest(){
	//TODO
    }
    @Before
    public void initDB()
    {
	databaseCreator.initPropertyType();
	databaseCreator.initTags();
    }
    @Test
    public void getCityList()
    {
	
	IApplicationContext app = new GloberryGuiContext();
	app.init();
	
	Random rand = new Random();
	
	City city = databaseCreator.generateCityWithPsevdoRandomProperties("City1", rand.nextInt(180), rand.nextInt(180));
	
	List<City> cityList = new ArrayList<City>();
	cityList.add(city);
	userCityService.setCityList(cityList);
	//alcohol
	Slider alcoholSlider = app.getSlidersByName("alcohol");
	alcoholSlider.setLeftValue(10);
	alcoholSlider.setRightValue(30);
	float value = city.getValueByPropertyType(alcoholSlider.getPropertyType(),Month.APRIL);
	if(value < 30 && value > 10)
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 1);
	}
	else
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 0);
	}
	alcoholSlider.setLeftValue(alcoholSlider.getMinValue());
	alcoholSlider.setRightValue(alcoholSlider.getMaxValue());
	
	
	//food
	Slider foodSlider = app.getSlidersByName("food");
	foodSlider.setLeftValue(10);
	foodSlider.setRightValue(30);
	value = city.getValueByPropertyType(foodSlider.getPropertyType(),Month.APRIL);
	if(value < 30 && value > 10)
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 1);
	}
	else
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 0);
	}
	foodSlider.setLeftValue(foodSlider.getMinValue());
	foodSlider.setRightValue(foodSlider.getMaxValue());
	
	
	//livingCost
	Slider livingCostSlider = app.getSlidersByName("livingCost");
	livingCostSlider.setLeftValue(100);
	livingCostSlider.setRightValue(150);
	value = city.getValueByPropertyType(livingCostSlider.getPropertyType(),Month.APRIL);
	if(value < 150 && value > 100)
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 1);
	}
	else
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 0);
	}
	livingCostSlider.setLeftValue(livingCostSlider.getMinValue());
	livingCostSlider.setRightValue(livingCostSlider.getMaxValue());
	
	
	//temperature
	Slider temperatureSlider = app.getSlidersByName("livingCost");
	temperatureSlider.setLeftValue(-15);
	temperatureSlider.setRightValue(15);
	value = city.getValueByPropertyType(temperatureSlider.getPropertyType(),Month.APRIL);
	if(value < 15 && value > -15)
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 1);
	}
	else
	{
	    assert(userCityService.getCitiesWithRequestedProperties(app).size() == 0);
	}
	temperatureSlider.setLeftValue(temperatureSlider.getMinValue());
	temperatureSlider.setRightValue(temperatureSlider.getMaxValue());
    }

}
