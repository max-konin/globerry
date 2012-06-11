package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.utils.PropertySegment;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.ISlider;
import com.globerry.project.service.interfaces.ISliders;
/**
 * 
 * @author Sergey Krupin
 *
 */
@Service
@Scope("session")
public class Sliders extends Observable implements ISliders
{
    @Autowired
    IPropertyTypeDao propertyTypeDao;
    List<Slider> sliders = new ArrayList<Slider>();
    @Override
    public void init()
    {
	for(int i = 1; i < 6; i++)
	{
	    PropertyType type = propertyTypeDao.getById(i);
	    if(type != null)
	    {
		Slider slider = new Slider(type);
		this.addSlider(slider);
	    }
	    
	}
	
	//помоему все параметры совпадают с DefaultDataBaseCreator
	/*createSlider(true, -35, 35, "temperature");
	createSlider(false, 0, 30, "alcohol");
	createSlider(false, 0, 24, "travel time");
	createSlider(false, 0, 300, "cost of living");
	createSlider(false, 0, 100, "food");
	createSlider(false, 0, 10, "mood");
	createSlider(false, 0, 10, "security");
	createSlider(false, 0, 10, "sex");*/
    }
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
	    listPropertySegments.add(slider.getPropertySegment());
	}
	return listPropertySegments;
    }
    /**
     * @author: Artem
     * 
     * Функция для инициализации слайдеров
     * @param dependingMonth true|false 
     * @param minValue минимальное значение
     * @param maxValue максимальное значение
     * @param name Название свойства 
     */
    private void createSlider(Boolean dependingMonth, int minValue, int maxValue, String name)
    {
	PropertyType propertyType = new PropertyType();
	propertyType.setDependingMonth(dependingMonth);
	propertyType.setMaxValue(maxValue);
	propertyType.setMinValue(minValue);
	propertyType.setName(name);
	
	Slider slider = new Slider(propertyType);
	sliders.add(slider);
	
    }

}
