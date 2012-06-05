package com.globerry.project.service.interfaces;

import java.util.List;
import java.util.Observer;

import com.globerry.project.utils.PropertySegment;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface ISliders extends Observer
{
    public void blockItemOnClickHandler();
    public void onChange();
    public void addSlider(ISlider slider);
    public List<PropertySegment> getProperties();
}
