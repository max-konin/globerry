/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.domain.City;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author signal
 */
public class CurveServiceTest {
	
	public CurveServiceTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of dropDb method, of class CurveService.
	 */
    @Ignore
	@Test
	public void testDropDb() {
		System.out.println("dropDb");
		CurveService instance = new CurveService();
		instance.dropDb();
		// TODO review the generated test code and remove the default call to fail.
	}

	/**
	 * Test of getCurve method, of class CurveService.
	 */
	@Test
	@Ignore
	public void testGetCurve() {
		System.out.println("getCurve");
		System.out.println(System.getProperty("java.library.path"));
		Long paramsHash = 6757L;
		List<City> cities = new ArrayList<City>();
		CurveService instance = new CurveService();
		List expResult = new ArrayList();
		List result = instance.getCurve(paramsHash, cities);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.	
	}
}
