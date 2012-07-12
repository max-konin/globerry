package com.globerry.project.domain;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

public class TourTest
{

    @Test
    public void TourEqualsTest()
    {

	Tour tour1= new Tour();
	tour1.setName("where");
	tour1.setDescription("vaaa");
	tour1.setCost(1);
	Date date = new Date(123);
	tour1.setDateStart(date);
	tour1.setDateEnd(date);
	Tour tour2 = new Tour();
	tour2.setName("where");
	tour2.setDescription("vaaa");
	tour2.setCost(1);
	tour2.setDateStart(date);
	tour2.setDateEnd(date);
	//System.out.println(property1.getName().equals(property2.getName()));
	assertEquals(tour1, tour2);
	assertEquals(tour2, tour1);
	assertEquals(tour1, tour1);
	assertEquals(tour2, tour2);
	assertEquals(tour1.hashCode(), tour2.hashCode());
	//property2.setCost(123);
	tour1.setDateStart(Date.valueOf("2000-12-11"));
	//property2.setName("mars");
	
	assertFalse(tour1.equals(tour2));
	assertFalse(tour1.hashCode() == tour2.hashCode());
    }
}
