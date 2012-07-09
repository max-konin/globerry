package com.globerry.project.service.gui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;





public class SelectBoxTest
{
    private SelectBox selectBox;
    static final int initionalId = 5;
    @Before
    public void ConstructorHasValueTest()
    {
	List<Integer> values = new ArrayList<Integer>();
	values.add(0);
	values.add(4);
	values.add(40000);
	selectBox = new SelectBox(initionalId, values);
	for(int currentValue : values)
	{
	    assert(selectBox.hasValue(currentValue));
	}
	selectBox = new SelectBox(initionalId);
	assert(selectBox.getId()==initionalId);
    }
    @Test
    public void SetAddTest()
    {
	selectBox = new SelectBox(initionalId);
	selectBox.addValue(15);
	selectBox.addValue(150);
	selectBox.addValue(0);
	selectBox.addValue(-10);
	try
	{
	    selectBox.setValue(150);
	}
	catch(IllegalArgumentException e)
	{
	    fail("Added Value not in selectBox");
	}
	
	try
	{
	    selectBox.setValue(1);
	}
	catch(IllegalArgumentException e)
	{
	    
	}
    }

}
