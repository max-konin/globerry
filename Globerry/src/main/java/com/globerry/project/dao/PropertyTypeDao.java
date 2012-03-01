package com.globerry.project.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;
import java.lang.IllegalArgumentException;
@Repository
public class PropertyTypeDao implements IPropertyTypeDao
{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addPropertyType(PropertyType propertyType) throws MySqlException
    {
	try
	{
        	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
        	sessionFactory.getCurrentSession().save(propertyType);
        	tx.commit();
        	sessionFactory.close();
	}
	catch(ConstraintViolationException e)
	{
	       MySqlException mySqlExc = new MySqlException();
	       mySqlExc.setMyClass(propertyType);
	       mySqlExc.setDescription("propertyType name is dublicated");
	       throw mySqlExc;
	}
	
    }

    @Override
    public Set<PropertyType> getPropertyTypeList()
    {
	 Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	 Set <PropertyType> list = new HashSet(sessionFactory.getCurrentSession().createQuery("from PropertyType").list());
	 tx.commit();
	 sessionFactory.close();
	 return list;
    }

    @Override
    public void updatePropertyType(PropertyType propertyType)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().update(propertyType);
	tx.commit();
	sessionFactory.close();
	
    }

    @Override
    public void removePropertyType(PropertyType propertyType)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().delete(propertyType);
	tx.commit();
	sessionFactory.close();
	
    }
    @Override
    public void removePropertyType(int id)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	PropertyType propertyType = (PropertyType) sessionFactory.getCurrentSession().load(
		PropertyType.class, id);
	if(null == propertyType) throw new IllegalArgumentException("Such id is not excist!!!");
	sessionFactory.getCurrentSession().delete(propertyType);
	tx.commit();
	sessionFactory.close();
	
    }


}
