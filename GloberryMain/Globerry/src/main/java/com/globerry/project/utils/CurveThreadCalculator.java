/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.utils;

import com.globerry.project.domain.CityShort;
import com.globerry.project.domain.Curve;
import com.globerry.project.domain.LatLng;
import com.globerry.project.service.CurveMultyThreadService;
import com.globerry.project.service.interfaces.ICurveService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.globerry.project.utils.GeoTools.*;

/**
 *
 * @author Max
 */
public class CurveThreadCalculator implements Runnable
{

	private float stepLat = 0.2f;
	private float stepLng = 0.2f;
	private float zLevel = 30;
	private ICurveService curveService;
	private Curve curve;

	public CurveThreadCalculator()
	{
	}

	public void init(float stepLat, float stepLng, float zLevel,ICurveService curveService)
	{
		this.stepLat = stepLat;
		this.stepLng = stepLng;
		this.zLevel = zLevel;
		this.curveService = curveService;
	}
	private static final int zRadiusConst = 1000000;

	/**
	 * @return the curve
	 */
	public Curve getCurve()
	{
		return curve;
	}

	/**
	 * @param curve the curve to set
	 */
	public void setCurve(Curve curve)
	{
		this.curve = curve;
	}

	enum Direction
	{

		Up, Right, Down, Left, NoDirection
	}

	@Override
	public void run()
	{
		while (true)
		{
			curve = curveService.getRawCurve();
			if (curve == null)
			{
				break;
			}
			calcCurve();
		}

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

	public Curve calcCurve()
	{

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

		float zlevelValue = zLevel;
		// делим города на кривулины, затем будем брать 1 город из каждой
		// кривулины




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

		return curve;
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
}
