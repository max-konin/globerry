/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.supportClasses;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Country;
import com.globerry.project.service.CountryService;
import com.globerry.project.service.UserCityService;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Max
 */
public class CountrySAXParserHandler extends DefaultHandler
{

    IDao<Country> countryDao;
    List<Country> countrys;
    boolean result = false;
    boolean countries = false;
    boolean country = false;
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CountrySAXParserHandler.class);

    public CountrySAXParserHandler(IDao<Country> countryDao)
    {
        this.countryDao = countryDao;
        countrys = countryDao.getAll(Country.class);
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {

        if (qName.equalsIgnoreCase("Result"))
        {
            result = true;
        }

        if (qName.equalsIgnoreCase("Countries"))
        {
            countries = true;
        }

        if (qName.equalsIgnoreCase("Country"))
        {
            country = true;
            Country country = new Country();
            country.getOperatorMapping().setISCId(new Integer(attributes.getValue("id")).intValue());
            logger.debug("ISC id: " + country.getOperatorMapping().getISCId());
            country.setName(attributes.getValue("name"));
            logger.debug(country.getName());
            for (Country c : countrys)
            {
                if (c.getName().equalsIgnoreCase(country.getName()))
                {
                    c.getOperatorMapping().setISCId(country.getOperatorMapping().getISCId());
                    countryDao.update(c);
                }
            }

        }

    }

    public void endElement(String uri, String localName,
            String qName) throws SAXException
    {
    }

    public void characters(char ch[], int start, int length) throws SAXException
    {

        if (result)
        {
            result = false;
            return;
        }

        if (countries)
        {
            countries = false;
            return;
        }

        if (country)
        {
            country = false;
            return;
        }

    }
}
