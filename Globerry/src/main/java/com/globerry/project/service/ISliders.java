package com.globerry.project.service;

import java.util.Observer;

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
}
