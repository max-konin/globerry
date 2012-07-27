package com.globerry.project.utils;

import com.globerry.project.domain.Interval;
import com.globerry.project.domain.PropertyType;

/**
 * @author max Вообще на самом деле не вижу надобности в данном классе.
 */
public class PropertySegment
{
    private PropertyType propertyType;
    private Interval interval = new Interval();

    public PropertySegment(PropertyType propertyType, int leftValue, int rightValue)
    {
	this.propertyType = propertyType;
	interval.setLeft(leftValue);
	interval.setRight(rightValue);
    }

    public PropertySegment(PropertyType propertyType)
    {
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
    public int getLeftValue()
    {
	return interval.getLeft();
    }

    /**
     * @param leftValue
     *            the leftValue to set
     */
    public void setLeftValue(int leftValue)
    {
	interval.setLeft(leftValue);
    }

    /**
     * @return the rightValue
     */
    public int getRightValue()
    {
	return interval.getRight();
    }

    /**
     * @param rightValue
     *            the rightValue to set
     */
    public void setRightValue(int rightValue)
    {
	interval.setRight(rightValue);
    }

    public Interval getInterval()
    {
        return interval;
    }

    public void setInterval(Interval interval)
    {
        this.interval = interval;
    }

    public String toString()
    {
	return String.format("Type: %s    LeftValue: %4.1f    RightValue: %4.1f\n", propertyType.getName(), 
                                                                                    getLeftValue(), 
                                                                                    getRightValue());
    }

}
