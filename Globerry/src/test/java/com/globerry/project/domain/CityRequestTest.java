/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.globerry.project.dao.ContextLoaderListener;


/**
 * @author Max
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/serviceTestContext.xml")
@TestExecutionListeners({

    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class

}) 
public class CityRequestTest
{

    @Test
    public void getHQLQueryTest()
    {
	List<Tag> tagList  = new ArrayList<Tag>();
	Tag tag = new Tag();
	tag.setId(1);
	tagList.add(tag);
	tag = new Tag();
	tag.setId(5);
	tagList.add(tag);
	ICityRequest cityRequest = new CityRequest(tagList);
	assertEquals(true,cityRequest.getHQLQuery().equalsIgnoreCase( "select distinct city "
                                                                    + "from City city "
                                                                        + "inner join city.tagList t1 "
                                                                        + "inner join city.tagList t2 "
                                                                    + "where t1.id=1 and t2.id=5"));
	List<Tag> nuLL=null;
	cityRequest.setTags(nuLL);
	assertEquals(true ,cityRequest.getHQLQuery().equalsIgnoreCase("select distinct city from City city"));
    }

}
