/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.domain.City;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import static org.junit.Assert.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.xml.sax.SAXException;
import com.globerry.project.dao.IDao;
import com.globerry.project.service.gui.SelectBox;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static com.globerry.project.testHelpers.AccessorHelper.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Max
 */
public class ICSHellGateServiceTest
{

	ICSHellGateService aISCSHellGateService = new ICSHellGateService();
	@Mock
	IApplicationContext applicationContext;

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void buildTourRequestTest() throws ParserConfigurationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, URISyntaxException, MalformedURLException
	{
		StringBuilder requestURIBase = new StringBuilder("http://somepage.com?cnt=");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Set<Element> currentCountryResorts = new HashSet<Element>();
		for (int i = 0; i < 10; ++i)
		{
			Element resort = constructResort(i, doc);
			currentCountryResorts.add(resort);
		}
		Integer countryId = 666;
		Class[] args = new Class[3];
		args[0] = StringBuilder.class;
		args[1] = Integer.TYPE;
		args[2] = Set.class;
		Method buildTourRequestMethod = setMethodAccessible(ICSHellGateService.class, "buildTourRequest", args);
		String testRequest = (String) buildTourRequestMethod.invoke(aISCSHellGateService, requestURIBase, countryId, currentCountryResorts);
		System.out.println(testRequest);
		String rightUrl = "http://somepage.com?cnt=666&resort=2&resort=8&resort=4&resort=1&resort=0&resort=7&resort=6&resort=3&resort=5&resort=9";
		String[] params = rightUrl.split("&");
		for (int i = 0, length = params.length; i < length; ++i)
		{
			if (!testRequest.contains(params[i]))
			{
				fail("buildTourRequest make wrong request");
			}
		}
	}

	private Element constructResort(Integer id, Document doc)
	{
		Element resort = doc.createElement("resort");
		resort.setAttribute("id", id.toString());
		return resort;
	}
	//@Test

	public void getRequestedResortsAndGroupByCountryTest() throws ParserConfigurationException, SAXException, IOException
	{
		when(aISCSHellGateService.getXMLDocument(aISCSHellGateService.resortsURI)).thenReturn(null);
	}

	@Test
	public void getToursTest() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		List<City> cityList = initCityForgetToursTest();
		Calendar calendar = Calendar.getInstance();
		final int month = calendar.get(Calendar.MONTH);
		SelectBox whenTag = mock(SelectBox.class);
		when(applicationContext.getWhenTag()).thenReturn(whenTag);
		when(whenTag.getValue()).thenReturn(month);
		IDao<City> cityDaoMock = mock(IDao.class);
		setField(aISCSHellGateService, cityDaoMock, "cityDao");
		Integer[] cityIdArray = new Integer[4];
		for (int i = 0, length = cityList.size(); i < length; ++i)
		{
			when(cityDaoMock.getById(City.class, i)).thenReturn(cityList.get(i));
			cityIdArray[i] = i;
		}

		System.out.println(aISCSHellGateService.getTours(cityIdArray, applicationContext));
	}

	private List<City> initCityForgetToursTest()
	{
		City city = new City();
		List<City> cityList = new ArrayList<City>();

		city.setId(0);
		city.setName("Berlin");
		cityList.add(city);

		city = new City();
		city.setId(1);
		city.setName("Andorra");
		cityList.add(city);

		city = new City();
		city.setId(2);
		city.setRu_name("Вена");
		cityList.add(city);

		city = new City();
		city.setId(3);
		city.setRu_name("Три Долины");
		cityList.add(city);
		return cityList;
	}

	@Test
	public void isContainsCityNameTest() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, IllegalArgumentException, InvocationTargetException
	{
		String someResort = "Berlin Три долины ТРЭШ И УГАР";
		List<City> cityList = initCityForgetToursTest();
		Class[] argClasses = new Class[2];
		argClasses[0] = City.class;
		argClasses[1] = String.class;
		Method isContainsCityNameMethod = setMethodAccessible(ICSHellGateService.class, "isContainsCityName", argClasses);
		assertTrue((Boolean) isContainsCityNameMethod.invoke(aISCSHellGateService, cityList.get(0), someResort));
		assertTrue(!(Boolean) isContainsCityNameMethod.invoke(aISCSHellGateService, cityList.get(1), someResort));
		assertTrue(!(Boolean) isContainsCityNameMethod.invoke(aISCSHellGateService, cityList.get(2), someResort));
		assertTrue((Boolean) isContainsCityNameMethod.invoke(aISCSHellGateService, cityList.get(3), someResort));
		assertTrue(!(Boolean) isContainsCityNameMethod.invoke(aISCSHellGateService, cityList.get(3), null));
		boolean isThrown = false;
		try
		{
			isContainsCityNameMethod.invoke(aISCSHellGateService, null, someResort);
		}
		catch (InvocationTargetException ex)
		{
			if (ex.getCause() instanceof IllegalArgumentException)
			{
				isThrown = true;
			}
		}
		if (!isThrown)
		{
			fail();
		}
	}

	@Test
	public void getFormattedDateTest() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException
	{
		Class[] argClasses = new Class[1];
		argClasses[0] = int.class;
		Method getFormattedDate = setMethodAccessible(ICSHellGateService.class, "getFormattedDate", argClasses);
		for (int i = 0; i < 11; ++i)
		{
			String dateString = (String) getFormattedDate.invoke(aISCSHellGateService, new Integer(i));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formatter.parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			assertTrue(calendar.get(Calendar.MONTH) == i);
		}
	}
}
