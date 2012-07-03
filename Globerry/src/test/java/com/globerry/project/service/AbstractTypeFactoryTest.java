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
import com.globerry.project.service.admin.AdminParser;

/**
 * @author Artem
 *
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/WEB-INF/configuration/test/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

}) 
public class AbstractTypeFactoryTest
{
    @Autowired
    private AdminParser adminParser;
    
    @Test 
    public void testAdmin()
    {
	String coord = "20 - 250";
	System.err.println(adminParser.getAverageValue(coord));

    }
    
}
