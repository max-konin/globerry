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
    public void сopyValues(IGuiComponent component) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported for this class.");
    }
    
    @Override
    public String toString() {
        return "Slider container. Left: " + left + " Right: " + right;
    }
}
