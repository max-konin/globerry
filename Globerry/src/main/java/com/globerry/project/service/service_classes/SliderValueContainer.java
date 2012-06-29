/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;

/**
 * 
 * @author Ed
 */
public class SliderValueContainer implements ISlider {

    float left, right;
    int id;
    @Override
    public float getLeftValue() {
        return left;
    }

    @Override
    public float getRightValue() {
        return right;
    }

    @Override
    public void setLeftValue(float value) {
        this.left = value;
    }

    @Override
    public void setRightValue(float value) {
        this.right = value;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public void setValues(IGuiComponent component) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported for this class.");
    }
    
    @Override
    public String toString() {
        return "Slider container. Left: " + left + " Right: " + right;
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
}
