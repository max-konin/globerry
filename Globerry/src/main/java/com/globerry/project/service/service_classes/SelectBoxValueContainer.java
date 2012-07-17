/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISelectBox;

/**
 * Этот класс нужен чтобы хранить  значения от селектов от клиента. Целостность данных в нем не гарантируется.
 * @author Ed
 */
public class SelectBoxValueContainer implements ISelectBox{

    int value;
    int id;
    
    public void setValue(Integer value) {
        this.value = value;
    }
   
    public void setValue(int value) {
        this.value = value;
    }
   
    public int getValue() {
        return value;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
   
    public String toString() {
        return "Select container: value " + value;
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
    
}
