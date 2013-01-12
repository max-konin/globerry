/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.utils;

import com.globerry.project.domain.LatLng;

/**
 *
 * @author Max
 */
public class GeoTools
{
	public static double distance(LatLng p1, LatLng p2)
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
	
}
