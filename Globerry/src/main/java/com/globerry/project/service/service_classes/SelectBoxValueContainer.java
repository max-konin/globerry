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
public class SelectBoxValueContainer implements ISelectBox {

    int value;
    int id;
    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
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
        throw new UnsupportedOperationException("Not supported for this element");
    }
    
    @Override
    public String toString() {
        return "Select container: value " + value;
    }
    
    
}
