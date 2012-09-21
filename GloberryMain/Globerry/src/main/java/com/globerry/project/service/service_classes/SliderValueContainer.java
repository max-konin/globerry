/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;

/**
 * 
 * @author Ed
 */
public class SliderValueContainer implements ISlider {

    int left, right;
    int id;
   
    public int getLeftValue() {
        return left;
    }

   
    public int getRightValue() {
        return right;
    }

   
    public void setLeftValue(int value) {
        this.left = value;
    }

    
    public void setRightValue(int value) {
        this.right = value;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
   
    public void setLeftValue(Integer value) {
        this.left = value;
    }
    
    public void setRightValue(Integer value) {
        this.right = value;
    }

    @Override
    public void setValues(IGuiComponent component) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IGuiComponent clone()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PropertyType getPropertyType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
