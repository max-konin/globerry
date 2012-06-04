package com.globerry.project.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CityDao;
import com.globerry.project.dao.EventDao;
import com.globerry.project.domain.City;
import com.globerry.project.domain.Event;

@Service
public class EventPage implements IEntityCreator
{
    
    @Autowired
    private EventDao eventDao;
    @Autowired
    private CityDao cityDao;

    static final String JSPPAGE = "eventpage";
    static final String JSPUPDATEPAGE ="eventupdatepage";
    @Override
    public String getJspListFile()
    {
	return "admin/" + JSPPAGE;
    }

    @Override
    public void setList(Map<String, Object> map)
    {
	map.put("eventPage", eventDao.getEventList());
    }

    @Override
    public void removeElem(int id)
    {
	eventDao.removeEvent(id);	
    }

    @Override
    public void getElemById(Map<String, Object> map, int id)
    {
	map.put("event", eventDao.getEventById(id));
    }

    @Override
    public String getJspUpdateFile()
    {
	return "admin/" + JSPUPDATEPAGE;
    }

    @Override
    public void updateElem(Object object)
    {
	Event event = (Event) object;
	eventDao.updateEvent(event);
	
    }

    @Override
    public Map<String, Object> getRelation(Map<String, Object> map, int id)
    {
	Event event = eventDao.getEventById(id);
	map.put("cityList", event.getCities());
	//map.put("allCities", cityDao.getCityList());
	return map;
    }

    @Override
    public void getRelation(Map<String, Object> map)
    {
	map.put("allCities", cityDao.getCityList());
	
    }

    @Override
    public void addRelaion(String type, int elementId, int itemId)
    {
	Event event = eventDao.getEventById(elementId);
	City city = cityDao.getCityById(itemId);
	event.getCities().add(city);
	eventDao.updateEvent(event);
    }

    @Override
    public void removeRelation(String type, int elementId, int itemId)
    {
	Event event = eventDao.getEventById(elementId);
	City city = cityDao.getCityById(itemId);
	event.getCities().remove(city);
	eventDao.updateEvent(event);
	
    }



}
