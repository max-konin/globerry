package com.globerry.project.domain;

public class LatLng {

	public float lat;
	public float lng;

	public LatLng() {
	}

	public LatLng(float latitude, float longitude) {
		lat = latitude;
		lng = longitude;
	}
	
	public String toJSON() {
		return "{\"lat\":" + lat + ",\"lng\":" + lng + "}";
	}
}
