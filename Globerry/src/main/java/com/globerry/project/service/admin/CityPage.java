package com.globerry.project.service.admin;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.MySqlException;
import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.EventDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;

@Service
public class CityPage implements IEntityCreator
{

    @Autowired
    private CityDao cityDao;
    @Autowired
    private EventDao eventDao;
    @Autowired
    private 
    

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
    @Override
    public void updateElem(Object object)
    {
	//City city = (City) object;
	System.err.println(object.getClass());
	cityDao.updateCity((City) object);
    }
    @Override
    public Map<String, Object> getRelation(Map<String, Object> map, int id)
    {
	City city = cityDao.getCityById(id);
	map.put("eventList", city.getEvents());
	map.put("dmpList", city.getDmpList());
	map.put("propertyList", city.getPropertyList());
	map.put("tagList",city.getTagList());
	return map;
    }
    @Override
    public void getRelation(Map<String, Object> map)
    {
	map.put("eventList", eventDao.getEventList());
	
    }
	

    @Override
    public void removeRelation(String type, int elementId, int itemId)
    {
		// TODO Auto-generated method stub
    }

    
	@Override
	public void addRelaion(String type, int elementId, int itemId) {
		throw new UnsupportedOperationException("Not supported yet.");
	}



}
