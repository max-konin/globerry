package com.globerry.project.service;

import java.util.Observable;

import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.ISlider;
import com.globerry.project.utils.PropertySegment;

public class Slider extends Observable implements ISlider
{
    /*
     * @author max
     * Добавил одно поле вмето 3
     * private float minValue;
     * private float maxValue;
     * private PropertyType type;
     */
   
    private PropertySegment propertySegment;
    public Slider(PropertyType type){
        propertySegment = new PropertySegment(type);
	
    }
    @Override
    @Deprecated
    public void onChange(float newState)
    {
	if (!isStateCorrectly(newState))
	    return;
	getPropertySegment().setMinValue(newState);
	super.notifyObservers(new EventUI(this));
    }
    @Deprecated
    public float getMinValue()
    {
	return getPropertySegment().getMinValue();
    }
    public PropertyType getType()
    {
	return getPropertySegment().getPropertyType();
    }
    @Override
    public void onChange(float newStateLeft, float newStateRight)
    {
	if (!(isStateCorrectly(newStateLeft)&&isStateCorrectly(newStateRight)&&(newStateLeft<=newStateRight)))
	    return;
	getPropertySegment().setMinValue(newStateLeft);
	getPropertySegment().setMaxValue(newStateRight);
    }
    public float getStateLeft()
    {
	return getPropertySegment().getMinValue();
    }
    public float getStateRight()
    {
	return getPropertySegment().getMaxValue();
    }
    private boolean isStateCorrectly(float newState){
	return (newState <= getPropertySegment().getPropertyType().getMaxValue()&& newState >= getPropertySegment().getPropertyType().getMinValue())?true:false;
    }

    /**
     * @return the propertySegment
     */
    public PropertySegment getPropertySegment() {
        return propertySegment;
    }

    /**
     * @param propertySegment the propertySegment to set
     */
    public void setPropertySegment(PropertySegment propertySegment) {
        this.propertySegment = propertySegment;
    }

}