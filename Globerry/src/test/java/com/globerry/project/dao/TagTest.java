/**
 * 
 */
package com.globerry.project.dao;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.City;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;

/**
 * @author Artem
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/WEB-INF/configuration/test/daoTestContext.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
public class TagTest
{
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CityDao cityDao;
    
    /**
     * Рандомный генератор стрингов
     * @return стринг
     */
    private String getStringGenerator()
    {  
	
      final int LENGHT = 8;  
      StringBuffer sb = new StringBuffer();  
      for (int x = 0; x <LENGHT; x++)  
      {  
        sb.append((char)((int)(Math.random()*26)+97));  
      }  
      return sb.toString();  
    } 
    
    @Test
    public void test()
    {
	try
	{
		Tag tag1 = new Tag();
		tag1.setImg(getStringGenerator());
		tag1.setName(getStringGenerator());
		tagDao.addTag(tag1);

		Tag tag2 = new Tag();
		tag2.setImg(getStringGenerator());
		tag2.setName(getStringGenerator());

		//tagDao.addTag(tag2);

		City city = new City();
		city.setName("Novosibirsk");
		cityDao.addCity(city);
		
		//tag2.getCityList().add(city);
		tagDao.addTag(tag2);
		//cityDao.removeCity(city);
		
		int check = 0;
		Tag test = new Tag();
		test.setName(getStringGenerator());
		test.setImg("t");
		tagDao.addTag(test);
		Iterator<Tag> it = tagDao.getTagList().iterator();
        	while(it.hasNext())
        	{
        	    Tag test2 = it.next();
        	    if(test.equals(test2))
        	    {
        		++check;
        	    }
        	}
        	assertEquals(check,1);//*/
	}
	catch(MySqlException e)
	{
	    fail(e.getDescription());
	}
    }
    
    @Test
    public void addCityToTagList()
    {
	City city = new City();
	city.setName(getStringGenerator());

	try
	{
	    cityDao.addCity(city);

	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Tag tag = new Tag();
	tag.setName("sdfg");
	city.getTagList().add(tag);
	
	try
	{
	    tagDao.addTag(tag);
	    cityDao.updateCity(city);
	    
	} catch (MySqlException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

}
