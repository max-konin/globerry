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

import com.globerry.project.domain.City;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;

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
public class TagTest
{
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CityDao cityDao;
    @Test
    public void test()
    {

		
	
		Tag tag1 = new Tag();
		tag1.setImg("PUTIN.IMG");
		tag1.setName("PUTIN");
		tagDao.addTag(tag1);
		tagDao.addTag(tag1);
		Tag tag2 = new Tag();
		tag2.setImg("MEDVEDEV.img");
		tag2.setName("MEDVEDEV");

		City city = new City();
		city.setName("Novosibirsk");
		city.getTagList().add(tag1);
		city.getTagList().add(tag2);
		cityDao.addCity(city);//*/
		cityDao.removeCity(city);
		
		int check = 0;
		Tag test = new Tag();
		test.setName("Monkey");
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

}
