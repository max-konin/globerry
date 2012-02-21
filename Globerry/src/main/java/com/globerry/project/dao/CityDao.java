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
	// TODO Auto-generated method stub
	
    }

    @Override
    public List<City> getCityList(CityRequest request)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateCity(City oldCity, City newCity)
    {
	// TODO Auto-generated method stub
	
    }

}
