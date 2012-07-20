package com.globerry.project.service.admin;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.IDao;
import com.globerry.project.dao.QueryFactory;
import com.globerry.project.domain.City;


@Service
public class CityPage implements IEntityCreator
{

    @Autowired
    private IDao<City> cityDao;
    @Autowired
    private QueryFactory queryFactory;

    static final String JSPPAGE = "citypage";
    static final String JSPUPDATEPAGE = "cityupdatepage";

    @Override
    public String getJspListFile()
    {
	return "admin/" + JSPPAGE;

    }

    @Override
    public void setList(Map<String, Object> map)
    {
	map.put("cityList", cityDao.getAll(City.class));
    }

    @Override
    public void removeElem(int id)
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getElemById(Map<String, Object> map, int id)
    {
	map.put("city",cityDao.getById(City.class,id));
    }

    @Override
    public String getJspUpdateFile()
    {
	// TODO Auto-generated method stub
	return "admin/" + JSPUPDATEPAGE;
    }

    @Override
    public void updateElem(Object object)
    {
	// City city = (City) object;
	cityDao.update((City) object);
    }

    @Override
    public Map<String, Object> getRelation(Map<String, Object> map, int id)
    {
	City city = (City) cityDao.getById(City.class,id);
	map.put("tagList", city.getTagList());
	return map;
    }

    @Override
    public void getRelation(Map<String, Object> map)
    {
	throw new UnsupportedOperationException("Not supported yet.");

    }

    @Override
    public void removeRelation(String type, int elementId, int itemId)
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addRelaion(String type, int elementId, int itemId)
    {
	throw new UnsupportedOperationException("Not supported yet.");
    }

}
