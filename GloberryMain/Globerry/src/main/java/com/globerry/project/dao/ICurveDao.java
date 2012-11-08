/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.Curve;
import com.globerry.project.domain.LatLng;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author signal
 */
public interface ICurveDao {
	
	public void addCurves(String key, Collection<Curve> curves);
	
	public void deleteCurves(String key);
	
	public Collection<Curve> getCurves(String paramsHash);
	
	public void dropDB();
}
