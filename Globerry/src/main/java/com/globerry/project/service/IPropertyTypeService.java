package com.globerry.project.service;

import java.util.Set;

import com.globerry.project.domain.Property;
import com.globerry.project.domain.PropertyType;

/**
 * 
 * @author Сергей Крупин
 *
 */
public interface IPropertyTypeService
{
    public Set<Property> getOptionList();
    public void removePropertyType(PropertyType propertyType);
    public void addPropertyType(PropertyType propertyType);
}
