package com.globerry.project.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.City;

@Repository
public class CityDao implements ICityDao
{
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public void addCity(City city)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().save(city);
	   tx.commit();
	   sessionFactory.close();
    }

    @Override
    public void removeCity(City city)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().delete(city);
	   tx.commit();
	   sessionFactory.close();
    }

    @Override
    public List<City> getCityList(CityRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }


    @Override
    public void updateCity(City city)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	   sessionFactory.getCurrentSession().update(city);
	   tx.commit();
	   sessionFactory.close();
	
    }

}
