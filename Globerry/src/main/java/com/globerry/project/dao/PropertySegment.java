package com.globerry.project.dao;

import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;

public class PropertySegment
{
    private PropertyType propertyType;
    private float minValue;
    private float maxValue;
    public PropertySegment(Property property){
	this.propertyType = property.getPropertyType();
	this.setValue(property.getValue());
    }
    public PropertyType getPropertyType()
    {
	return propertyType;
    }
    public void setPropertyType(PropertyType propertyType)
    {
	this.propertyType = propertyType;
    }
    public float getMinValue()
    {
	return minValue;
    }
    public void setMinValue(float minValue)
    {
	this.minValue = minValue;
    }
    public float getMaxValue()
    {
	return maxValue;
    }
    public void setMaxValue(float maxValue)
    {
	this.maxValue = maxValue;
    }
    public void setValue(float Value)
    {
	//TODO for single slider
    }
}
