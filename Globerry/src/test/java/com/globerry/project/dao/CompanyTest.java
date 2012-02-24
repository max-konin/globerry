package com.globerry.project.dao;


import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import com.globerry.project.dao.ContextLoaderListener;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;

import com.globerry.project.dao.ICompanyDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.Tour;


import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

})
//*/
public class CompanyTest
{
    @Autowired
    private TagDao tagDao;
    
    @Test(timeout=10000)
    public void test() throws Exception
    {
	//update test
	Tag tag1 = new Tag();
	tag1.setImg("img");
	tagDao.addTag(tag1);
	Tag tag2 = new Tag();
	tag2.setId(1);
	tag2.setImg("Dinosaur");
	tag2.setName("asdasdasd");
	tagDao.updateTag(tag2);
	//remove tags by id
	//tagDao.removeTag(5);
	//show list
	Tag test = new Tag();
	test.setName("Monkey");
	/*Tag test1 = new Tag();
	test1.setName("Crocodile");*/
	List<Tag> tagList = tagDao.getTagList();
	Iterator<Tag> it = tagList.iterator();
	while(it.hasNext())
	{
	    Tag test2 = it.next();
	    if(test.getId() == test2.getId())
		assertEquals(true,test2.equals(test));
	}
	
	
	
	
	//ICompanyDao companyDao = new CompanyDao();
//	City city = new City();
//	city.setName("Novosibirsk");
//	Tag tag = new Tag();
//	tag.setImg("java.jpg");
//	tag.setName("prog");
//	tag.getCityList().add(city);
//	
	//System.out.print(test.getName());
	//rangeTest.setMaxX(1);
	//wait(2);
	//assertEquals(1, rangeTest.getMaxX());
	//CompanyDao dao = new CompanyDao();
	//dao.addCompany(test);
	//fail("Not yet implemented");
	//failNotEquals("qqqq", 1, 2);
    }

}
