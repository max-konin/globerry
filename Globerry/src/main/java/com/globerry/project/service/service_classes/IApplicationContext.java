/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;

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
    
    Slider getTemperatureSlider();
    Slider getAlcoholSlider();
    Slider getTravelTimeSlider();
    Slider getLivingCostSlider();
    Slider getFoodCostSlider();
    
    IGuiComponent getObjectById(int id);
}