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
public class CompanyTest
{

    @Test
    public void EqualsTest()
    {
	Company  company1= new Company();
	Company company2 = new Company();
	Tour tour = new Tour();
	assertEquals(company1, company2);
	assertEquals(company1.hashCode(), company2.hashCode());
	//company2.setCost(123);
	company2.setName("mars");
	company2.getTourList().add(tour);
	assertFalse(company1.equals(company2));
	assertFalse(company1.hashCode() == company2.hashCode());
    }

}
