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
public class AutoTest
{

    @Test
    public void EqualsTest()
    {
	Auto  auto1= new Auto();
	Auto auto2 = new Auto();
	assertEquals(auto1, auto2);
	assertEquals(auto1.hashCode(), auto2.hashCode());
	//auto2.setCost(123);
	auto2.setName("BMW");
	assertFalse(auto2.equals(auto1));
	assertFalse(auto1.hashCode() == auto2.hashCode());
    }

}
