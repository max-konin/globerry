/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.dao;

import com.globerry.project.domain.LatLng;
import java.util.List;

/**
 *
 * @author signal
 */
public interface ICurveDao {
	
	public void addCurve(String key, List<LatLng> curves);
	
	public void deleteCurve(String key);
	
	public List<LatLng> getCurve(String paramsHash);
	
	public void dropDB();
}
