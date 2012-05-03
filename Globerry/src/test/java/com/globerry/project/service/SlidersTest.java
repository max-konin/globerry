package com.globerry.project.service;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.globerry.project.dao.ContextLoaderListener;
import com.globerry.project.dao.PropertySegment;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.ISlider;
/**
 * 
 * @author Sergey Krupin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/META-INF/spring/app-context.xml")
@TestExecutionListeners({
    WebContextTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    ContextLoaderListener.class
})
public class SlidersTest
{
    @Autowired
    Sliders sliders;
    @Test
    public void propertiesListTest()
    {
	PropertyType type = new PropertyType();
	type.setName("testAdd");
	type.setMinValue(0);
	type.setMaxValue(10);
	ISlider slider = new Slider(type );
	sliders.addSlider(slider);
	sliders.changeOrCreate(type, 1, 5);
	List<PropertySegment> properties = sliders.getProperties();
	PropertySegment property = properties.get(0);
	if (property.getPropertyType() != type ||
		property.getMinValue() != 1 ||
		property.getMaxValue() != 5)
	    fail();
    }
    @Test
    public void onChangeTest()
    {
	PropertyType type = new PropertyType();
	type.setName("testAdd");
	type.setMinValue(0);
	type.setMaxValue(10);
	ISlider slider = new Slider(type );
	sliders.addSlider(slider);
	sliders.changeOrCreate(type, 1, 5);
	if (1 != sliders.getSlider(type).getStateLeft() || 5 != sliders.getSlider(type).getStateRight())
	    fail();
    }
    @Test
    public void addSliderTest()
    {
	PropertyType type = new PropertyType();
	type.setName("testAdd");
	ISlider slider = new Slider(type );
	sliders.addSlider(slider);
	ISlider slider2 = sliders.getSlider(type);
	if (slider != slider2)
	    fail();
    }

}
