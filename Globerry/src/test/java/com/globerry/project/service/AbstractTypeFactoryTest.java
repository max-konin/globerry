/**
 * 
 */
package com.globerry.project.service;

import static org.junit.Assert.*;


import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.CompanyPage;
import com.globerry.project.service.admin.EventPage;
import com.globerry.project.service.admin.IEntityCreator;
import com.globerry.project.service.admin.WrongPage;

/**
 * @author Artem
 *
*/

public class AbstractTypeFactoryTest
{
    @Mock
    CityPage cityPage;
    @Mock
    EventPage eventPage;
    @Mock
    CompanyPage companyPage;
    @Mock
    WrongPage wrongPage;
    
    @InjectMocks
    private AbstractTypeFactory abstractTypeFactory = new AbstractTypeFactory();
    
    @Test 
    public void testAdmin()
    {
	MockitoAnnotations.initMocks(this);
	/*String coord = "20 - 250";
	System.err.println(adminParser.getAverageValue(coord));*/
	//System.out.println(abstractTypeFactory.responsePage("eventadminpage"));
	

	assertTrue(abstractTypeFactory.responsePage("cityadminpage") instanceof CityPage);
	assertTrue(abstractTypeFactory.responsePage("evenTADMINPAGE") instanceof EventPage);
	assertTrue(abstractTypeFactory.responsePage("CompanYADminPage") instanceof CompanyPage);
	assertTrue(abstractTypeFactory.responsePage("s;kltghdfgho;ierkhg;ohpdfiuyhsrtuhiposrjhposr[ph") instanceof WrongPage);
    }
    
}
