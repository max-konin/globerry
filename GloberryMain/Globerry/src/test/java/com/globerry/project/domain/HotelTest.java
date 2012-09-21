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
public class HotelTest
{

    @Test
    public void EqualsTest()
    {
	Hotel  hotel1= new Hotel();
	hotel1.setName("alajava");
	hotel1.setCost(123);
	Hotel hotel2 = new Hotel();
	hotel2.setCost(123);
	hotel2.setName("alajava");
	//System.out.println(hotel1.getName().equals(hotel2.getName()));
	assertEquals(hotel1, hotel2);
	assertEquals(hotel1.hashCode(), hotel2.hashCode());
	//hotel2.setCost(123);
	hotel1.setName("mars");
	//hotel2.setName("mars");
	
	assertFalse(hotel1.equals(hotel2));
	assertFalse(hotel1.hashCode() == hotel2.hashCode());
    }
}
