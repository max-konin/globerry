/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.gui;

import com.globerry.project.domain.PropertyType;
import com.globerry.project.utils.PropertySegment;

/**
 *
 * @author Ed
 */
public interface ISlider extends IGuiComponent{
    
    int getLeftValue();
    
    int getRightValue();
    
    void setLeftValue(int value);
    
    void setRightValue(int value);     
    
    PropertyType getPropertyType();
    
}
