package com.globerry.project.dao;

import java.util.List;
import java.util.Set;


import com.globerry.project.MySqlException;
import com.globerry.project.domain.PropertyType;

public interface IPropertyTypeDao
{
    public void addPropertyType(PropertyType propertyType) throws MySqlException;
    public Set<PropertyType> getPropertyTypeList();
    public void updatePropertyType(PropertyType propertyType);
    public void removePropertyType(PropertyType propertyType);
    public void removePropertyType(int id);
    public PropertyType getById(int id);
}