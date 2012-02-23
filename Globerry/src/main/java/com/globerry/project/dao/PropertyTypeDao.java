package com.globerry.project.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.PropertyType;
@Repository
public class PropertyTypeDao implements IPropertyTypeDao
{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addPropertyType(PropertyType propertyType)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().save(propertyType);
	tx.commit();
	sessionFactory.close();
	
    }

}
