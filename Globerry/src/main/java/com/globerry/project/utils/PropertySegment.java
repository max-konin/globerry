package com.globerry.project.utils;

import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;

/*
     * @author max
     * Вообще на самом деле не вижу надобности в данном классе. 
     */
public class PropertySegment
{
    private PropertyType propertyType;
    private float minValue;
    private float maxValue;
    public PropertySegment(Property property){
	this.propertyType = property.getPropertyType();
	this.setValue(property.getValue());
    }
    /*
     * @author max
     * Сомнительный метод, учитывая то, что в PropertyType есть мин и макс значения
     */
    public PropertySegment(PropertyType propertyType, float minValue, float maxValue){
	this.propertyType = propertyType;
	this.minValue = minValue;
	this.maxValue = maxValue;
    }
    /*
     * @author max
     * Написал аналог того что выше.
     */
    public PropertySegment(PropertyType propertyType){
	this.propertyType = propertyType;
	this.minValue = propertyType.getMinValue();
	this.maxValue = propertyType.getMaxValue();
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
