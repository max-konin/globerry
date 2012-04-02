package com.globerry.project.service;

import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globerry.project.dao.CompanyDao;
import com.globerry.project.domain.Company;
//удалить??!
@Service
public class CompanyPage implements IEntityCreator
{

    @Autowired
    private CompanyDao companyDao;
   /* @Autowired
    private CompanyService cmpService;*/
    
    static final String JSPPAGE = "companypage";
    
    @Override
    public String getJspFile()
    {
	// TODO Auto-generated method stub
	return JSPPAGE;
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

}
