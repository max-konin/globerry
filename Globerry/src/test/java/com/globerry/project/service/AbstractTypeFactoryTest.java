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

/**
 * @author Artem
 *
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

}) 
public class AbstractTypeFactoryTest
{
      @Autowired
      private CityDao cityDao;
      
      @Autowired 
      private CityPage cityPage;
      @Test
      public void cityDao()
      {
            City city = new City();
            city.setName("Ottawa");
            try
        	{
        	    cityDao.addCity(city);
        	} catch (MySqlException e)
        	{
        	    // TODO Auto-generated catch block
        	    e.printStackTrace();
        	}
      }

      @Test
      public void test()
      {
	  Map<String, Object> map = new HashMap<String,Object>();
	 // IEntityCreator creator = AbstractTypeFactory.responsePage("companyadminpage");
	  cityPage.setList(map);
	  for(Object o:map.values())
	  {
	      System.err.println(o.toString());
	  }
	
      }

}
