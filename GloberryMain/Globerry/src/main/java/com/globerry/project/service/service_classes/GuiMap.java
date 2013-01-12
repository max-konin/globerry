package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.CheckBox;
import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;

import java.util.Map;


/**
 *
 * @author Ed
 */
public class GuiMap {
    
    private static Map<Integer, String> componentMap = new HashMap<Integer, String>();
   
    /**
     * Возвращает нужную затычку для компонента для заданного id. При изменении GUI, данный метод нужно приводить в 
     * адекватный изменениям вид.
     * @param id идентификатор компонента
     * @return возвращаемый контейнер для компонента. Возвращаемый компонент является затычкой, т.е. не гарантирует
     * целостность данных.
     */
    public static IGuiComponent getElementById(int id) 
    {        
        if (!componentMap.containsKey(id)) return null;
        if (componentMap.get(id).equals("Slider")) return new SliderValueContainer();
        if (componentMap.get(id).equals("SelectBox")) return new SelectBoxValueContainer();
        if (componentMap.get(id).equals("CheckBox")) return new SelectBoxValueContainer();
        return null;
    }
    
    /**
     * Вызывается каждый раз, когда добавляется компонет
     * @param component компонент
     */
    public static void componentAddHandler(IGuiComponent component) throws IllegalArgumentException
    {
        if (componentMap.containsKey(component.getId()))
                throw new IllegalArgumentException( "Slider whith id = " + component.getId() + " is already exsist");
        
        if(component instanceof Slider) componentMap.put(component.getId(), "Slider");
        if(component instanceof SelectBox) componentMap.put(component.getId(), "SelectBox");
        if(component instanceof CheckBox) componentMap.put(component.getId(), "CheckBox");
    }
    
    /**
     * Быдлятина.
     */
    public static String staticToString()
    {
            return componentMap.toString();
    }

    public static final void clear()
    {
        componentMap = new HashMap<Integer, String>();
    }
}
