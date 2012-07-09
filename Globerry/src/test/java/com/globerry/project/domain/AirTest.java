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
public class AirTest
{

    @Test
    public void EqualsTest()
    {
	Air air1 = new Air();
	Air air2 = new Air();
	assertEquals(air1, air2);
	assertEquals(air1.hashCode(), air2.hashCode());
	air2.setCost(123);
	assertFalse(air1.equals(air2));
	assertFalse(air1.hashCode() == air2.hashCode());
    }

}
