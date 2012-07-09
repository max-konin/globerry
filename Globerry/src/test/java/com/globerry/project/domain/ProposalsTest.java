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
public class ProposalsTest
{

    @Test
    public void ProposalsEqualsTest()
    {
	City city = new City();
	Tour tour = new Tour();
	Proposals proposals1= new Proposals();
	proposals1.setCity(city);
	
	Proposals proposals2 = new Proposals();
	proposals2.setCity(city);
	//System.out.println(property1.getName().equals(property2.getName()));
	assertEquals(proposals1, proposals2);
	assertEquals(proposals2, proposals1);
	assertEquals(proposals1, proposals1);
	assertEquals(proposals2, proposals2);
	assertEquals(proposals1.hashCode(), proposals2.hashCode());
	//property2.setCost(123);
	proposals1.getTourList().add(tour);
	//property2.setName("mars");
	
	assertFalse(proposals1.equals(proposals2));
	assertFalse(proposals1.hashCode() == proposals2.hashCode());
    }
}
