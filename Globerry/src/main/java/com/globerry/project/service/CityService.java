package com.globerry.project.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CityDao;
import com.globerry.project.domain.CityRequest;
import com.globerry.project.dao.ICityDao;
import com.globerry.project.domain.City;
import com.globerry.project.service.interfaces.ICityService;
import com.globerry.project.utils.PropertySegment;

@Service
public class CityService implements ICityService
{
    @Autowired
    private ICityDao cityDao; 

    @Override
    public List<City> getCityList()
    {
	// TODO Auto-generated method stub
	return cityDao.getCityList();
    }
    

}
