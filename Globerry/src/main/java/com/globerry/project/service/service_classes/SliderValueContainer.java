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

    float left, right;
    int id;
   
    public float getLeftValue() {
        return left;
    }

   
    public float getRightValue() {
        return right;
    }

   
    public void setLeftValue(float value) {
        this.left = value;
    }

    
    public void setRightValue(float value) {
        this.right = value;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    
    /**
     * Метод для нормальной работы с рефлексией, т.к. Java не счтает типы float и Float одинаковыми.
     * @param value значение левого ползунка
     */
    public void setLeftValue(Float value) {
        this.left = value;
    }
    
    /**
     * Метод для нормальной работы с рефлексией, т.к. Java не счтает типы float и Float одинаковыми.
     * @param value значенте правого ползунка
     */
    public void setRightValue(Float value) {
        this.right = value;
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
