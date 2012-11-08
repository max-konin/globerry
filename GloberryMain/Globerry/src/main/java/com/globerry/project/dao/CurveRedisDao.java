/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.Cachable;
import com.globerry.project.domain.Curve;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.*;

/**
 *
 * @author signal
 */
@Repository
public class CurveRedisDao implements ICurveDao {
	
	@Autowired
	private JedisPool redisConnectionPool;
	
	private Jedis jedis;
    protected static final Logger logger = Logger.getLogger(CurveRedisDao.class);
	
	@Override
	public void addCurves(String key, Collection<Curve> curve) {
		jedis = redisConnectionPool.getResource();
		redisConnectionPool.returnResource(jedis);
	}

	@Override
	public void deleteCurves(String key) {
		jedis = redisConnectionPool.getResource();
		redisConnectionPool.returnResource(jedis);
	}

	@Override
	public void dropDB() {
		jedis = redisConnectionPool.getResource();
		jedis.flushDB();
		redisConnectionPool.returnResource(jedis);
	}

	@Override
	public Collection<Curve> getCurves(String paramsHash) {
		jedis = redisConnectionPool.getResource();
		List<Curve> curve = new ArrayList();
		redisConnectionPool.returnResource(jedis);
		return curve;
	}
}
