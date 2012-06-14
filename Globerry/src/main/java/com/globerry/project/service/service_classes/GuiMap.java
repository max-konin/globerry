package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;

/**
 *
 * @author Ed
 */
public class GuiMap {
    /**
     * Возвращает нужную затычку для компонента для заданного id. При изменении GUI, данный метод нужно приводить в 
     * адекватный изменениям вид.
     * @param id идентификатор компонента
     * @return возвращаемый контейнер для компонента. Возвращаемый компонент является затычкой, т.е. не гарантирует
     * целостность данных.
     */
    public static IGuiComponent getElementById(int id) {
        
        if(id >= 1 && id <= 3)
            return new SelectBoxValueContainer();
        if(id >= 4 && id <= 5)
            return new SliderValueContainer();
        return null;
    }
}
