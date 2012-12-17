/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.supportClasses;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Country;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Max
 */
@RunWith(MockitoJUnitRunner.class)
public class CountrySAXParserHandlerTest
{
    
    @Mock 
    private IDao<Country> countryDao;
    @Mock
    Attributes attributes;
    @Test
    public void startTest()
    {
        List<Country> countryList = new ArrayList<Country>();
        Country country;
        country = new Country();
        country.setId(0);
        country.setName("Country0");
        countryList.add(country);
        country = new Country();
        country.setId(1);
        country.setName("Country1");
        Country country1 = country;
        countryList.add(country);
        country = new Country();
        country.setId(2);
        country.setName("Country2");
        countryList.add(country);
        when(countryDao.getAll(Country.class)).thenReturn(countryList);
        CountrySAXParserHandler handle = new CountrySAXParserHandler(countryDao);
        when(attributes.getValue("name")).thenReturn("Country1");
        when(attributes.getValue("id")).thenReturn("123");
        try
        {
            handle.startElement(null, null, "Country", attributes);
        }
        catch (SAXException ex)
        {
            Logger.getLogger(CountrySAXParserHandlerTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        verify(countryDao).update(country1);
        assertTrue(country1.getOperatorMapping().getISCId() == 123);
    }
}
