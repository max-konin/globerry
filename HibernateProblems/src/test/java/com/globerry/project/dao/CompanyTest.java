package com.globerry.project.dao;

import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import com.globerry.project.domain.Company;

import junit.framework.TestCase;


public class CompanyTest extends TestCase
{

    @Ignore("Not yet implemented")
    @Test(timeout=1)
    public void test() //throws InterruptedException
    {
	Company test = new Company();
	//rangeTest.setMaxX(1);
	//wait(2);
	//assertEquals(1, rangeTest.getMaxX());
	CompanyDao dao = new CompanyDao();
	dao.addCompany(test);
	fail("Not yet implemented");
	//failNotEquals("qqqq", 1, 2);
    }

}
