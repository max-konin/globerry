/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Country;
import com.globerry.project.supportClasses.CountrySAXParserHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Max
 */
@Service
@DependsOn(value="sessionFactory")
public class CountryService
{
    @Autowired
    IDao<Country> countryDao;
    protected static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CountrySAXParserHandler.class);
    //@PostConstruct
    public void ICSCountryMappingOnStartUp()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try
        {
            parser = factory.newSAXParser();
        }
        catch (ParserConfigurationException ex)
        {
            logger.debug("Can't create new SAXParser",ex);
            return;
        }
        catch (SAXException ex)
        {
            logger.debug("Can't create new SAXParser",ex);
            return;
        }
        
        
        DefaultHandler handler = new CountrySAXParserHandler(countryDao);
        try
        {
            parser.parse("http://api.icstrvl.ru/tour-api/getCountries.xml", handler);
        }
        catch (SAXException ex)
        {
            logger.debug("Can't parse http://api.icstrvl.ru/tour-api/getCountries.xml",ex);
        }
        catch (IOException ex)
        {
            logger.debug("Can't parse http://api.icstrvl.ru/tour-api/getCountries.xml",ex);
        }
    }
}