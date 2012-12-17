/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.dao.ICurveDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;
import com.globerry.project.service.interfaces.ICurveService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import nl.cloudfarming.client.lib.geotools.GeometryTools;

import com.globerry.project.domain.LatLng;
import com.globerry.project.service.interfaces.IUserCityService;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import java.lang.IllegalArgumentException;

/**
 *
 * @author signal
 */
@Service
@Scope("session")
public class CurveService implements ICurveService
{

    private static final int zRadiusConst = 1000000;

    enum Direction
    {

        Up, Right, Down, Left, NoDirection
    }

    private static float stepLat = 0.2f;
    private static float stepLng = 0.2f;
    private float epsilon;
	
	@Override
	public void setStepLat(float stepLat) {
		CurveService.stepLat = stepLat;
	}

	@Override
	public void setStepLng(float stepLng) {
		CurveService.stepLng = stepLng;
	}
	
	@Override
	public float getStepLat() {
		return CurveService.stepLat;
	}

	@Override
	public float getStepLng() {
		return CurveService.stepLng;
	}
	
	
    private native List<String> calculateCurve(List<CityShort> cities);
    private List<CityShort> cityList;
    private Map<Integer, Float> zLevel;
    @Autowired
    private ICurveDao curveMongoDao;
    @Autowired
    private IUserCityService userCityService;

    @Override
    public void dropDb()
    {
        // curveDao.dropDB();
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
    }

    public CurveService()
    {
        init();
    }

    public CurveService(List<CityShort> cityList)
    {
        init();
        this.cityList = cityList;
    }

