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
    private float leftValue;
    private float rightValue;
    
    
    public PropertySegment(PropertyType propertyType, float leftValue, float rightValue){
	this.propertyType = propertyType;
	this.leftValue = leftValue;
	this.rightValue = rightValue;
    }
    
    public PropertySegment(PropertyType propertyType){
	this(propertyType, propertyType.getMinValue(), propertyType.getMaxValue());
    }
    
    public PropertyType getPropertyType()
    {
	return propertyType;
    }
    public void setPropertyType(PropertyType propertyType)
    {
	this.propertyType = propertyType;
    }

    /**
     * @return the leftValue
     */
    public float getLeftValue() {
        return leftValue;
    }

    /**
     * @param leftValue the leftValue to set
     */
    public void setLeftValue(float leftValue) {
        this.leftValue = leftValue;
    }

    /**
     * @return the rightValue
     */
    public float getRightValue() {
        return rightValue;
    }

    /**
     * @param rightValue the rightValue to set
     */
    public void setRightValue(float RightValue) {
        this.rightValue = RightValue;
    }
    public String toString()
    {
        return String.format("Type: %s    LeftValue: %4.1f    RightValue: %4.1f\n", 
                                    propertyType.getName(), leftValue, rightValue);
    }

}
