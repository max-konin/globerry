/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.service.service_classes;
import static org.mockito.Mockito.*;


import com.globerry.project.service.gui.IGuiComponent;
import com.globerry.project.service.gui.SelectBox;
import com.globerry.project.service.gui.Slider;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Последовательность действий в этом текте важна
 * @author max
 */
public class GuiMapTest
{    
    
    /**
     * Test of componentAddHandler method, of class GuiMap.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testComponentAddHandler() 
    {
        IGuiComponent selectBox = mock(SelectBox.class);
        IGuiComponent slider = mock(Slider.class);
        IGuiComponent slider2 = mock(Slider.class);
        
        when(slider.getId()).thenReturn(1);
        when(slider2.getId()).thenReturn(1);
        when(selectBox.getId()).thenReturn(2);
        
        GuiMap.clear();
        GuiMap.componentAddHandler(selectBox);
        GuiMap.componentAddHandler(slider);
       
        Map<Integer, String> componentMap = new HashMap<Integer, String>();
        componentMap.put(1, "Slider");
        componentMap.put(2, "SelectBox");
        
        assertTrue(componentMap.toString().equals(GuiMap.staticToString()));
        GuiMap.componentAddHandler(slider2);
        
    }
    
    /**
     * Test of getElementById method, of class GuiMap.
     */
    @Test
    public void testGetElementById()
    {
        assertTrue(GuiMap.getElementById(1) instanceof SliderValueContainer);
        assertTrue(GuiMap.getElementById(2) instanceof SelectBoxValueContainer);
        
        assertTrue(GuiMap.getElementById(5) == null);
    }

    
}
