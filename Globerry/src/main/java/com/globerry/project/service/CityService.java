package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.IDao;
import com.globerry.project.domain.City;
import com.globerry.project.service.interfaces.ICityService;

@Service
public class CityService implements ICityService
{
    @Autowired
    private IDao<City> cityDao; 

    @Override
    public List<City> getCityList()
    {
	// TODO Auto-generated method stub
	return cityDao.getAll(City.class);
    }
    @Override
    public void addCity(City city)
    {
	    cityDao.add(city);
	
    }

}
