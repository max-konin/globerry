/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.integration.dao;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Country;
import com.google.common.collect.HashMultimap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CountryMappingTest
{

    @Test
    public void vo() throws IOException, ParserConfigurationException, SAXException
    {
        try
        {
            List<City> cityList = new ArrayList<City>();
			City cityForRequest = new City();
			cityForRequest.setName("Andorra");
			cityList.add(cityForRequest);
			cityForRequest = new City();
			cityForRequest.setRu_name("Берлин");
			cityList.add(cityForRequest);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document resortsXML = dBuilder.parse("http://api.icstrvl.ru/tour-api/getResorts.xml");
            resortsXML.getDocumentElement().normalize();

            System.out.println("Root element :" + resortsXML.getDocumentElement().getNodeName());
            NodeList resorts = resortsXML.getElementsByTagName("resort");
            System.out.println("-----------------------");
            HashMultimap <Integer, Element> resortsGroups = HashMultimap.create(); // group resorts by country
            for (int i = 0, j = resorts.getLength(); i < j; i++)
            {
                Node resortNode = resorts.item(i);
                if (resortNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element resort = (Element) resortNode;
                    for (City city : cityList)
                    {
						System.out.println(resort.getAttribute("name"));
                        if (isContainsCityName(city, resort.getAttribute("name")))
                        {
                            resortsGroups.put(new Integer(resort.getAttribute("country")), resort);
                            break;
                        }
                    }
                }
            }
            List<Document> toursXMLs = new ArrayList<Document>();
            StringBuilder requestURIBase = new StringBuilder();
            requestURIBase.append("http://api.icstrvl.ru/tour-api/getTours.xml?ad=");
            requestURIBase.append(2);
            requestURIBase.append("&page=1&pagesize=50&city=538625");
            requestURIBase.append("&date1=");
            requestURIBase.append("2013-02-01");
            requestURIBase.append("&date2=");
            requestURIBase.append("2013-03-01");
            requestURIBase.append("&cnt=");
            Set<Integer> countriesIds = resortsGroups.keySet();
            Node resultTag = null;
            for (Integer countryId : countriesIds)
            {
                Set<Element> currentCountryResorts = resortsGroups.get(countryId);
                StringBuilder requestURI = new StringBuilder(requestURIBase);
                requestURI.append(countryId);
                for(Element resort : currentCountryResorts)
                {
					requestURI.append("&resort=");
                    requestURI.append(resort.getAttribute("id"));
                }
				System.out.println(requestURI.toString());
                Document toursXML = dBuilder.parse(requestURI.toString());
                toursXML.getDocumentElement().normalize();
				if(resultTag == null)
					resultTag = toursXML.getElementsByTagName("result").item(0);
				else
				{
					Node node = toursXML.getElementsByTagName("tours").item(0);
					node = resultTag.getOwnerDocument().importNode(node, true);
					resultTag.appendChild(node);
				}
				 
            }
            Document document = resultTag.getOwnerDocument();
DOMImplementationLS domImplLS = (DOMImplementationLS) document
    .getImplementation();
LSSerializer serializer = domImplLS.createLSSerializer();
String str = serializer.writeToString(resultTag);
System.out.println(str);
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
    }

    boolean isContainsCityName(City city, String string)
    {
		boolean isRuNameContains = false;
		if(city.getRu_name() != null)
			isRuNameContains = string.contains(city.getRu_name().subSequence(0, city.getRu_name().length() - 1));
		boolean isNameContains = false;
		if(city.getName() != null)
			isNameContains = string.contains(city.getName().subSequence(0, city.getName().length() - 1));
        return  isRuNameContains || isNameContains;
    }
}