    private boolean isInCurve(List<LatLng> curve, CityShort city) throws IllegalArgumentException
    {
        if (curve.size() == 0)
        {
            throw new IllegalArgumentException("Curve Points have size 0");
        }
        float influence = 0.5f;
        LatLng startPoint = curve.get(0);
        float lng0 = city.getLongitude(), lat0 = city.getLatitude(), lng1, lng2, lat1, lat2, angle = 0;
        for (int i = 0, k = curve.size(); i < k; ++i)
        {
            LatLng point = curve.get(i);
            lng1 = point.lng;
            lat1 = point.lat;
            if (i + 1 == curve.size())
            {
                lng2 = startPoint.lng;
                lat2 = startPoint.lat;
            }
            else
            {
                LatLng point1 = curve.get(i + 1);
                lng2 = point1.lng;
                lat2 = point1.lat;
            }

            float cos = ((lng1 - lng0) * (lng2 - lng0) + (lat1 - lat0) * (lat2 - lat0))
                    / (float) (Math.hypot(lng1 - lng0, lat1 - lat0) * Math.hypot(lng2 - lng0, lat2 - lat0));
            if (cos > 1)
            {
                cos = 1;
            }
            angle += Math.signum((lng1 - lng0) * (lat2 - lat0) - (lng2 - lng0) * (lat1 - lat0)) * Math.acos(cos) * 180 / Math.PI;
        }
        angle = Math.abs(angle);
        if (Math.abs(angle - 360) < influence * 180 / Math.PI)
        {
            return true;
        }
        return false;
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

    public Collection<Curve> calcCurves(int mapZoom)
    {
        if(cityList == null || cityList.size() == 0)
            return null;
        // город
        CityShort city;

        // Массив куда складываются номера вершин квадрата, которые попали в
        // срез поля
        int buffer[] = new int[4];
        // точка которая не попала срез(используется для count == 3)
        int buffer2 = -1;
        // точки на квадрате по которым строится кривулина, попадают в массив
        // returnPoints, отсчет право-лево по часовой стрелке
        LatLng pointLeft = null;
        LatLng pointRight = null;

        float epsilon = stepLat * 17717;

        int exitCode = 0;
        int c = 0;

        float zlevelValue = zLevel.get(new Integer(mapZoom)).floatValue();
        // делим города на кривулины, затем будем брать 1 город из каждой
        // кривулины
        Collection<Curve> curves = groupCities(zLevel.get(mapZoom), new CityZPredicate());

        

        for (Curve curve : curves)
        {
            List<LatLng> returnPoints = new ArrayList<LatLng>();
            city = curve.getCityList().get(0);

            Direction defaultDirection = Direction.Up;
            // направление выбора квадрата
            Direction direction = Direction.NoDirection;

            LatLng start = new LatLng((float) Math.floor(city.getLatitude() / stepLat) * stepLat, (float) Math.floor(city.getLongitude()
                    / stepLng)
                    * stepLng);
            LatLng center = new LatLng(start.lat, start.lng);
            // массив вершин квадрата
            LatLng[] squarePoints =
            {
                new LatLng(center.lat, center.lng), new LatLng(center.lat, center.lng + stepLng),
                new LatLng(center.lat - stepLat, center.lng + stepLng), new LatLng(center.lat - stepLat, center.lng)
            };

            exitCode = 0;
            while (exitCode == 0)
            {
                c++;
                if (c > 30000)
                {
                    exitCode = 1;
                    c = 0;
                    continue;
                }
                int count = 0;
                for (int i = 0; i < 4; ++i)
                {
                    if (Z(squarePoints[i], curve.getCityList()) > zlevelValue)
                    {
                        buffer[count] = i;
                        ++count;
                    }
                    else
                    {
                        buffer2 = i;
                    }
                }
                // в зависимости от того сколько точек попало в срез поля и то
                // какие они определяется какие точки попадут в returnPoints
                switch (count)
                {
                    case 0:
                        exitCode = -1;
                        break;
                    case 1:
                        if (buffer[0] == 0)
                        {
                            pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
                            pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
                            direction = Direction.Left;
                        }
                        else if (buffer[0] == 1)
                        {
                            pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
                            pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
                            direction = Direction.Up;
                        }
                        else if (buffer[0] == 2)
                        {
                            pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
                            pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
                            direction = Direction.Right;
                        }
                        else if (buffer[0] == 3)
                        {
                            pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
                            pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
                            direction = Direction.Down;
                        }
                        break;
                    case 2:
                        if (buffer[0] == 0 && buffer[1] == 1)
                        {
                            pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
                            pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
                            direction = Direction.Left;
                        }
                        else if (buffer[0] == 1 && buffer[1] == 2)
                        {
                            pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
                            pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
                            direction = Direction.Up;
                        }
                        else if (buffer[0] == 2 && buffer[1] == 3)
                        {
                            pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
                            pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
                            direction = Direction.Right;
                        }
                        else if (buffer[0] == 0 && buffer[1] == 3)
                        {
                            pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
                            pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
                            direction = Direction.Down;
                        }
                        else if (buffer[0] == 0 && buffer[1] == 2)
                        {
                            if (direction == Direction.Up)
                            {
                                pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
                                pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
                                direction = Direction.Right;
                            }
                            else if (direction == Direction.Down)
                            {
                                pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
                                pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
                                direction = Direction.Left;
                            }
                        }
                        else if (buffer[0] == 1 && buffer[1] == 3)
                        {
                            if (direction == Direction.Left)
                            {
                                pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
                                pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
                                direction = Direction.Up;
                            }
                            else if (direction == Direction.Right)
                            {
                                pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
                                pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
                                direction = Direction.Down;
                            }
                            else if (direction == Direction.NoDirection)
                            {
                                pointLeft = null;
                                pointRight = null;
                                direction = Direction.Down;
                            }
                        }
                        break;
                    case 3:
                        if (buffer2 == 0)
                        {
                            pointLeft = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
                            pointRight = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
                            direction = Direction.Up;
                        }
                        else if (buffer2 == 1)
                        {
                            pointLeft = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
                            pointRight = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
                            direction = Direction.Right;
                        }
                        else if (buffer2 == 2)
                        {
                            pointRight = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
                            pointLeft = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
                            direction = Direction.Down;
                        }
                        else if (buffer2 == 3)
                        {
                            pointRight = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
                            pointLeft = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
                            direction = Direction.Left;
                        }
                        break;
                    case 4:
                        direction = defaultDirection;
                        break;
                }
                float distance = 0;
                if (returnPoints.size() != 0)
                {
                    LatLng startPoint = returnPoints.get(0);
                    distance = (float) distance(new LatLng(startPoint.lat, startPoint.lng), new LatLng(pointRight.lat, pointRight.lng));
                }
                if (count != 4 && count != 0)
                {
                    if (returnPoints.size() == 0 && pointLeft != null)
                    {
                        returnPoints.add(pointLeft);
                    }
                    else if (distance < epsilon)
                    {
                        try
                        {
                            if (this.isInCurve(returnPoints, city))
                            {
                                exitCode = 1;
                                continue;
                            }
                            else
                            {
                                returnPoints.clear();
                                center = new LatLng(start.lat, start.lng);
                                pointRight = null;
                                pointLeft = null;
                                defaultDirection = Direction.Down;
                            }
                        }
                        catch (IllegalArgumentException e)
                        {
                            //TODO
                        }
                        
                    }
                    if (pointRight != null)
                    {
                        returnPoints.add(pointRight);
                    }
                }
                // выбор направления движения
                switch (direction)
                {
                    case Up:
                        center.lat += stepLat;
                        break;
                    case Right:
                        center.lng += stepLng;
                        break;
                    case Down:
                        center.lat -= stepLat;
                        break;
                    case Left:
                        center.lng -= stepLng;
                        break;
                }
                // squarePoints
                squarePoints[0] = new LatLng(center.lat, center.lng);
                squarePoints[1] = new LatLng(center.lat, center.lng + stepLng);
                squarePoints[2] = new LatLng(center.lat - stepLat, center.lng + stepLng);
                squarePoints[3] = new LatLng(center.lat - stepLat, center.lng);
            }
            curve.setPoints(returnPoints);
            returnPoints = new ArrayList<LatLng>();
        }

        return curves;
    }

    // point = LatLng
     private float Z(LatLng point, List<CityShort> cityList)
    {
        float buffer = 0;
        float maxBuffer = 0;
        for (CityShort city : cityList)
        {
            buffer = city.getWeight() / (float) distance(new LatLng(city.getLatitude(), city.getLongitude()), point);
            if (maxBuffer < buffer)
            {
                maxBuffer = buffer;
            }
        }
        return maxBuffer * zRadiusConst;
    }

    private double distance(LatLng p1, LatLng p2)
    {
        int Rad = 6378137;
        double d2r = Math.PI / 180;
        double dLat = (p1.lat - p2.lat) * d2r;
        double dLng = (p1.lng - p2.lng) * d2r;
        double latL = p2.lat * d2r;
        double latR = p1.lat * d2r;
        double sinL = Math.sin(dLat / 2);
        double sinR = Math.sin(dLng / 2);
        double a = sinL * sinL + sinR * sinR * Math.cos(latL) * Math.cos(latR);

        return Rad * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public Collection<Curve> getCurves(IApplicationContext appContext)
    {
        Collection<Curve> results = curveMongoDao.getCurves(appContext.getHash());

        if (results != null)
        {
            return results;
        }

        this.cityList = userCityService.getCityList(appContext);
        results = calcCurves(appContext.getMapZoom().getValue());

        curveMongoDao.addCurves(appContext.getHash(), results);

        return results;
    }
}
