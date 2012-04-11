package com.globerry.project.service.admin;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.domain.City;

@Service
public class CityPage implements IEntityCreator
{
    @Autowired
    CityDao cityDao;
    

    static final String JSPPAGE = "citypage";
    static final String JSPUPDATEPAGE ="cityupdatepage";
    @Override
    public String getJspListFile()
    {
	return "admin/" + JSPPAGE;

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
    @Override
    public void getElemById(Map<String, Object> map, int id)
    {
	map.put("city", cityDao.getCityById(id));
    }
    @Override
    public String getJspUpdateFile()
    {
	// TODO Auto-generated method stub
	return "admin/" + JSPUPDATEPAGE;
    }
    



}
