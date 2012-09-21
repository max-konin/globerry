/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service;

import com.globerry.project.domain.City;
import com.globerry.project.service.interfaces.ICurveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import nl.cloudfarming.client.lib.geotools.GeometryTools;


import com.globerry.project.domain.LatLng;
import org.springframework.stereotype.Service;

/**
 *
 * @author signal
 */
@Service
public class CurveService implements ICurveService {

	private static final int zRadiusConst = 1000000;

	enum Direction {

		Up, Right, Down, Left, NoDirection
	}

	private native List<String> calculateCurve(List<City> cities);
	private List<City> cityList;
	private Map<Integer, Float> zLevel;

	@Override
	public void dropDb() {
		//curveDao.dropDB();
	}

	@Override
	public List<String> getCurve(Long paramsHash, List<City> cities) {
		//List<String> curves = curveDao.getCurve(paramsHash);
		//if (curves.isEmpty())
		//{
		//    List<String> newCurves = calculateCurve(cities);
		// curveDao.addCurve(paramsHash.toString(), newCurves);
		//    return newCurves;
		//}
		//return curves;
		return null;
	}

	public CurveService() {
		zLevel = new HashMap<Integer, Float>();
		zLevel.put(new Integer(2), new Float(14f));
		zLevel.put(new Integer(3), new Float(18f));
		zLevel.put(new Integer(4), new Float(22f));
		zLevel.put(new Integer(5), new Float(25f));
		zLevel.put(new Integer(6), new Float(33f));
		zLevel.put(new Integer(7), new Float(37f));
		zLevel.put(new Integer(8), new Float(40f));
	}
	private final static float stepLat = 0.6f;
	private final static float stepLng = 0.6f;

	private void ExcludeCurveCityInList(List<LatLng> curve, List<City> cityList) {
		if (curve.size() == 0) {
			return;
		}
		if (cityList.size() == 0) {
			return;
		}
		float influence = 0.5f;
		LatLng startPoint = curve.get(0);
		Iterator<City> it = cityList.listIterator();
		do {
			City city = it.next();
			float lng0 = city.getLongitude(), lat0 = city.getLatitude(), lng1, lng2, lat1, lat2, angle = 0;
			for (int i = 0, k = curve.size(); i < k; ++i) {
				LatLng point = curve.get(i);
				lng1 = point.lng;
				lat1 = point.lat;
				if (i + 1 == curve.size()) {
					lng2 = startPoint.lng;
					lat2 = startPoint.lat;
				} else {
					LatLng point1 = curve.get(i + 1);
					lng2 = point1.lng;
					lat2 = point1.lat;
				}

				float cos = ((lng1 - lng0) * (lng2 - lng0) + (lat1 - lat0) * (lat2 - lat0)) / (float) (Math.hypot(lng1 - lng0, lat1 - lat0) * Math.hypot(lng2 - lng0, lat2 - lat0));
				if (cos > 1) {
					cos = 1;
				}
				angle += Math.signum((lng1 - lng0) * (lat2 - lat0) - (lng2 - lng0) * (lat1 - lat0)) * Math.acos(cos) * 180 / Math.PI;
			}
			angle = Math.abs(angle);
			if (Math.abs(angle - 360) < influence * 180 / Math.PI) {
				it.remove();
			}
		} while (it.hasNext());
	}

	private void groupCities(float zLevel) {
		List<City> cityListCopy = new ArrayList<City>(cityList);
		Iterator<City> it = cityListCopy.iterator();
		List<City> curve = new ArrayList<City>();
		do {
			do {
				City city = it.next();
				City cityToAdd = it.next();
				float distanceBetween = (float) GeometryTools.getDistance(city.getLatitude(), city.getLongitude(),
						cityToAdd.getLatitude(), cityToAdd.getLongitude()) / zRadiusConst;
				float cityRadius = city.getWeight() / zLevel;
				float cityToAddRadius = cityToAdd.getWeight() / zLevel;
				if (distanceBetween < (cityRadius + cityToAddRadius)) {
					curve.add(cityToAdd);
					it.remove();
				}

			} while (it.hasNext());
		} while (it.hasNext());
	}

