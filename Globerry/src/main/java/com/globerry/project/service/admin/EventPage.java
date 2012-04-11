package com.globerry.project.service.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.EventDao;

@Service
public class EventPage implements IEntityCreator
{
    
    @Autowired
    private EventDao eventDao;

    static final String JSPPAGE = "eventpage";
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
	// TODO Auto-generated method stub
	
    }

    @Override
    public String getJspUpdateFile()
    {
	// TODO Auto-generated method stub
	return null;
    }

}
