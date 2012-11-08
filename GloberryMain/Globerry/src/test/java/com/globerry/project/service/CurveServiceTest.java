/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.domain.City;
import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.junit.*;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author signal
 */
public class CurveServiceTest 
{
    ICityPredicate predicate;
    List<CityShort> cityList = new ArrayList();
    CurveService curveService;
    @Before
    public void before()
    {
	for(int i = 0; i < 5; ++i)
	{
	    CityShort city = new CityShort();
	    city.setId(i);
	    cityList.add(city);
	}
	predicate = mock(ICityPredicate.class);
	when(predicate.compare(cityList.get(0), cityList.get(1), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(0), cityList.get(2), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(0), cityList.get(3), 10)).thenReturn(true);
	when(predicate.compare(cityList.get(0), cityList.get(4), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(1), cityList.get(2), 10)).thenReturn(true);
	when(predicate.compare(cityList.get(1), cityList.get(3), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(1), cityList.get(4), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(2), cityList.get(3), 10)).thenReturn(true);
	when(predicate.compare(cityList.get(2), cityList.get(4), 10)).thenReturn(false);
	when(predicate.compare(cityList.get(3), cityList.get(4), 10)).thenReturn(false);
    }
    @Test
    public void CurveServiceTest()
    {
		curveService = new CurveService(cityList);
		Collection<Curve> curves = curveService.groupCities(10, predicate);
		boolean good = false;
		for(Curve curve:curves)
		{
		    if(curve.getCityList().contains(cityList.get(0)))
		    {
				assertTrue(curve.getCityList().contains(cityList.get(3)));
				assertTrue(curve.getCityList().contains(cityList.get(2)));
				assertTrue(curve.getCityList().contains(cityList.get(1)));
				good = true;
		    }
		}
		assertTrue(good);
    }

   

    /**
     * Test of dropDb method, of class CurveService.
     */
    @Ignore
    @Test
    public void testDropDb()
    {
	System.out.println("dropDb");
	CurveService instance = new CurveService();
	instance.dropDb();
	// TODO review the generated test code and remove the default call to
	// fail.
    }

    /**
     * Test of getCurve method, of class CurveService.
     */
    @Test
    @Ignore
    public void testGetCurve()
    {
	// TODO review the generated test code and remove the default call to
	// fail.
    }
}
