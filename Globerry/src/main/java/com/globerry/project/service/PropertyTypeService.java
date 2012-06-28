/**
 * 
 */
package com.globerry.project.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.IPropertyTypeDao;
import com.globerry.project.dao.PropertyTypeDao;
import com.globerry.project.domain.PropertyType;
import com.globerry.project.service.interfaces.IPropertyTypeService;
import com.globerry.project.MySqlException;

/**
 * @author Artem
 *
 */
@Service("propertyTypeService")
public class PropertyTypeService implements IPropertyTypeService
{
    @Autowired
    private IPropertyTypeDao propertyTypeDao;

    /* (non-Javadoc)
     * @see com.globerry.project.service.IPropertyTypeService#getPropertyTypeList()
     */
    @Override
    public List<PropertyType> getPropertyTypeList()
    {
	// TODO Auto-generated method stub
	return propertyTypeDao.getPropertyTypeList();
    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IPropertyTypeService#addProperyType(com.globerry.project.domain.PropertyType)
     */
    @Override
    public void addProperyType(PropertyType propertyType) throws MySqlException
    {
	try
	{
	    propertyTypeDao.addPropertyType(propertyType);
	} catch (MySqlException e)
	{
	    throw e;
	}

    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IPropertyTypeService#removePropertyType(com.globerry.project.domain.PropertyType)
     */
    @Override
    public void removePropertyType(PropertyType propertyType)
    {
	propertyTypeDao.removePropertyType(propertyType);

    }

    /* (non-Javadoc)
     * @see com.globerry.project.service.IPropertyTypeService#removePropertyType(int)
     */
    @Override
    public void removePropertyType(int id)
    {
	// TODO Auto-generated method stub

    }
    public int getPropertyDaoHash()
    {
	return this.propertyTypeDao.hashCode();
    }

}
