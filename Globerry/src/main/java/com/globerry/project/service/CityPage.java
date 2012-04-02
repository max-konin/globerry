package com.globerry.project.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.domain.City;
//удалить?!
@Service
public class CityPage implements IEntityCreator
{
    @Autowired
    CityDao cityDao;
    

    static final String JSPPAGE = "citypage";
    @Override
    public String getJspFile()
    {
	return JSPPAGE;

    }
    @Override
    public void setList(Map<String, Object> map)
    {
	map.put("cityList", cityDao.getCityList());
    }
    @Override
    public void removeElem(int id)
    {
	cityDao.removeCity(id);
    }
    



}