	public List<List<LatLng>> calcCurves(int mapZoom) {

		// город
		City city;

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

		List<City> remainingCities = new ArrayList<City>(cityList);

		List<List<LatLng>> curves = new ArrayList<List<LatLng>>();

		float zlevelValue = zLevel.get(new Integer(mapZoom)).floatValue();

		while (remainingCities.size() != 0) {
			List<LatLng> returnPoints = new ArrayList<LatLng>();
			city = remainingCities.remove(0);

			// направление выбора квадрата 0-вверх 1-вправо 2-вниз 3-влево
			Direction direction = Direction.NoDirection;

			LatLng start = new LatLng((float) Math.floor(city.getLatitude() / stepLat) * stepLat, (float) Math.floor(city.getLongitude() / stepLng) * stepLng);
			LatLng center = new LatLng(start.lat, start.lng);
			// массив вершин квадрата
			LatLng[] squarePoints = {new LatLng(center.lat, center.lng), new LatLng(center.lat, center.lng + stepLng),
				new LatLng(center.lat - stepLat, center.lng + stepLng), new LatLng(center.lat - stepLat, center.lng)};

			exitCode = 0;
			while (exitCode == 0) {
				c++;
				if (c > 2000) {
					exitCode = 1;
					c = 0;
					continue;
				}
				int count = 0;
				for (int i = 0; i < 4; ++i) {
					if (Z(squarePoints[i]) > zlevelValue) {
						buffer[count] = i;
						++count;
					} else {
						buffer2 = i;
					}
				}
				// в зависимости от того сколько точек попало в срез поля и то
				// какие они определяется какие точки попадут в returnPoints
				switch (count) {
					case 0:
						exitCode = -1;
						break;
					case 1:
						if (buffer[0] == 0) {
							pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
							pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
							direction = Direction.Left;
						} else if (buffer[0] == 1) {
							pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
							pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
							direction = Direction.Up;
						} else if (buffer[0] == 2) {
							pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
							pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
							direction = Direction.Right;
						} else if (buffer[0] == 3) {
							pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
							pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
							direction = Direction.Down;
						}
						break;
					case 2:
						if (buffer[0] == 0 && buffer[1] == 1) {
							pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
							pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
							direction = Direction.Left;
						} else if (buffer[0] == 1 && buffer[1] == 2) {
							pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
							pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
							direction = Direction.Up;
						} else if (buffer[0] == 2 && buffer[1] == 3) {
							pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
							pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
							direction = Direction.Right;
						} else if (buffer[0] == 0 && buffer[1] == 3) {
							pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
							pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
							direction = Direction.Down;
						} else if (buffer[0] == 0 && buffer[1] == 2) {
							if (direction == Direction.Up) {
								pointLeft = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
								pointRight = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
								direction = Direction.Right;
							} else if (direction == Direction.Down) {
								pointLeft = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
								pointRight = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
								direction = Direction.Left;
							}
						} else if (buffer[0] == 1 && buffer[1] == 3) {
							if (direction == Direction.Left) {
								pointLeft = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
								pointRight = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
								direction = Direction.Up;
							} else if (direction == Direction.Right) {
								pointLeft = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
								pointRight = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
								direction = Direction.Down;
							} else if (direction == Direction.NoDirection) {
								pointLeft = null;
								pointRight = null;
								direction = Direction.Down;
							}
						}
						break;
					case 3:
						if (buffer2 == 0) {
							pointLeft = new LatLng(squarePoints[0].lat - stepLat / 2, squarePoints[0].lng);
							pointRight = new LatLng(squarePoints[0].lat, squarePoints[0].lng + stepLng / 2);
							direction = Direction.Up;
						} else if (buffer2 == 1) {
							pointLeft = new LatLng(squarePoints[1].lat, squarePoints[1].lng - stepLng / 2);
							pointRight = new LatLng(squarePoints[1].lat - stepLat / 2, squarePoints[1].lng);
							direction = Direction.Right;
						} else if (buffer2 == 2) {
							pointRight = new LatLng(squarePoints[2].lat, squarePoints[2].lng - stepLng / 2);
							pointLeft = new LatLng(squarePoints[2].lat + stepLat / 2, squarePoints[2].lng);
							direction = Direction.Down;
						} else if (buffer2 == 3) {
							pointRight = new LatLng(squarePoints[3].lat + stepLat / 2, squarePoints[3].lng);
							pointLeft = new LatLng(squarePoints[3].lat, squarePoints[3].lng + stepLng / 2);
							direction = Direction.Left;
						}
						break;
					case 4:
						direction = Direction.Up;
						break;
				}
				float distance = 0;
				if (returnPoints.size() != 0) {
					LatLng startPoint = returnPoints.get(0);
					distance = (float) GeometryTools.getDistance(startPoint.lat, startPoint.lng, pointRight.lat, pointRight.lng);
				}
				if (count != 4 && count != 0) {
					if (returnPoints.size() == 0 && pointLeft != null) {
						returnPoints.add(pointLeft);
					} else if (distance < epsilon) {
						exitCode = 1;
						continue;
					}
					if (pointRight != null) {
						returnPoints.add(pointRight);
					}
				}
				// выбор направления движения
				switch (direction) {
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
				//squarePoints
				squarePoints[0] = new LatLng(center.lat, center.lng);
				squarePoints[1] = new LatLng(center.lat, center.lng + stepLng);
				squarePoints[2] = new LatLng(center.lat - stepLat, center.lng + stepLng);
				squarePoints[3] = new LatLng(center.lat - stepLat, center.lng);
			}
			ExcludeCurveCityInList(returnPoints, remainingCities);
			curves.add(returnPoints);
		}

		return curves;
	}

	// point = LatLng
	private float Z(LatLng point) {
		City city;
		float buffer = 0;
		float maxBuffer = 0;
		for (int i = 0; i < cityList.size(); i++) {
			city = cityList.get(i);
			buffer = city.getWeight() / (float) GeometryTools.getDistance(city.getLatitude(), city.getLongitude(), point.lat, point.lng);
			if (maxBuffer < buffer) {
				maxBuffer = buffer;
			}
		}
		return maxBuffer * zRadiusConst;
	}

	public void updateCityList(List<City> cityList) {
		this.cityList = cityList;
	}
}
