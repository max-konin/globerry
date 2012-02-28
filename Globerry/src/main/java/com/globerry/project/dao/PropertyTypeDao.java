package com.globerry.project.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.MySqlException;
import com.globerry.project.domain.Company;
import com.globerry.project.domain.PropertyType;
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
    public List<PropertyType> getPropertyTypeList()
    {
	 Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	List <PropertyType> list = sessionFactory.getCurrentSession().createQuery("from PropertyType").list();
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


}
