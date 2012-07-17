
package com.globerry.project.service.service_classes;

import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.ITagDao;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

/**
 * Фабрика для создания контекстов приложения
 * @author max
 */
@Component
@Scope("singleton")
public class ApplicationContextFactory
{
    @Autowired
    ITagDao tagDao;
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    
    GloberryGuiContext prototype;
    
    /**
     * Создает контекст приложения
     * @return контекст
     */
    public IApplicationContext createAppContext()
    {
        if(prototype == null)
            createProtopype();
        return new GloberryGuiContext(prototype);
    }
    
    /**
     * Инициализирует прототип для содания контекстов приложения
     */
    private void createProtopype()
    {
        prototype = new GloberryGuiContext();
        
        HashMap<Integer, IGuiComponent> componentsMap = new HashMap<Integer, IGuiComponent>();
        HashMap<String, Slider> sliders = new HashMap<String, Slider>();

        List<Tag> tags = tagDao.getTagList();
        SelectBox whoTag = new SelectBox(1);
        SelectBox whatTag = new SelectBox(2);
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

        SelectBox whenTag = new SelectBox(3);        
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
        
        prototype.setComponentsMap(componentsMap);
        prototype.setWhatTag(whatTag);
        prototype.setWhoTag(whoTag);
        prototype.setWhenTag(whenTag);
        prototype.setSliders(sliders);
        
    }
    
   
}
