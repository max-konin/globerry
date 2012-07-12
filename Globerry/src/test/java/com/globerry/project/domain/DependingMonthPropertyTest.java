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
public class DependingMonthPropertyTest
{

    @Test
    public void EqualsTest()
    {
	DependingMonthProperty  dmp1= new DependingMonthProperty();
	dmp1.setMonth(4);
	dmp1.setPropertyType(new PropertyType());
	dmp1.setValue(1);
	DependingMonthProperty dmp2 = new DependingMonthProperty();
	dmp2.setMonth(4);
	dmp2.setPropertyType(new PropertyType());
	dmp2.setValue(1);
	PropertyType pt = new PropertyType();
	pt.setMaxValue(123);
	assertEquals(dmp1, dmp2);
	assertEquals(dmp1.hashCode(), dmp2.hashCode());
	//dmp2.setCost(123);
	dmp2.setMonth(1);
	dmp2.setPropertyType(pt);
	dmp2.setValue(1);
	assertFalse(dmp1.equals(dmp2));
	assertFalse(dmp1.hashCode() == dmp2.hashCode());
    }

}
