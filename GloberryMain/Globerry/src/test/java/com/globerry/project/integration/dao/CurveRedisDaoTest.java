/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.integration.dao;

import com.globerry.project.dao.CurveRedisDao;
import com.globerry.project.dao.ICurveDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.LatLng;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 *
 * @author signal
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/daoTestContext.xml")
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	ContextLoaderListener.class
})
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurveRedisDaoTest {
	
	@Autowired
	private ICurveDao curveRedisDao;
	@Autowired
	private JedisPool redisConnectionPool;
	
	private Jedis jedis;	
	
	@Before
	public void setUp() {
		jedis = redisConnectionPool.getResource();
	}
	
	@After
	public void tearDown() {
		jedis.flushDB();
		redisConnectionPool.returnResource(jedis);
	}
	
	@Test
        @Ignore
	public void testAddCurve() throws Exception {
		String key = "1234";
		List<LatLng> curvesLP = new ArrayList();
		curvesLP.add(new LatLng(4, 32));
		curvesLP.add(new LatLng(45, 21));
		Curve curve = new Curve(new ArrayList(), curvesLP);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(curve);
		oos.close();
		String t = curve.toJSON();
		Curve tu = new Curve();
		tu.fromJSON(t);
		assertEquals(tu.toJSON(), t);
	}

	@Test
        @Ignore
	public void testDeleteCurve() {
		String key = "1234";
		List<LatLng> curves = new ArrayList();
		curves.add(new LatLng(4, 32));
		curves.add(new LatLng(45, 21));
		Transaction t = jedis.multi();
		for(LatLng value : curves)
			t.lpush(key, value.toJSON());
		t.exec();
		curveRedisDao.deleteCurves(key);
		assertNull(jedis.get(key));
	}

	@Test
        @Ignore
	public void testDropDB() {
		curveRedisDao.dropDB();
		assertNull(jedis.randomKey());
	}
	
	@Test
        @Ignore
	public void testGetCurve() throws Exception {
		String paramsHash = "1234";
		List<LatLng> curvesLP = new ArrayList();
		curvesLP.add(new LatLng(4, 32));
		curvesLP.add(new LatLng(45, 21));
		Curve curve = new Curve(new ArrayList(), curvesLP);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(curve);
		oos.close();
		jedis.set(paramsHash, baos.toString());
	}
}
