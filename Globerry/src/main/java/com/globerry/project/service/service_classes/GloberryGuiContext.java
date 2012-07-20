/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс, который содержит в себе информацию о состоянии элементов в приложении. Соответствие между элементами на
 * сервере и в этом классе необходимо держать в актуальном состоянии.
 *
 * @see http://grwe.ru/ids.png .
 * @author Ed
 */
@Component
@Scope("session")
public class GloberryGuiContext implements IApplicationContext, Cloneable, IModifyApplicationContext {
    private SelectBox whoTag;
    private SelectBox whenTag;
    private SelectBox whatTag;
    private HashMap<Integer, IGuiComponent> componentsMap;
    private HashMap<String, ISlider> sliders;
    @Autowired
    IDao<Tag> tagDao;
    @Autowired
    IDao<PropertyType> propertyTypeDao;
    
    public GloberryGuiContext(){};
    
    protected GloberryGuiContext(IApplicationContext context)
    {
        
        whoTag = (SelectBox) context.getWhoTag().clone();
        whenTag = (SelectBox) context.getWhenTag().clone();
        whatTag = (SelectBox) context.getWhatTag().clone();
        
        componentsMap = new HashMap<Integer, IGuiComponent>();        
        componentsMap.put(whoTag.getId(), whoTag);
        componentsMap.put(whenTag.getId(), whenTag);
        componentsMap.put(whatTag.getId(), whatTag);
        
        sliders = new HashMap<String, ISlider>();
        for(String sliderName: context.getSliders().keySet())
        {
            Slider slider = (Slider) context.getSliders().get(sliderName).clone();
            sliders.put(sliderName, slider);
            componentsMap.put(slider.getId(), slider);
        }
        
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
    public ISlider getSlidersByName(String name) {
        return getSliders().get(name);
    }

    
    @Override
    public IGuiComponent getObjectById(int id) throws IllegalArgumentException {
        IGuiComponent ret = getComponentsMap().get(id);
        if (ret == null) {
            throw new IllegalArgumentException("Element with such id doesn't exist");
        }
        return ret;
    }
        

    @Override
    public String toString() {
        String str = String.format("\nSelects: "
                + "         Who:   %s,\n"
                + "         When: %s\n"
                + "         What: %s\n"
                + "Sliders:",
                whoTag, whenTag, whatTag);
        for (String name : getSliders().keySet()) {
            str += String.format("\n         " + name + ":  %s\n", getSliders().get(name));
        }
        return str;
        /*
         * return String.format("Selects: Who %s,\n" + " When: %s\n" + " What: %s\n" + "Sliders: Alcohol: %s\n" + "
         * travel time: %s\n" + " living cost: %s\n" + " food cost: %s\n" + " temperature: %s", whoTag, whenTag,
         * whatTag, alcoholSlider, travelTimeSlider, livingCostSlider, foodCostSlider, temperatureSlider);
         */

    }
    
    
    
    /**
     * @return the sliders
     */
    @Override
    public HashMap<String, ISlider> getSliders() {
        return sliders;
    }

    /**
     * @param whoTag the whoTag to set
     */
    @Override
    public void setWhoTag(SelectBox whoTag)
    {
        this.whoTag = whoTag;
    }

    /**
     * @param whenTag the whenTag to set
     */
    @Override
    public void setWhenTag(SelectBox whenTag)
    {
        this.whenTag = whenTag;
    }

    /**
     * @param whatTag the whatTag to set
     */
    @Override
    public void setWhatTag(SelectBox whatTag)
    {
        this.whatTag = whatTag;
    }

    /**
     * @return the componentsMap
     */
    public HashMap<Integer, IGuiComponent> getComponentsMap()
    {
        return componentsMap;
    }

    /**
     * @param componentsMap the componentsMap to set
     */
    @Override
    public void setComponentsMap(HashMap<Integer, IGuiComponent> componentsMap)
    {
        this.componentsMap = componentsMap;
    }

    /**
     * @param sliders the sliders to set
     */  
    public void setSliders(HashMap<String, ISlider> sliders)
    {
        this.sliders = sliders;
    }
    
}
