package com.globerry.project.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.globerry.project.domain.PropertyType;

public class SliderTest
{
    @Test
    public void testInit(){
	PropertyType type = new PropertyType();
	Slider slider = new Slider(type );
	if (slider.getType() != type)
	    fail();
    }
    @Test
    public void testState(){
	PropertyType type = new PropertyType();
	type.setName("test");
	type.setMinValue(0);
	type.setMaxValue(10);
	Slider slider = new Slider(type );
	slider.onChange(-1, 50);
	slider.onChange(6, 5);
	if (slider.getStateLeft() != 0 || slider.getStateRight() != 10)
	    fail((new Float(slider.getStateLeft()).toString())+" "+(new Float(slider.getStateRight()).toString()));
    }

}
