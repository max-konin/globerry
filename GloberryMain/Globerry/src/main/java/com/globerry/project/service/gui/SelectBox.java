/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

import java.util.*;
import org.dom4j.IllegalAddException;

/**
 *
 * @author Ed
 */
public class SelectBox implements ISelectBox {

    ArrayList<Integer> values;
    int id, currentIndex;
    
    public SelectBox(SelectBox selectBox)
    {
        this.values = (ArrayList<Integer>)selectBox.getOptionAvaliable().clone();
        this.id = selectBox.getId();
        this.currentIndex = selectBox.getCurrentIndex();
    }
    
    public SelectBox(int id, Collection<Integer> initialValues) 
    {
        this.id = id;
        this.currentIndex = 0;
        values = new ArrayList<Integer>(initialValues);
    }
    
    public SelectBox(int id) {
        this.id = id;
        this.currentIndex = 0;
        values = new ArrayList<Integer>();
    }
    @Override
    public IGuiComponent clone()
    {
        return new SelectBox(this);
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
    public void setValues(IGuiComponent component) throws IllegalArgumentException {
        try {
            ISelectBox selectBox = (ISelectBox) component;
            setValue(selectBox.getValue());
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Not instance of select box " + e.getMessage());
        }
    }

    public ArrayList<Integer> getOptionAvaliable() {return values;}
    
    public int getCurrentIndex() {
        return currentIndex;
    }

    

    @Override
    public int getValue() {
        return values.get(currentIndex);
    }
    
    @Override
    public String toString() {
        return "Select value " + values.get(currentIndex) + ", current index " + currentIndex;
    }
    
}
