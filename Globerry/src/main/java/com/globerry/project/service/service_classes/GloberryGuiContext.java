/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.HashMap;

/**
 * Класс, который содержит в себе информацию о состоянии элементов в приложении. Соответствие между элементами на
 * сервере и в этом классе необходимо держать в актуальном состоянии.
 * @see http://grwe.ru/ids.png .
 * @author Ed
 */
public class GloberryGuiContext implements IApplicationContext {

    SelectBox whoTag, whenTag, whatTag;
    Slider alcoholSlider, travelTimeSlider, livingCostSlider, foodCostSlider, temperatureSlider;
    HashMap<Integer, IGuiComponent> componentsMap;
    
    /**
     * Инициализирует новый экземпляр контекста приложения. Хранит в себе состояние всех контроллов в приложении.
     * Какой id чему соответствует можно посмотреть здесь @see http://grwe.ru/ids.png .
     */
    public GloberryGuiContext() {
        componentsMap = new HashMap<Integer, IGuiComponent>();
        
        whoTag = new SelectBox(1);
        whoTag.addValue(1);
        whoTag.addValue(2);
        whoTag.addValue(3);
        componentsMap.put(1, whoTag);
        
        whatTag = new SelectBox(2);
        for(int i = 1; i < 5; i++)
            whatTag.addValue(i);
        componentsMap.put(2, whatTag);
        
        whenTag = new SelectBox(2);
        for(int i = 1; i < 13; i++)
            whatTag.addValue(i);
        componentsMap.put(3, whenTag);
        
        temperatureSlider = new Slider(4, -35, +35);
        componentsMap.put(4, temperatureSlider);
        
        travelTimeSlider = new Slider(5, 0, 24);
        componentsMap.put(5, travelTimeSlider);
        
        livingCostSlider = new Slider(6, 0 , 300);
        componentsMap.put(6, livingCostSlider);
        
        foodCostSlider = new Slider(7, 0, 100);
        componentsMap.put(7, foodCostSlider);
        
        alcoholSlider = new Slider(8, 0, 30);
        componentsMap.put(8, alcoholSlider);
    }
    
    @Override
    public SelectBox getWhoTag() {
        return whoTag;
    }

    @Override
    public SelectBox getWhenTag() {
        return whenTag;
    }

    @Override
    public SelectBox getWhatTag() {
        return whatTag;
    }

    @Override
    public Slider getTemperatureSlider() {
        return temperatureSlider;
    }

    @Override
    public Slider getAlcoholSlider() {
        return alcoholSlider;
    }

    @Override
    public Slider getTravelTimeSlider() {
        return travelTimeSlider;
    }

    @Override
    public Slider getLivingCostSlider() {
        return livingCostSlider;
    }

    @Override
    public Slider getFoodCostSlider() {
        return foodCostSlider;
    }

    @Override
    public IGuiComponent getObjectById(int id) {
        return componentsMap.get(id);
    }
    
}
