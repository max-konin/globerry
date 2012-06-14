/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

import java.util.*;

/**
 *
 * @author Ed
 */
public class SelectBox implements ISelectBox {

    ArrayList<Integer> values;
    int id, currentIndex;
    
    public SelectBox(int id, Collection<Integer> initialValues) {
        this.id = id;
        this.currentIndex = 0;
        values = new ArrayList<Integer>(initialValues);
    }
    
    public SelectBox(int id) {
        this.id = id;
        this.currentIndex = 0;
        values = new ArrayList<Integer>();
    }
    
    public boolean hasValue(int value) {
        for(int currentValue : values) {
            if(currentValue == value)
                return true;
        }
        return false;
    }
    
    public void addValue(int value) {
       if(hasValue(value)) throw new IllegalArgumentException("Option with such value already exists.");
       values.add(value);
    }
    
    @Override
    public void setValue(int value) {
        int index = values.indexOf(value);
        if(index == -1) throw new IllegalArgumentException("No element with such index.");
        currentIndex = index;
    }
    
    public void setCurrentIndex(int index) {
        if(index >= values.size()) throw new IllegalArgumentException("There is no element with such index.");
        currentIndex = index;
    }
    
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void сopyValues(IGuiComponent component) throws IllegalArgumentException {
        ISelectBox selectBox = (ISelectBox) component;
        if(selectBox == null) throw new IllegalArgumentException("Passed paremeter isn't instance if ISlider.");
    }

    
    public int getCurrentIndex() {
        return currentIndex;
    }

    

    @Override
    public int getValue() {
        return values.get(currentIndex);
    }
    
    

    
}
