/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.testHelpers;

import com.globerry.project.service.ICSHellGateService;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.Test;

/**
 *
 * @author Max
 */ 
public class AccessorHelper
{
	static public Method setMethodAccessible(Class _class,String methodName,Class[] argClasses) throws NoSuchMethodException
	{
		Method method = _class.getDeclaredMethod(methodName, argClasses);
		method.setAccessible(true);
		return method;
	}

	static public void setField(Object object,Object value, String fieldName) throws SecurityException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException
	{
		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, value);
	}
}
