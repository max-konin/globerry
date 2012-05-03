package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.PropertySegment;
import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.ISlider;
import com.globerry.project.service.interfaces.ISliders;

@Service
@Scope("session")
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
    public Slider getSlider(PropertyType type){
	Iterator<Slider> it = sliders.iterator();
	while(it.hasNext()){
	    Slider slider = it.next();
	    if (slider.getType().equals(type))
		return slider;
	}
	return null;
    }
    public void changeOrCreate(PropertyType type,int leftValue,int rightValue){
	Slider slider = getSlider(type);
	if (slider == null){
	    slider = new Slider(type);
	    this.addSlider(slider);
	}
	slider.onChange(leftValue, rightValue);
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

    @Override
    public List<PropertySegment> getProperties()
    {
	List<PropertySegment> listPropertySegments = new ArrayList<PropertySegment>();
	Iterator<Slider> it = sliders.iterator();
	while(it.hasNext()){
	    Slider slider = it.next();
	    PropertySegment propertySegment = new PropertySegment(
		    slider.getType(),
		    slider.getStateLeft(),
		    slider.getStateRight());
	    listPropertySegments.add(propertySegment);
	}
	return listPropertySegments;
    }

}
