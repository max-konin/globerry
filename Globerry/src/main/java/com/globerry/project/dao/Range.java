package com.globerry.project.dao;
/**
 * 
 * @author Сергей
 * Класс задающий границы прямоугольника на карте
 */
public class Range
{
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    public Range(){
	
    }
    public float getMinX()
    {
	return minX;
    }
    public void setMinX(float minX)
    {
	this.minX = minX;
    }
    public float getMinY()
    {
	return minY;
    }
    public void setMinY(float minY)
    {
	this.minY = minY;
    }
    public float getMaxX()
    {
	return maxX;
    }
    public void setMaxX(float maxX)
    {
	this.maxX = maxX;
    }
    public float getMaxY()
    {
	return maxY;
    }
    public void setMaxY(float maxY)
    {
	this.maxY = maxY;
    }
}
