/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.interfaces;

import com.globerry.project.domain.City;
import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.LatLng;
import com.globerry.project.service.service_classes.IApplicationContext;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author signal
 */
public interface ICurveService {
	
	public void dropDb();
	
	public Curve getRawCurve();
	
	public Collection<Curve> getCurves(IApplicationContext appContext);
	
	public void setStepLat(float stepLat);
	
	public void setStepLng(float stepLng);
	
	public float getStepLat();
	
	public float getStepLng();
}
