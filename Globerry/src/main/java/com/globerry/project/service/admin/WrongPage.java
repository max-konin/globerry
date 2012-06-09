package com.globerry.project.service.admin;

import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class WrongPage implements IEntityCreator
{

    @Override
    public String getJspListFile()
    {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setList(Map<String, Object> map)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void removeElem(int id)
    {
	// TODO Auto-generated method stub
	
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

    @Override
    public void updateElem(Object object)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Map<String, Object> getRelation(Map<String, Object> map, int id)
    {
	return null;
    }

    @Override
    public void getRelation(Map<String, Object> map)
    {
	// TODO Auto-generated method stub
	
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
