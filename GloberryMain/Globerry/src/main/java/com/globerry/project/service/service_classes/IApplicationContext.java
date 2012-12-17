/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.CheckBox;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;
import org.geotools.util.DateRange;

/**
 *
 * @author Ed
 * Интерфейс доступа к данным контекста приложения. Содержит в себе методы для обращешия ко всем элементам GUI, а также
 * к некоторым другим данным о текущем контексте приложения.
 */
public interface IApplicationContext {
    
    SelectBox getWhoTag();
    
    SelectBox getWhenTag();
    
    SelectBox getWhatTag();
    
    SelectBox getMapZoom();
    
    public CheckBox getVisa();
    
    public CheckBox getRusLanguage();
    
    ISlider getSlidersByName(String name);
    
    HashMap<Integer, IGuiComponent> getComponentsMap();
  
    public HashMap<String, ISlider> getSliders();
    
    IGuiComponent getObjectById(int id) throws IllegalArgumentException;   
   
    public String getHash();
	
	
}
