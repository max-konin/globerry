/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Max
 *
 */

public class CityRequestTest
{

    @Test
    public void test()
    {
	List<Tag> tagList  = new ArrayList<Tag>();
	Tag tag = new Tag();
	tag.setId(1);
	tagList.add(tag);
	tag = new Tag();
	tag.setId(5);
	tagList.add(tag);
	ICityRequest cityRequest = new CityRequest(tagList);
	assert(cityRequest.getHQLQuery().equalsIgnoreCase("select distinct city from City city inner join city.tagList t1 inner join city.tagList t2 where t1.id=1 and t2.id=5"));
	List<Tag> nuLL=null;
	cityRequest.setTags(nuLL);
	assert(cityRequest.getHQLQuery().equalsIgnoreCase("select distinct city"));
    }

}
