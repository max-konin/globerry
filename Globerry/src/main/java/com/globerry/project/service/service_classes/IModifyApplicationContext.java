/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;

import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;
import java.util.HashMap;

/**
 *
 * @author max
 */
public interface IModifyApplicationContext
{

    /**
     * @param componentsMap the componentsMap to set
     */
    void setComponentsMap(HashMap<Integer, IGuiComponent> componentsMap);

    /**
     * @param sliders the sliders to set
     */
    void setSliders(HashMap<String, Slider> sliders);

    /**
     * @param whatTag the whatTag to set
     */
    void setWhatTag(SelectBox whatTag);

    /**
     * @param whenTag the whenTag to set
     */
    void setWhenTag(SelectBox whenTag);

    /**
     * @param whoTag the whoTag to set
     */
    void setWhoTag(SelectBox whoTag);
    
}
