/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.gui.Slider;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.service_classes.IApplicationContext;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
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
public class GloberryGuiContext implements IApplicationContext {

    SelectBox whoTag, whenTag, whatTag;
    Slider alcoholSlider, travelTimeSlider, livingCostSlider, foodCostSlider, temperatureSlider;
    HashMap<Integer, IGuiComponent> componentsMap;
    HashMap<String, Slider> sliders;
    @Autowired
    ITagDao tagDao;
    @Autowired
    IPropertyTypeDao propertyTypeDao;

    /**
     * Инициализирует новый экземпляр контекста приложения. Хранит в себе состояние всех контроллов в приложении. Какой
     * id чему соответствует можно посмотреть здесь
     *
     * @see http://grwe.ru/ids.png .
     */
    @Override
    @Transactional
    public void init() {
        componentsMap = new HashMap<Integer, IGuiComponent>();
        sliders = new HashMap<String, Slider>();

        List<Tag> tags = tagDao.getTagList();
        whoTag = new SelectBox(1);
        whatTag = new SelectBox(2);
        for (Tag tag : tags) {
            if (tag.getTagsType() == TagsType.WHO) {
                whoTag.addValue(tag.getId());
            } else {
                whatTag.addValue(tag.getId());
            }
        }
        componentsMap.put(1, whoTag);
        GuiMap.componentAddHandler(whoTag);
        componentsMap.put(2, whatTag);
        GuiMap.componentAddHandler(whatTag);

        whenTag = new SelectBox(3);        
        for(Month month : Month.values()) 
            whenTag.addValue(month.ordinal());
        
        componentsMap.put(3, whenTag);
        GuiMap.componentAddHandler(whenTag);

        List<PropertyType> properyTypes = propertyTypeDao.getPropertyTypeList();

        int i = 4;
        Slider slider;
        for (PropertyType type : properyTypes) {
            slider = new Slider(i, type);
            sliders.put(type.getName(), slider);
            componentsMap.put(i, slider);
            GuiMap.componentAddHandler(slider);
            i++;
            //TODO Затычка для dependingMounth.
            if (slider.getPropertyType().getId() >= 7) {
                slider.getPropertyType().setDependingMonth(true);
            }
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
    public Slider getSlidersByName(String name) {
        return getSliders().get(name);
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
    public IGuiComponent getObjectById(int id) throws IllegalArgumentException {
        IGuiComponent ret = componentsMap.get(id);
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
        for (String name : sliders.keySet()) {
            str += String.format("\n         " + name + ":  %s\n", sliders.get(name));
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
    public HashMap<String, Slider> getSliders() {
        return sliders;
    }
}
