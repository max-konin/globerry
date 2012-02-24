package com.globerry.project.dao;

import java.util.List;


import com.globerry.project.domain.PropertyType;

public interface IPropertyTypeDao
{
    public void addPropertyType(PropertyType propertyType);
    public List<PropertyType> getPropertyTypeList();
    public void updatePropertyType(PropertyType propertyType);
    public void removePropertyType(PropertyType propertyType);
}
