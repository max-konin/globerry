/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.interfaces;

import com.globerry.project.domain.City;
import com.globerry.project.domain.LatLng;
import java.util.List;

/**
 *
 * @author signal
 */
public interface ICurveService {
	
	public List<String> getCurve(Long paramsHash, List<City> cities);
	
	public void dropDb();
	
	public List<List<LatLng>> calcCurves(int mapZoom);
	
	public void updateCityList(List<City> cityList);
	
}
