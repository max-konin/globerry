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
	company1.setAccess(1);
	company1.setDescription("asd");
	company1.setEmail("asdasdasdasdas");
	company1.setLogin("hjghjghjghjgh");
	company1.setPassword("bvnbnvbnvbn");
	Tour tour1 = new Tour();
	tour1.setDescription("zzzzzz");
	company1.getTourList().add(tour1);
	Company company2 = new Company();
	company2.setAccess(1);
	company2.setDescription("asd");
	company2.setEmail("asdasdasdasdas");
	company2.setLogin("hjghjghjghjgh");
	company2.setPassword("bvnbnvbnvbn");
	Tour tour2 = new Tour();
	tour2.setDescription("z1zzz1zz");
	company1.getTourList().add(tour2);

	assertEquals(company1, company2);
	assertEquals(company1.hashCode(), company2.hashCode());
	//company2.setCost(123);
	company2.setName("mars");
	assertFalse(company1.equals(company2));
	assertFalse(company1.hashCode() == company2.hashCode());
    }

}
