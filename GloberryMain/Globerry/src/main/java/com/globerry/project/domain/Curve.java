package com.globerry.project.domain;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2f;

public class Curve implements Cachable<Curve>
{
    private List<CityShort> cityList;
    private List<LatLng> points;   
    
    
    public Curve()
    {
	this.cityList = new ArrayList();
	this.points = new ArrayList<LatLng>();
    }

    public Curve(List<CityShort> cityList, List<LatLng> points)
    {
	this.cityList = cityList;
	this.points = points;
    }

    public List<LatLng> getPoints()
    {
	return points;
    }

    public void setPoints(List<LatLng> points)
    {
	this.points = points;
    }

    public List<CityShort> getCityList()
    {
	return cityList;
    }

    public void setCityList(List<CityShort> cityList)
    {
	this.cityList = cityList;
    }

	@Override
	public Curve fromJSON(String json) {
		this.cityList.clear();
		this.points.clear();
		String cityLS = json.substring(json.indexOf("[") + 1, json.indexOf("]"));
		String pointsS = json.substring(json.lastIndexOf("[") + 1, json.lastIndexOf("]"));
		String[] citiesS = cityLS.split("[}],[{]");
		String[] pointsMS = pointsS.split("[}],[{]");
		if(!citiesS[0].isEmpty())
			for(String cityS : citiesS) {
				this.cityList.add((new CityShort()).fromJSON(cityS));
			}
		if(!pointsMS[0].isEmpty())
			for(String pointS : pointsMS) {
				this.points.add((new LatLng()).fromJSON(pointS));
			}
		return this;
	}

	@Override
	public String toJSON() {
		StringBuilder ret = new StringBuilder("{\"cityList\":[");
		for(CityShort city : cityList) {
			ret.append(city.toJSON());
                        ret.append(",");
		}
		if(!cityList.isEmpty())
			ret.deleteCharAt(ret.length() - 1);
		ret.append("],\"points\":[");
		for(LatLng latlng : points) {
			ret.append(latlng.toJSON());
                        ret.append(",");
		}
		if(!points.isEmpty())
			ret.deleteCharAt(ret.length() - 1);
		ret.append("]}");
		return ret.toString();
	}
	
}
