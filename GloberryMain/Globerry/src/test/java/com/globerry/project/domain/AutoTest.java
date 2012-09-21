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
	auto1.setCost(432);
	auto1.setName("Mitsubisu");
	Auto auto2 = new Auto();
	auto2.setCost(432);
	auto2.setName("Mitsubisu");
	assertEquals(auto1, auto2);
	assertEquals(auto2, auto1);
	assertEquals(auto1, auto1);
	assertEquals(auto2, auto2);
	assertEquals(auto1.hashCode(), auto2.hashCode());
	//auto2.setCost(123);
	auto2.setName("BMW");
	assertFalse(auto2.equals(auto1));
	assertFalse(auto1.hashCode() == auto2.hashCode());
    }

}
