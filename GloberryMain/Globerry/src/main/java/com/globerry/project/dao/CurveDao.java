/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.LatLng;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

/**
 *
 * @author signal
 */
@Repository
public class CurveDao implements ICurveDao{
	
	private Jedis jedis = new Jedis("localhost");

	@Override
	public void addCurve(String key, List<LatLng> curves) {
		for(LatLng value : curves)
			jedis.lpush(key, value.toJSON());
	}

	@Override
	public void deleteCurve(String key) {
		jedis.del(key);
	}

	@Override
	public void dropDB() {
		jedis.flushDB();
	}

	@Override
	public List<LatLng> getCurve(String paramsHash) {
		List<LatLng> curves = new ArrayList();
		float lat, lng;
		for(long i = 0, l = jedis.llen(paramsHash); i < l; ++i) {		
			lat = Float.parseFloat(jedis.lindex(paramsHash, i).replaceAll("[{,}]|\"[a-z]*\":", "").replaceFirst(",[0-9]*[.][0-9]*", ""));
			lng = Float.parseFloat(jedis.lindex(paramsHash, i).replaceAll("[{,}]|\"[a-z]*\":", "").replaceFirst("[0-9]*[.][0-9]*,", ""));
			curves.add(new LatLng(lat, lng));
		}
		return curves;
	}
}
