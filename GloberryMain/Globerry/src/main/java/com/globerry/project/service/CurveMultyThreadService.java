/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.dao.ICurveDao;
import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.LatLng;
import com.globerry.project.service.interfaces.ICurveService;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.IApplicationContext;
import com.globerry.project.utils.CurveThreadCalculator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import static com.globerry.project.utils.GeoTools.*;

/**
 *
 * @author Max
 */
@Service
@Scope("session")
public class CurveMultyThreadService implements ICurveService
{

    private static final int zRadiusConst = 1000000;

    private static float stepLat = 0.2f;
    private static float stepLng = 0.2f;
    private float epsilon;
	
    private List<CityShort> cityList;
    
	private Map<Integer, Float> zLevel;
	
	Collection<Curve> curves;
	Iterator<Curve> curveIterator;
	
    @Autowired
    private ICurveDao curveMongoDao;
    @Autowired
    private IUserCityService userCityService;

	private CurveThreadCalculator[] curveThreadCalculators = new CurveThreadCalculator[8];
	
	@Override
	public void setStepLat(float stepLat) {
		this.stepLat = stepLat;
		
	}

	@Override
	public void setStepLng(float stepLng) {
		this.stepLng = stepLng;
	}
	
	@Override
	public float getStepLat() {
		return this.stepLat;
	}

	@Override
	public float getStepLng() {
		return this.stepLng;
	}
	
    private void init()
    {
        zLevel = new HashMap<Integer, Float>();
        zLevel.put(new Integer(2), new Float(14f));
        zLevel.put(new Integer(3), new Float(18f));
        zLevel.put(new Integer(4), new Float(22f));
        zLevel.put(new Integer(5), new Float(25f));
        zLevel.put(new Integer(6), new Float(33f));
        zLevel.put(new Integer(7), new Float(37f));
        zLevel.put(new Integer(8), new Float(40f));
        epsilon = (float) distance(new LatLng(0, 0), new LatLng(stepLat, stepLng));
		for(int i = 0,length = curveThreadCalculators.length; i < length; ++i)
			curveThreadCalculators[i] = new CurveThreadCalculator();
    }

    public CurveMultyThreadService()
    {
        init();
    }

    public CurveMultyThreadService(List<CityShort> cityList)
    {
        init();
        this.cityList = cityList;
    }

	public synchronized Float getZLevel(Integer mapZoom)
	{
		return zLevel.get(mapZoom);
	}

	@Override
	public synchronized Curve getRawCurve()
	{
		if(curveIterator.hasNext())
			return curveIterator.next();
		else
			return null;
	}

	@Override
	public void dropDb()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

    

    private class CityZPredicate implements ICityPredicate
    {

        @Override
        public boolean compare(CityShort city1, CityShort city2, float zLevel)
        {
            float distanceBetween = (float) distance(new LatLng(city1.getLatitude(), city1.getLongitude()), new LatLng(city2.getLatitude(),
                    city2.getLongitude()))
                    / zRadiusConst;
            float cityRadius = city1.getWeight() / zLevel;
            float cityToAddRadius = city2.getWeight() / zLevel;
            if (distanceBetween < (cityRadius + cityToAddRadius))
            {
                return true;
            }
            return false;
        }
    }

    protected Collection<Curve> groupCities(float zLevel, ICityPredicate predicate)
    {
        Map<CityShort, Integer> idToColor = new HashMap();
        int color = 0;
        int lastColor = -1;
        for (int i = 0, listSize = cityList.size(); i < listSize - 1; ++i)
        {
            CityShort city = cityList.get(i);
            if (idToColor.containsKey(city))
            {
                color = idToColor.get(city);
                for (int j = i + 1; j < listSize; ++j)
                {
                    CityShort cityToAdd = cityList.get(j);
                    if (predicate.compare(city, cityToAdd, zLevel))
                    {
                        if (idToColor.containsKey(cityToAdd))
                        {
                            int oldColor = idToColor.get(cityToAdd);

                            Set<CityShort> keys = idToColor.keySet();
                            for (CityShort key : keys)
                            {
                                if (idToColor.get(key) == oldColor)
                                {
                                    idToColor.put(key, color);
                                }
                            }
                        }
                        idToColor.put(cityToAdd, color);
                    }

                }
            }
            else
            {
                color = -1;
                for (int j = i + 1; j < listSize; ++j)
                {
                    CityShort cityToAdd = cityList.get(j);

                    if (predicate.compare(city, cityToAdd, zLevel))
                    {
                        if (idToColor.containsKey(cityToAdd))
                        {
                            if (color == -1)
                            {
                                color = idToColor.get(cityToAdd);
                                idToColor.put(city, color);
                            }
                            else
                            {
                                int oldColor = idToColor.get(cityToAdd);
                                Set<CityShort> keys = idToColor.keySet();
                                for (CityShort key : keys)
                                {
                                    if (idToColor.get(key) == oldColor)
                                    {
                                        idToColor.put(key, color);
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (color == -1)
                            {
                                color = ++lastColor;
                                idToColor.put(city, color);
                            }
                            idToColor.put(cityToAdd, color);
                        }
                    }
                    else
                    {
                        if ((j + 1 == listSize) && (color == -1))
                        {
                            color = ++lastColor;
                            idToColor.put(city, color);
                        }
                    }

                }

            }

        }
        CityShort city = cityList.get(cityList.size() - 1);
        if (!idToColor.containsKey(city))
        {
            color = ++lastColor;
            idToColor.put(city, color);
        }

        Set<CityShort> keys = idToColor.keySet();
        Map<Integer, Curve> curvesMap = new HashMap<Integer, Curve>();
        for (CityShort key : keys)
        {
            color = idToColor.get(key);
            if (curvesMap.containsKey(color))
            {
                curvesMap.get(color).getCityList().add(key);
            }
            else
            {
                Curve curve = new Curve();
                curve.getCityList().add(key);
                curvesMap.put(color, curve);
            }
        }
        return curvesMap.values();
    }


    public Collection<Curve> getCurves(IApplicationContext appContext)
    {
        Collection<Curve> results = curveMongoDao.getCurves(appContext.getHash());

        if (results != null)
        {
            return results;
        }

        this.cityList = userCityService.getCityList(appContext);
		if(cityList == null || cityList.size() == 0)
            return null;
		
		int mapZoom = appContext.getMapZoom().getValue();
		float zLevelValue = zLevel.get(mapZoom);
		curves = groupCities(zLevel.get(mapZoom), new CityZPredicate());
		curveIterator = curves.iterator();
		Thread[] threads = new Thread[curveThreadCalculators.length];
		for(int i = 0; i < curveThreadCalculators.length; ++i)
		{
			curveThreadCalculators[i].init(stepLat, stepLng, zLevel.get(mapZoom).floatValue(), this);
			Thread thread = new Thread(curveThreadCalculators[i]);
			thread.start();
			threads[i] = thread;
		}
		for(Thread thread : threads)
		{
			try
			{
				thread.join();
			}
			catch (InterruptedException ex)
			{
				Logger.getLogger(CurveMultyThreadService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

        curveMongoDao.addCurves(appContext.getHash(), results);

        return curves;
    }
}

