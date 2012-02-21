package com.globerry.project.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.globerry.project.domain.City;
import com.globerry.project.domain.Month;
import com.globerry.project.domain.Temperature;

@Repository
public class TemperatureDao implements ITemperatureDao
{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public Temperature getTemp(Month month, City city)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Temperature> getTempList(City city)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Temperature> getTempList(Month month)
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setTemp(Temperature temperature)
    {
	Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	sessionFactory.getCurrentSession().save(temperature);
	tx.commit();
	sessionFactory.close();
	
    }

}
