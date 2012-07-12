/**
 * 
 */
package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.domain.City;
import com.globerry.project.service.admin.AbstractTypeFactory;
import com.globerry.project.service.admin.AdminParser;
import com.globerry.project.service.admin.CityPage;
import com.globerry.project.service.admin.CompanyPage;
import com.globerry.project.service.admin.EventPage;
import com.globerry.project.service.admin.WrongPage;

/**
 * @author Artem
 *
*/

public class AbstractTypeFactoryTest
{
    private AbstractTypeFactory abstractTypeFactory = new AbstractTypeFactory();
    
    @Test 
    public void testAdmin()
    {
	/*String coord = "20 - 250";
	System.err.println(adminParser.getAverageValue(coord));*/
	assertTrue(abstractTypeFactory.responsePage("CitYAdminPage") instanceof CityPage);
	assertTrue(abstractTypeFactory.responsePage("evenTADMINPAGE") instanceof EventPage);
	assertTrue(abstractTypeFactory.responsePage("CompanYADminPage") instanceof CompanyPage);
	assertTrue(abstractTypeFactory.responsePage("s;kltghdfgho;ierkhg;ohpdfiuyhsrtuhiposrjhposr[ph") instanceof WrongPage);
    }
    
}
