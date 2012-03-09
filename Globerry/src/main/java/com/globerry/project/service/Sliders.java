package com.globerry.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;

public class Sliders extends Observable implements ISliders
{
    List<Slider> sliders = new ArrayList<Slider>();
    @Override
    public void blockItemOnClickHandler()
    {
	// TODO Auto-generated method stub
    }

    @Override
    public void onChange()
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void addSlider(ISlider slider)
    {
	Slider sliderTemp = (Slider) slider;
	sliders.add(sliderTemp);
	sliderTemp.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
	if (sliders.contains(o)){
	    super.notifyObservers(arg);
	}
    }

}
