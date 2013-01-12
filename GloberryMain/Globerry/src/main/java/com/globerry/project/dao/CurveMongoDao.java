/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.CurveContainer;
import com.globerry.project.domain.LatLng;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author signal
 */
@Repository
public class CurveMongoDao implements ICurveDao {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void addCurves(String key, Collection<Curve> curveList) {
		CurveContainer curves = new CurveContainer(key, curveList);
		mongoTemplate.insert(curves);
	}

	@Override
	public void deleteCurves(String key) {
		
	}

	@Override
	public void dropDB() {
		mongoTemplate.dropCollection("curves");
	}

	@Override
	public Collection<Curve> getCurves(String paramsHash) {
		CurveContainer result = mongoTemplate.findById(paramsHash, CurveContainer.class);
		if (result != null) {
			return result.getCurves();
		}
		return null;
	}
}
