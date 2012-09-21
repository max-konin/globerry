package com.globerry.project.service.gui;

import org.apache.log4j.Logger;

import com.globerry.project.service.service_classes.SelectBoxValueContainer;

/**
 * 
 * @author MaxGurenko
 */
public class CheckBox implements ICheckBox
{
    private boolean isChecked;
    private int id;
    protected static final Logger logger = Logger.getLogger(CheckBox.class);
    
    public CheckBox(int id)
    {
	this.id = id;
	this.isChecked = false;
    }
    public CheckBox(int id, boolean isCheked)
    {
	this.id = id;
	this.isChecked = isCheked;
    }
    public CheckBox(CheckBox checkBox)
    {
	this.id = checkBox.getId();
	this.isChecked = checkBox.isChecked();
    }
    public boolean isChecked()
    {
	return isChecked;
    }

    public void setChecked(boolean isChecked)
    {
	this.isChecked = isChecked;
    }
    @Override
    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    @Override
    public void setValues(IGuiComponent component) throws IllegalArgumentException
    {
	try
	{
	    SelectBoxValueContainer checkBox = (SelectBoxValueContainer) component;
	    boolean bool;
	    if(checkBox.getValue() == 1)
		bool = true;
	    else if
	    (checkBox.getValue() == 0)
		bool = false;
	    else
		throw new IllegalArgumentException("Not cute SelectBoxValueContainer value(not 1 or 0)");
	    this.setChecked(bool);
	}
	catch (ClassCastException e)
	{
	    throw new IllegalArgumentException("Not instance of SelectBoxValueContainer " + e.getMessage());
	}
    }
    public IGuiComponent clone()
    {
        return new CheckBox(this);
    }
    @Override
    public String toString() {
        return "isChecked: " + isChecked + "\n Id:" + id;
    }
}
