/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * @author Artem
 *
 */
public class AirTest
{

    @Test
    public void EqualsTest()
    {
	Air air1 = new Air();
	air1.setCost(123);
	Date date = new Date();
	date.setTime(123214);
	air1.setDate(date);
	air1.setName("bangkok");
	Air air2 = new Air();
	air2.setCost(123);
	air2.setDate(date);
	air2.setName("bangkok");
	assertEquals(air1, air2);
	assertEquals(air1.hashCode(), air2.hashCode());
	air2.setCost(321);
	assertFalse(air1.equals(air2));
	assertFalse(air1.hashCode() == air2.hashCode());
    }

}
