package com.globerry.project.service;

import java.util.Observable;

import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.ISlider;

public class Slider extends Observable implements ISlider
{
    private float state;
    private float stateSecond;
    private PropertyType type;
    public Slider(PropertyType type){
	this.type = type;
	this.state = type.getMinValue();
	this.stateSecond = type.getMaxValue();
    }
    @Override
    @Deprecated
    public void onChange(float newState)
    {
	if (!isStateCorrectly(newState))
	    return;
	state = newState;
	super.notifyObservers(new EventUI(this));
    }
    @Deprecated
    public float getState()
    {
	return state;
    }
    public PropertyType getType()
    {
	return type;
    }
    @Override
    public void onChange(float newStateLeft, float newStateRight)
    {
	if (!(isStateCorrectly(newStateLeft)&&isStateCorrectly(newStateRight)&&(newStateLeft<=newStateRight)))
	    return;
	state = newStateLeft;
	stateSecond = newStateRight;
    }
    public float getStateLeft()
    {
	return state;
    }
    public float getStateRight()
    {
	// TODO Auto-generated method stub
	return stateSecond;
    }
    private boolean isStateCorrectly(float newState){
	return (newState <= type.getMaxValue()&& newState >= type.getMinValue())?true:false;
    }

}