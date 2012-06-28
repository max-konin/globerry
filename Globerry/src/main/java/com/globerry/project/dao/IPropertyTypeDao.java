package com.globerry.project.dao;

import java.util.List;
import java.util.Set;


import com.globerry.project.MySqlException;
import com.globerry.project.domain.PropertyType;

public interface IPropertyTypeDao
{
    public void addPropertyType(PropertyType propertyType) throws MySqlException;
    public List<PropertyType> getPropertyTypeList();
    public void updatePropertyType(PropertyType propertyType);
    public void removePropertyType(PropertyType propertyType);
    public void removePropertyType(int id);
    public PropertyType getById(int id);
    /**
     * Функция которая возвращает PropertyType из названия
     * @param name название PropertyType 
     * @return propertyType или null, если его не существует
     * 
     */
    public PropertyType getPropertyTypeByName(String name);
}
