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
	City city2 = new City();
	assertEquals(city1, city2);
	assertEquals(city1.hashCode(), city2.hashCode());
	//city2.setCost(123);
	city2.setName("BMW");
	assertFalse(city1.equals(city2));
	assertFalse(city1.hashCode() == city2.hashCode());
    }

}
