/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.City;
import com.globerry.project.service.service_classes.IApplicationContext;
import com.google.common.collect.HashMultimap;
import org.springframework.stereotype.Service;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author Max
 */
@Service
public class ICSHellGateService
{

	@Autowired
	IDao<City> cityDao;
	protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ICSHellGateService.class);
	private final String resortsURI = "http://api.icstrvl.ru/tour-api/getResorts.xml";
	private final String toursURI = "http://api.icstrvl.ru/tour-api/getTours.xml?ad=";

	public String getTours(int[] cityIdArray, IApplicationContext appContext)
	{
		List<City> requestCities = getCities(cityIdArray);
		Node tours = null;
		try
		{
			HashMultimap<Integer, Element> resortsGrouppedByCountry = getRequestedResortsAndGroupByCountry(requestCities);
			StringBuilder requestURIBase = generateURIBaseForToursRequest(appContext);
			tours = doTourRequestsAndGetTours(resortsGrouppedByCountry, requestURIBase);
		}
		catch (SAXException ex)
		{
		}
		catch (ParserConfigurationException ex)
		{
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return serializeNode(tours);
	}

	private boolean isContainsCityName(City city, String string)
	{
		boolean isRuNameContains = false;
		if (city.getRu_name() != null)
		{
			isRuNameContains = string.contains(city.getRu_name().subSequence(0, city.getRu_name().length() - 1));
		}
		boolean isNameContains = false;
		if (city.getName() != null)
		{
			isNameContains = string.contains(city.getName().subSequence(0, city.getName().length() - 1));
		}
		return isRuNameContains || isNameContains;
	}

	private static String getTagValue(String sTag, Element eElement)
	{
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	private List<City> getCities(int[] cityIds)
	{
		List<City> cityList = new ArrayList<City>();
		for (int i = 0, iterations = cityIds.length; i < iterations; ++i)
		{
			cityList.add(cityDao.getById(City.class, cityIds[i]));
		}
		return cityList;
	}

	private Document getDocument(String URI) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(URI);
		document.getDocumentElement().normalize();
		return document;
	}

	private HashMultimap<Integer, Element> getRequestedResortsAndGroupByCountry(List<City> requestCities) throws ParserConfigurationException, SAXException, IOException
	{
		Document resortsXML = getDocument(resortsURI);
		NodeList resorts = resortsXML.getElementsByTagName("resort");
		HashMultimap<Integer, Element> resortsGroups = HashMultimap.create(); // group resorts by country
		for (int i = 0, j = resorts.getLength(); i < j; i++)
		{
			Node resortNode = resorts.item(i);
			if (resortNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element resort = (Element) resortNode;
				for (City city : requestCities)
				{
					//logger
					if (isContainsCityName(city, resort.getAttribute("name")))
					{
						resortsGroups.put(new Integer(resort.getAttribute("country")), resort);
						break;
					}
				}
			}
		}
		return resortsGroups;

	}

	private StringBuilder generateURIBaseForToursRequest(IApplicationContext appContext)
	{
		StringBuilder requestURIBase = new StringBuilder();
		requestURIBase.append(toursURI);
		requestURIBase.append(2);
		requestURIBase.append("&page=1&pagesize=50&city=538625");
		requestURIBase.append(getDateRangeForURI(appContext));
		requestURIBase.append("&cnt=");
		return requestURIBase;
	}

	private String getDateRangeForURI(IApplicationContext appContext)
	{

		int requestedMonth = appContext.getWhenTag().getValue();

		StringBuilder dateRange = new StringBuilder();
		String startDate = getFormattedDate(requestedMonth);
		String endDate = getFormattedDate(requestedMonth + 1);
		dateRange.append("&date1=");
		dateRange.append(startDate);
		dateRange.append("&date2=");
		dateRange.append(endDate);
		return dateRange.toString();
	}

	private String getFormattedDate(int requestedMonth)
	{
		Calendar cal = Calendar.getInstance();
		if (cal.get(Calendar.MONTH) > requestedMonth);
		{
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		}
		cal.set(Calendar.MONTH, requestedMonth);
		cal.set(Calendar.DATE, 1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}

	private Node doTourRequestsAndGetTours(HashMultimap<Integer, Element> resortsGrouppedByCountry, StringBuilder requestURIBase) throws ParserConfigurationException, SAXException, IOException
	{
		Set<Integer> countriesIds = resortsGrouppedByCountry.keySet();
		Node allTours = null;
		for (Integer countryId : countriesIds)
		{
			Set<Element> currentCountryResorts = resortsGrouppedByCountry.get(countryId);
			String tourURI = buildTourRequest(requestURIBase, countryId, currentCountryResorts);
			Document toursXML = getDocument(tourURI);
			if (allTours == null)
			{
				allTours = toursXML.getElementsByTagName("result").item(0);
			}
			else
			{
				Node node = toursXML.getElementsByTagName("tours").item(0);
				node = allTours.getOwnerDocument().importNode(node, true);
				allTours.appendChild(node);
			}

		}
		return allTours;
	}

	private String buildTourRequest(StringBuilder requestURIBase,int countryId, Set<Element> currentCountryResorts)
	{
		StringBuilder requestURI = new StringBuilder(requestURIBase);
		requestURI.append(countryId);
		for (Element resort : currentCountryResorts)
		{
			requestURI.append("&resort=");
			requestURI.append(resort.getAttribute("id"));
		}
		return requestURI.toString();
	}

	private String serializeNode(Node node)
	{
		Document document = node.getOwnerDocument();
		DOMImplementationLS domImplLS = (DOMImplementationLS) document
				.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		return serializer.writeToString(node);
	}
}
