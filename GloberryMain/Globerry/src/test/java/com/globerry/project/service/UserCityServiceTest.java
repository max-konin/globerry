/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.dao.IDao;


import com.globerry.project.dao.QueryFactory;
import static org.mockito.Mockito.*;

import com.globerry.project.domain.*;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 * @author max
 */
public class UserCityServiceTest
{
    @Mock
    IDao<City> cityDao;

    @Mock
    IDao<PropertyType> propertyTypeDao;

    @Mock
    IDao<Tag> tagDao;

    @Mock
    QueryFactory queryFactory;

    @InjectMocks
    UserCityService service = new UserCityService();

    IApplicationContext appContext = mock(IApplicationContext.class);

    HashMap<String, ISlider> sliders = new HashMap<String, ISlider>();

    List<City> cityList = new ArrayList<City>();

    @Before
    public void setUp()
    {
	MockitoAnnotations.initMocks(this);

	// Определяем состояния тегов в контексте
	SelectBox boxWho = new SelectBox(0);
	boxWho.addValue(1);
	boxWho.setValue(1);

	SelectBox boxWhat = new SelectBox(1);
	boxWhat.addValue(1);
	boxWhat.setValue(1);

	SelectBox boxWhen = new SelectBox(2);
	boxWhen.addValue(1);
	boxWhen.setValue(1);

	when(appContext.getWhoTag()).thenReturn(boxWho);
	when(appContext.getWhatTag()).thenReturn(boxWhat);
	when(appContext.getWhenTag()).thenReturn(boxWhen);

	// Определяем, какие теги возвращает tagDao
	List<Tag> tags = new ArrayList<Tag>();

	for (int i = 0; i < 5; i++)
	{
	    Tag tag = new Tag();
	    tag.setId(i);
	    tag.setName(String.format("tag-%d", i));
	    tags.add(tag);
	}
	when(tagDao.getAll(Tag.class)).thenReturn(tags);
	// Оперделяем список городов, которые возвращает CityDao

	Interval[] values =
	{ new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4),
		new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), new Interval(1, 4), };

	Temperature temp = new Temperature();
	Mood mood = new Mood();
	LivingCost cost = new LivingCost();

	temp.init(values);
	mood.init(values);
	cost.init(values);

	for (int i = 0; i < 100; i++)
	{
	    City city = new City("Berlin", i, i, i, 3, new Interval(1, 5), new Interval(1, 5), 2, 2, true, true, temp, mood, cost, tags);
	    cityList.add(city);
	}

	// Определяем состояние слайдеров, по которым будет проверятся
	// фильтрация городов.

	PropertyType prType = new PropertyType();
	prType.setId(1);
	prType.setMinValue(1);
	prType.setMaxValue(20);
	Slider slider = new Slider(2, prType);
	sliders.put("slider-%d", slider);
	when(appContext.getSlidersByName(anyString())).thenReturn(slider);

	when(appContext.getSliders()).thenReturn(sliders);

	when(cityDao.getByQuery(anyString())).thenReturn(cityList);

    }

    /**
     * Test of getCityList method, of class UserCityService.
     */
    @Test
    public void testGetCityList_IApplicationContext()
    {
	System.out.println("getCityList");

	List<CityShort> result = service.getCityList(appContext);
	verify(cityDao).getByQuery(anyString());

	for (CityShort city : result)
	    assertTrue(city.getWeight() != 0);
    }

    /**
     * Test of getCityList method, of class UserCityService.
     */
    @Test
    public void testGetCityList_0args()
    {
	System.out.println("getCityList");
	service.getCityList();
	verify(cityDao).getAll(City.class);
    }

    /**
     * Test of clickOnPassiveCity method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testClickOnPassiveCity()
    {
	System.out.println("clickOnPassiveCity");
	UserCityService instance = new UserCityService();
	instance.clickOnPassiveCity();
	// TODO review the generated test code and remove the default call to
	// fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of clickOnActiveCity method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testClickOnActiveCity()
    {
	System.out.println("clickOnActiveCity");
	UserCityService instance = new UserCityService();
	instance.clickOnActiveCity();
	// TODO review the generated test code and remove the default call to
	// fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of sliderOnChangeHandler method, of class UserCityService.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testSliderOnChangeHandler()
    {
	System.out.println("sliderOnChangeHandler");
	UserCityService instance = new UserCityService();
	instance.sliderOnChangeHandler();
	// TODO review the generated test code and remove the default call to
	// fail.
	fail("The test case is a prototype.");
    }

}
