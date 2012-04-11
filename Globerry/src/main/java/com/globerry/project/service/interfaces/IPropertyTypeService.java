/**
 * 
 */
package com.globerry.project.service.interfaces;

import java.util.List;
import java.util.Set;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.PropertyType;

/**
 * @author Artem
 *
 */
public interface IPropertyTypeService
{
    /**
     * Получает лист PropertyType
     * @return
     */
    public Set<PropertyType> getPropertyTypeList();
    /**
     * Добавляет PropertyType
     * @param propertyType
     */
    public void addProperyType(PropertyType propertyType) throws MySqlException;
    /**
     * Удаляет по объекту
     * @param propertyType
     */
    public void removePropertyType(PropertyType propertyType);
    /**
     * удаляет по id
     * @param id
     */
    public void removePropertyType(int id);
}
