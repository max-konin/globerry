/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.integration.dao;

import com.globerry.project.dao.CurveDao;
import com.globerry.project.domain.LatLng;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import redis.clients.jedis.Jedis;

/**
 *
 * @author signal
 */
public class CurveDaoTest {
	
	private Jedis jedis = new Jedis("localhost");
	
	public CurveDaoTest() {
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
	 * Test of addCurve method, of class CurveDao.
	 */
	@Test
	@Ignore
	public void testAddCurve() {
		System.out.println("addCurve");
		String key = "";
		List<LatLng> curves = new ArrayList();
		CurveDao instance = new CurveDao();
		instance.addCurve(key, curves);
		// TODO review the generated test code and remove the default call to fail.
		assert(jedis.lindex(key, 0).equals("Test"));
	}

	/**
	 * Test of deleteCurve method, of class CurveDao.
	 */
	@Test
	@Ignore
	public void testDeleteCurve() {
		System.out.println("deleteCurve");
		String key = "";
		CurveDao instance = new CurveDao();
		instance.deleteCurve(key);
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of dropDB method, of class CurveDao.
	 */
	@Test
	@Ignore
	public void testDropDB() {
		System.out.println("dropDB");
		CurveDao instance = new CurveDao();
		instance.dropDB();
		// TODO review the generated test code and remove the default call to fail.
		
	}

	/**
	 * Test of getCurve method, of class CurveDao.
	 */
	@Test
	@Ignore
	public void testGetCurve() {
		System.out.println("getCurve");
		String paramsHash = null;
		CurveDao instance = new CurveDao();
		List expResult = null;
		List result = instance.getCurve(paramsHash);
		// TODO review the generated test code and remove the default call to fail.
		
	}
}
