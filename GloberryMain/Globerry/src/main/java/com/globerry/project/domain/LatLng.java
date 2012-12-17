package com.globerry.project.domain;

public class LatLng implements Cachable<LatLng> {

	public float lat;
	public float lng;

	public LatLng() {
	}

	public LatLng(float latitude, float longitude) {
		lat = latitude;
		lng = longitude;
	}
	@Override
	public String toJSON() {
            StringBuilder str = new StringBuilder(40);
            str.append("{\"lat\":");
            str.append(lat);
            str.append(",\"lng\":");
            str.append(lng);
            str.append("}");
            return str.toString();
	}

	@Override
	public LatLng fromJSON(String json) {
		String[] feilds = json.replaceAll("[{}]|\"[a-z]*\":", "").split(",");
		this.lat = Float.parseFloat(feilds[0]);
		this.lng = Float.parseFloat(feilds[1]);
		return this;
	}
	
	
	
}
