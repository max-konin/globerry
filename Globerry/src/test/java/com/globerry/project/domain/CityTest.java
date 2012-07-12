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
	Event event = new Event();
	city2.getEvents().add(event);
	Proposals prop = new Proposals();
	city2.setProposals(prop);
	Property property = new Property();
	city2.getPropertyList().add(property);
	DependingMonthProperty dmp = new DependingMonthProperty();
	city2.getDmpList().add(dmp);
	Tag tag = new Tag();
	tag.setName("tag");
	city2.getTagList().add(tag);
	assertEquals(city1, city2);
	assertEquals(city2, city1);
	assertEquals(city1.hashCode(), city2.hashCode());
	//city2.setCost(123);
	city2.setName("BMW");
	assertFalse(city1.equals(city2));
	assertFalse(city1.hashCode() == city2.hashCode());
    }

}
