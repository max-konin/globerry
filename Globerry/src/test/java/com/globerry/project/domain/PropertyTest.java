/**
 * 
 */
package com.globerry.project.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Artem
 *
 */
public class PropertyTest
{

    @Test
    public void EqualsTest()
    {
	Property  property1= new Property();
	property1.setValue(1);
	PropertyType pt1 = new PropertyType();
	property1.setPropertyType(pt1);
	
	Property property2 = new Property();
	property2.setValue(1);
	property2.setPropertyType(pt1);
	
	assertEquals(property1, property2);
	assertEquals(property1.hashCode(), property2.hashCode());
	//property2.setCost(123);
	property1.setValue(2);
	PropertyType pt = new PropertyType();
	property2.setPropertyType(pt);
	//property2.setName("mars");
	
	assertFalse(property1.equals(property2));
	assertFalse(property1.hashCode() == property2.hashCode());
    }
    //Я запихал этот тест в проперти, т.к. они во первых очень похожи, во вторых там есть некоторое несовпадение имён, поэтому я решил, что мне сойдёт всё с рук
    @Test
    public void propertyTypeEqualsTest()
    {
	PropertyType propertyType1= new PropertyType();
	propertyType1.setMaxValue(1);
	propertyType1.setBetterWhenLess(true);
	propertyType1.setMaxValue(123);
	propertyType1.setMinValue(321);
	
	PropertyType propertyType2 = new PropertyType();
	propertyType2.setMaxValue(1);
	propertyType2.setBetterWhenLess(true);
	propertyType2.setMaxValue(123);
	propertyType2.setMinValue(321);
	//System.out.println(property1.getName().equals(property2.getName()));
	assertEquals(propertyType1, propertyType2);
	assertEquals(propertyType1.hashCode(), propertyType2.hashCode());
	//property2.setCost(123);
	propertyType1.setMaxValue(2);
	propertyType2.setName("WHere");
	//property2.setName("mars");
	
	assertFalse(propertyType1.equals(propertyType2));
	assertFalse(propertyType1.hashCode() == propertyType2.hashCode());
    }

}
