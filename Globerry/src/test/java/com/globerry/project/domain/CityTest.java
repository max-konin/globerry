/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Artem
 *
 */
public class CityTest
{

    @Test
    public void EqualsTest()
    {
	City  city1= new City();
	city1.setArea(5321);
	city1.setIsValid(true);
	city1.setId(7);
	city1.setPopulation(12312423);
	city1.setName("moscow");
	city1.setRu_name("фывфывфывфыв");
	city1.setLatitude(1);
	city1.setLongitude(2);
	Tag tag1 = new Tag();
	tag1.setName("tag1");
	city1.getTagList().add(tag1);
	
	City city2 = new City();
	city2.setArea(5321);
	city2.setIsValid(false);
	city2.setId(7);
	city2.setPopulation(12312423);
	city2.setName("moscow");
	city2.setRu_name("фывфывфывфыв");
	city2.setLatitude(1);
	city2.setLongitude(2);

	Proposals prop = new Proposals();
	city2.setProposals(prop);


	Tag tag = new Tag();
	tag.setName("tag1");
	city2.getTagList().add(tag);
	assertTrue(city1.equals(city2));
	assertTrue(city2.equals(city1));
	assertEquals(city1.hashCode(), city2.hashCode());
	//city2.setCost(123);
	city2.setName("BMW");
	assertFalse(city1.equals(city2));
	assertFalse(city1.hashCode() == city2.hashCode());
    }

}
