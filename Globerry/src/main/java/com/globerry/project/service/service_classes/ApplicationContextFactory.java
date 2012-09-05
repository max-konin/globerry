
package com.globerry.project.service.service_classes;


import com.globerry.project.dao.IDao;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.domain.Tag;
import com.globerry.project.domain.TagsType;
import com.globerry.project.service.gui.CheckBox;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.ISlider;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import java.util.Calendar;

/**
 * Фабрика для создания контекстов приложения
 * @author max
 */
@Component
@Scope("singleton")
public class ApplicationContextFactory
{
    static public final int whoTagId = 1;
    static public final int whatTagId = 2;
    static public final int whenTagId = 3;
    static public final int visaId = 13;
    static public final int rusLanguageId = 14;
    @Autowired
    IDao<Tag> tagDao;
    @Autowired
    IDao<PropertyType> propertyTypeDao;
    
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
        HashMap<String, ISlider> sliders = new HashMap<String, ISlider>();
        
        List<Tag> tags;
        try
        {           
            tags = tagDao.getAll(Tag.class);
        }
        catch(RuntimeException e)
        {
            prototype = null;
            throw e;
        }
        SelectBox whoTag = new SelectBox(whoTagId);
        SelectBox whatTag = new SelectBox(whatTagId);
        for (Tag tag : tags) {
            if (tag.getTagsType() == TagsType.WHO) {
                whoTag.addValue(tag.getId());
            } else {
                whatTag.addValue(tag.getId());
            }
        }
        
        componentsMap.put(whoTagId, whoTag);
        GuiMap.componentAddHandler(whoTag);
        
        componentsMap.put(whatTagId, whatTag);
        GuiMap.componentAddHandler(whatTag);

        SelectBox whenTag = new SelectBox(whenTagId);        
        for(Month month : Month.values()) 
            whenTag.addValue(month.ordinal());
        
        whenTag.setValue((Calendar.getInstance().get(Calendar.MONTH) + 1) % 12);//+1 для того чтобы доставались параметры для следующего месяца после текущего
        
        componentsMap.put(whenTagId, whenTag);
        GuiMap.componentAddHandler(whenTag);

        List<PropertyType> properyTypes;
        try
        {           
             properyTypes = propertyTypeDao.getAll(PropertyType.class);
        }
        catch(RuntimeException e)
        {
            prototype = null;
            throw e;
        }

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
        
        CheckBox visa = new CheckBox(visaId);
        visa.setChecked(false);
        CheckBox rusLanguage = new CheckBox(rusLanguageId);
        
        componentsMap.put(visaId, visa);
        componentsMap.put(rusLanguageId, rusLanguage);
        
        GuiMap.componentAddHandler(visa);
        GuiMap.componentAddHandler(rusLanguage);
        
        prototype.setComponentsMap(componentsMap);
        prototype.setWhatTag(whatTag);
        prototype.setWhoTag(whoTag);
        prototype.setWhenTag(whenTag);
        prototype.setVisa(visa);
        prototype.setRusLanguage(rusLanguage);
        prototype.setSliders(sliders);
        
    }
    
   
}
