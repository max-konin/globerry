package com.globerry.project.service.admin;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.globerry.project.dao.CompanyDao;
import com.globerry.project.domain.Company;

@Service
public class CompanyPage implements IEntityCreator
{

    @Autowired
    private CompanyDao companyDao;

    
    static final String JSPPAGE = "companypage";
    static final String JSPUPDATEPAGE = "companyupdatepage";
    
    @Override
    public String getJspListFile()
    {
	// TODO Auto-generated method stub
	return "admin/" + JSPPAGE;
    }

    @Override
    public void setList(Map<String, Object> map)
    {
	map.put("companyList", companyDao.getCompanyList());
    }

    @Override
    public void removeElem(int id)
    {
	companyDao.removeCompany(id);
	
    }

    @Override
    public void getElemById(Map<String, Object> map, int id)
    {
	map.put("company", companyDao.getCompanyById(id));
    }

    @Override
    public String getJspUpdateFile()
    {
	
	return "admin/" + JSPUPDATEPAGE;
    }

    @Override
    public void updateElem(Object object)
    {
	Company company = (Company) object;
	companyDao.updateCompany(company);
    }

    @Override
    public Map<String, Object> getRelation(Map<String, Object> map, int id)
    {
	Company company = companyDao.getCompanyById(id);
	map.put("tourList", company.getTourList());
	return map;
    }

    @Override
    public void getRelation(Map<String, Object> map)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void addRelaion(Object type, int elementId, int itemId)
    {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void removeRelation(Object type, int elementId, int itemId)
    {
	// TODO Auto-generated method stub
	
    }



}
